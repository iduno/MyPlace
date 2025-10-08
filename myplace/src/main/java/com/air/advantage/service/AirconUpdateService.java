package com.air.advantage.service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import com.air.advantage.aaservice.data.DataAircon;
import com.air.advantage.aaservice.data.DataAirconInfo;
import com.air.advantage.aaservice.data.DataZone;
import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessageAircon01ZoneInformation;
import com.air.advantage.cbmessages.CANMessageAircon03ZoneState;
import com.air.advantage.cbmessages.CANMessageAircon04ZoneConfiguration;
import com.air.advantage.cbmessages.CANMessageAircon05AirconState;
import com.air.advantage.cbmessages.CANMessageAircon0aMidInformation;

import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Service to apply incoming DataAircon updates, detect differences against the current
 * stored instance and emit appropriate CAN messages (mirrors legacy sendUpdateToCBUnit logic
 * at a simplified level). Only changed fields generate CAN frames.
 */
@ApplicationScoped
public class AirconUpdateService {

    @Inject
    EventBus eventBus;

    /**
     * Apply a map of Aircon updates (key = UID or alias) sending CAN messages for each changed aircon.
     */
    public void applyUpdates(Map<String, DataAircon> incomingMap) {
        if (incomingMap == null) return;
        incomingMap.forEach(this::applyUpdateForKey);
    }

    private void applyUpdateForKey(String key, DataAircon incoming) {
        if (incoming == null) return;
        DataAircon existing = MyMasterData.masterData.aircons.get(key);
        boolean isNew = existing == null;
        if (isNew) {
            existing = DataAircon.create();
            // Ensure uid present
            if (incoming.airconInfo.uid != null) {
                existing.airconInfo.uid = incoming.airconInfo.uid;
            } else if (existing.airconInfo.uid == null) {
                existing.airconInfo.uid = key; // fallback
            }
            MyMasterData.masterData.aircons.put(key, existing);
        }

    // Non-null per class definition
    @SuppressWarnings("null") // airconInfo is final and initialised in DataAircon
    DataAirconInfo oldInfo = existing.airconInfo;
    @SuppressWarnings("null")
    DataAirconInfo newInfo = incoming.airconInfo;

        if (oldInfo == null || newInfo == null) {
            // Should not happen, but guard to satisfy static analysis
            return;
        }
        if (newInfo.uid != null && !Objects.equals(newInfo.uid, oldInfo.uid)) {
            oldInfo.uid = newInfo.uid; // align
        } else if (oldInfo.uid == null) {
            oldInfo.uid = key; // fallback assignment
        }
        String uid = oldInfo.uid;

        AtomicBoolean anyMessageSent = new AtomicBoolean(false);

        // 1. Zone Information (JZ7 legacy) -> CANMessageAircon01ZoneInformation
        boolean zoneInfoChanged = changed(oldInfo.noOfZones, newInfo.noOfZones) ||
                changed(oldInfo.noOfConstantZones, newInfo.noOfConstantZones) ||
                changed(oldInfo.constantZone1, newInfo.constantZone1) ||
                changed(oldInfo.constantZone2, newInfo.constantZone2) ||
                changed(oldInfo.constantZone3, newInfo.constantZone3) ||
                changed(oldInfo.filterCleanStatus, newInfo.filterCleanStatus);
        if (zoneInfoChanged || isNew) {
            CANMessageAircon01ZoneInformation msg = new CANMessageAircon01ZoneInformation();
            populateHeader(msg, uid);
            // Destination (0x0e) constant used in existing code
            msg.setDestination(0x0e);
            msg.setNumZones(valueOrZero(newInfo.noOfZones, oldInfo.noOfZones));
            msg.setNumConstantZones(valueOrZero(newInfo.noOfConstantZones, oldInfo.noOfConstantZones));
            msg.setConstantZone1(valueOrZero(newInfo.constantZone1, oldInfo.constantZone1));
            msg.setConstantZone2(valueOrZero(newInfo.constantZone2, oldInfo.constantZone2));
            msg.setConstantZone3(valueOrZero(newInfo.constantZone3, oldInfo.constantZone3));
            msg.setFilterCleanStatus(valueOrZero(newInfo.filterCleanStatus, oldInfo.filterCleanStatus));
            eventBus.publish("communication-send-can", msg);
            anyMessageSent.set(true);
        }

        // 2. Aircon State (system state / mode / fan / setTemp / myZone)
        boolean airconStateChanged = changed(oldInfo.state, newInfo.state) ||
                changed(oldInfo.mode, newInfo.mode) ||
                changed(oldInfo.fan, newInfo.fan) ||
                changed(oldInfo.setTemp, newInfo.setTemp) ||
                changed(oldInfo.myZone, newInfo.myZone);
        if (airconStateChanged || isNew) {
            CANMessageAircon05AirconState msg = new CANMessageAircon05AirconState();
            populateHeader(msg, uid);
            // System State
            if (valueOr(oldInfo.state, newInfo.state) == DataAircon.SystemState.on) {
                msg.setSystemState(CANMessageAircon05AirconState.SystemState.ON);
            } else {
                msg.setSystemState(CANMessageAircon05AirconState.SystemState.OFF);
            }
            // Mode
            DataAircon.AirconMode mode = valueOr(oldInfo.mode, newInfo.mode);
            if (mode != null) switch (mode) {
                case cool -> msg.setSystemMode(CANMessageAircon05AirconState.SystemMode.COOL);
                case heat -> msg.setSystemMode(CANMessageAircon05AirconState.SystemMode.HEAT);
                case vent -> msg.setSystemMode(CANMessageAircon05AirconState.SystemMode.VENT);
                case dry -> msg.setSystemMode(CANMessageAircon05AirconState.SystemMode.DRY);
                case auto -> msg.setSystemMode(CANMessageAircon05AirconState.SystemMode.AUTO);
                case myauto -> msg.setSystemMode(CANMessageAircon05AirconState.SystemMode.MYAUTO);
            }
            // Fan
            DataAircon.FanStatus fan = valueOr(oldInfo.fan, newInfo.fan);
            if (fan != null) switch (fan) {
                case low -> msg.setSystemFan(CANMessageAircon05AirconState.FanState.LOW);
                case medium -> msg.setSystemFan(CANMessageAircon05AirconState.FanState.MEDIUM);
                case high -> msg.setSystemFan(CANMessageAircon05AirconState.FanState.HIGH);
                case auto -> msg.setSystemFan(CANMessageAircon05AirconState.FanState.AUTO);
                case autoAA -> msg.setSystemFan(CANMessageAircon05AirconState.FanState.AUTOAA);
                default -> msg.setSystemFan(CANMessageAircon05AirconState.FanState.AUTO);
            }
            msg.setSetTemp(valueOr(oldInfo.setTemp, newInfo.setTemp, 25.0f));
            msg.setMyZoneId(valueOrZero(newInfo.myZone, oldInfo.myZone));
            eventBus.publish("communication-send-can", msg);
            anyMessageSent.set(true);
        }

        // 3. Zone diffing
        if (!incoming.zones.isEmpty()) {
            for (String zoneKey : incoming.zones.keySet()) {
                DataZone newZone = incoming.zones.get(zoneKey);
                if (newZone == null) continue;

                DataZone existingZone = existing.zones.get(zoneKey);
                boolean zoneIsNew = false;
                if (existingZone == null) {
                    existingZone = new DataZone();
                    existing.zones.put(zoneKey, existingZone);
                    zoneIsNew = true;
                }

                // Capture old values for comparison (avoid direct field deref complaints)
                DataAircon.ZoneState oldState = existingZone.state;
                Integer oldValue = existingZone.value;
                Integer oldType = existingZone.type;
                Float oldSetTemp = existingZone.setTemp;
                Float oldMeasuredTemp = existingZone.measuredTemp;
                Integer oldNumber = existingZone.number;
                Integer oldMinDamper = existingZone.minDamper;
                Integer oldMaxDamper = existingZone.maxDamper;
                Integer oldMotion = existingZone.motion;
                Integer oldMotionConfig = existingZone.motionConfig;
                Integer oldError = existingZone.error;
                Integer oldRssi = existingZone.rssi;

                boolean zoneStateChanged = changed(oldState, newZone.state) ||
                        changed(oldValue, newZone.value) ||
                        changed(oldType, newZone.type) ||
                        changed(oldSetTemp, newZone.setTemp) ||
                        changed(oldMeasuredTemp, newZone.measuredTemp) ||
                        changed(oldNumber, newZone.number);
                if (zoneStateChanged || zoneIsNew) {
                    CANMessageAircon03ZoneState msg = new CANMessageAircon03ZoneState();
                    populateHeader(msg, uid);
                    Integer zoneNumber = valueOr(oldNumber, newZone.number);
                    if (zoneNumber == null) zoneNumber = parseZoneNumber(zoneKey);
                    msg.setZoneNumber(zoneNumber);
                    DataAircon.ZoneState newState = valueOr(oldState, newZone.state);
                    if (newState == DataAircon.ZoneState.open) {
                        msg.setZoneState(CANMessageAircon03ZoneState.ZoneState.OPEN);
                    } else {
                        msg.setZoneState(CANMessageAircon03ZoneState.ZoneState.CLOSE);
                    }
                    msg.setZonePercent(valueOr(oldValue, newZone.value, 100));
                    msg.setZoneType(valueOr(oldType, newZone.type, 0));
                    msg.setSetTemp(valueOr(oldSetTemp, newZone.setTemp, 25.0f));
                    msg.setMeasuredTemp(valueOr(oldMeasuredTemp, newZone.measuredTemp, 0.0f));
                    eventBus.publish("communication-send-can", msg);
                    anyMessageSent.set(true);
                }

                boolean zoneCfgChanged = changed(oldMinDamper, newZone.minDamper) ||
                        changed(oldMaxDamper, newZone.maxDamper) ||
                        changed(oldMotion, newZone.motion) ||
                        changed(oldMotionConfig, newZone.motionConfig) ||
                        changed(oldError, newZone.error) ||
                        changed(oldRssi, newZone.rssi);
                if (zoneCfgChanged || zoneIsNew) {
                    CANMessageAircon04ZoneConfiguration cfg = new CANMessageAircon04ZoneConfiguration();
                    populateHeader(cfg, uid);
                    Integer zoneNumber = valueOr(oldNumber, newZone.number);
                    if (zoneNumber == null) zoneNumber = parseZoneNumber(zoneKey);
                    cfg.setZoneNumber(zoneNumber);
                    cfg.setMinDamper(valueOr(oldMinDamper, newZone.minDamper, DataZone.DEFAULT_MINDAMPER));
                    cfg.setMaxDamper(valueOr(oldMaxDamper, newZone.maxDamper, DataZone.DEFAULT_MAXDAMPER));
                    cfg.setMotionStatus(valueOr(oldMotion, newZone.motion, DataZone.MOTION_STATE_NO_SENSOR));
                    cfg.setMotionConfig(valueOr(oldMotionConfig, newZone.motionConfig, DataZone.DEFAULT_SETMOTIONCFG));
                    cfg.setZoneError(valueOr(oldError, newZone.error, 0));
                    cfg.setRssi(valueOr(oldRssi, newZone.rssi, 0));
                    eventBus.publish("communication-send-can", cfg);
                    anyMessageSent.set(true);
                }

                // Update stored zone after diffing
                existingZone.copyFrom(newZone);
            }
        }

        // 4. (Optional) Unit type information or mid info – send mid info if any message was sent (mirrors legacy grouping)
        if (anyMessageSent.get()) {
            CANMessageAircon0aMidInformation mid = new CANMessageAircon0aMidInformation();
            populateHeader(mid, uid);
            eventBus.publish("communication-send-can", mid);
        }

        // 5. Apply full copy so future diffs work off updated state
        existing.copyFrom(incoming);
    }

    private static void populateHeader(CANMessage msg, String uid) {
        msg.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        msg.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        msg.setUid(uid);
    }

    private static int parseZoneNumber(String zoneKey) {
        try {
            if (zoneKey != null && zoneKey.length() == 3 && (zoneKey.startsWith("z") || zoneKey.startsWith("Z"))) {
                return Integer.parseInt(zoneKey.substring(1));
            }
        } catch (NumberFormatException ignored) {}
        return 0;
    }

    private static <T> boolean changed(T oldVal, T newVal) {
        if (newVal == null) return false; // treat null as 'no change requested'
        return !Objects.equals(oldVal, newVal);
    }

    private static int valueOrZero(Integer a, Integer b) {
        if (a != null) return a; if (b != null) return b; return 0;
    }

    private static <T> T valueOr(T oldVal, T newVal) {
        return newVal != null ? newVal : oldVal;
    }

    private static int valueOr(Integer oldVal, Integer newVal, int def) {
        if (newVal != null) return newVal; if (oldVal != null) return oldVal; return def;
    }

    private static float valueOr(Float oldVal, Float newVal, float def) {
        if (newVal != null) return newVal; if (oldVal != null) return oldVal; return def;
    }
}
