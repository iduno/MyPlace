package com.air.advantage.canhandler;

import org.jboss.logging.Logger;

import com.air.advantage.aaservice.data.DataAircon;
import com.air.advantage.aaservice.data.DataAircon.AirconMode;
import com.air.advantage.aaservice.data.DataAirconInfo;
import com.air.advantage.aaservice.data.DataZone;
import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessageAircon;
import com.air.advantage.cbmessages.CANMessageAircon00Unknown;
import com.air.advantage.cbmessages.CANMessageAircon01ZoneInformation;
import com.air.advantage.cbmessages.CANMessageAircon02UnitTypeInformation;
import com.air.advantage.cbmessages.CANMessageAircon02UnitTypeInformation.CodeStatus;
import com.air.advantage.cbmessages.CANMessageAircon02UnitTypeInformation.UnitType;
import com.air.advantage.cbmessages.CANMessageAircon03ZoneState;
import com.air.advantage.cbmessages.CANMessageAircon04ZoneConfiguration;
import com.air.advantage.cbmessages.CANMessageAircon05AirconState;
import com.air.advantage.cbmessages.CANMessageAircon06CBStatus;
import com.air.advantage.cbmessages.CANMessageAircon07CbStatusMessage;
import com.air.advantage.cbmessages.CANMessageAircon08CBErrorStatus;
import com.air.advantage.cbmessages.CANMessageAircon09ActivationCodeInformation;
import com.air.advantage.cbmessages.CANMessageAircon0aMidInformation;
import com.air.advantage.cbmessages.CANMessageAircon12ZoneSensorPairing;
import com.air.advantage.cbmessages.CANMessageAircon13CBInfoByte;
import com.air.advantage.cbmessages.CANMessageAircon26RfDevicePairing;
import com.air.advantage.cbmessages.CANMessageAircon27RfDeviceCalibration;

import io.vertx.mutiny.core.eventbus.EventBus;
// TODO: Import or define DataAirconInfo and Zone classes for this handler
// import your.package.DataAirconInfo;
// import your.package.Zone;

public class HandlerAirconCB extends Handler {
    private static final Logger LOG = Logger.getLogger(HandlerAirconCB.class);
    // Reference to AirconState data class (should be injected or managed)
    // private AirconState airconState;
     public HandlerAirconCB(MyMasterData myMasterData, EventBus eventBus) {
        this.myMasterData = myMasterData;
        this.eventBus = eventBus;
    }

    @Override
    public void process(CANMessage message) {
        if (message instanceof CANMessageAircon00Unknown) {
            process((CANMessageAircon00Unknown) message);
        } else if (message instanceof CANMessageAircon01ZoneInformation) {
            process((CANMessageAircon01ZoneInformation) message);
        } else if (message instanceof CANMessageAircon02UnitTypeInformation) {
            process((CANMessageAircon02UnitTypeInformation) message);
        } else if (message instanceof CANMessageAircon03ZoneState) {
            process((CANMessageAircon03ZoneState) message);
        } else if (message instanceof CANMessageAircon04ZoneConfiguration) {
            process((CANMessageAircon04ZoneConfiguration) message);
        } else if (message instanceof CANMessageAircon05AirconState) {
            process((CANMessageAircon05AirconState) message);
        } else if (message instanceof CANMessageAircon06CBStatus) {
            process((CANMessageAircon06CBStatus) message);
        } else if (message instanceof CANMessageAircon07CbStatusMessage) {
            process((CANMessageAircon07CbStatusMessage) message);
        } else if (message instanceof CANMessageAircon08CBErrorStatus) {
            process((CANMessageAircon08CBErrorStatus) message);
        } else if (message instanceof CANMessageAircon09ActivationCodeInformation) {
            process((CANMessageAircon09ActivationCodeInformation) message);
        } else if (message instanceof CANMessageAircon0aMidInformation) {
            process((CANMessageAircon0aMidInformation) message);
        } else if (message instanceof CANMessageAircon12ZoneSensorPairing) {
            process((CANMessageAircon12ZoneSensorPairing) message);
        } else if (message instanceof CANMessageAircon13CBInfoByte) {
            process((CANMessageAircon13CBInfoByte) message);
        } else if (message instanceof CANMessageAircon26RfDevicePairing) {
            process((CANMessageAircon26RfDevicePairing) message);
        } else if (message instanceof CANMessageAircon27RfDeviceCalibration) {
            process((CANMessageAircon27RfDeviceCalibration) message);
        } else if (message instanceof CANMessageAircon) {
            processAircon((CANMessageAircon) message);
        }
        // else ignore or throw exception
    }

    private void processAircon(CANMessageAircon airconMsg) {
        // TODO: Add business logic to update AirconState based on airconMsg fields
        // Example:
        // airconState.setTemperature(airconMsg.getTemperature());
        // airconState.setMode(airconMsg.getMode());
        // ...etc
    }

    // Recommend: For each Aircon message subtype, add a method to handle it
    private void process(CANMessageAircon00Unknown msg) {
        // TODO: Add business logic for CANMessageAircon00Unknown
    }

    private void process(CANMessageAircon01ZoneInformation msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
        DataAirconInfo dataAirconInfo = dataAircon.airconInfo;
        String error = validateZoneMessage(msg, dataAirconInfo);
        if (!error.isEmpty()) {
            LOG.debug("Invalid CB JZ7 msg - " + error);
            return;
        }
        // Map fields from msg to dataAirconInfo
        dataAirconInfo.uid = msg.getUid();
        dataAirconInfo.noOfZones = msg.getNumZones();
        dataAirconInfo.noOfConstantZones = msg.getNumConstantZones();
        dataAirconInfo.constantZone1 = msg.getConstantZone1();
        dataAirconInfo.constantZone2 = msg.getConstantZone2();
        dataAirconInfo.constantZone3 = msg.getConstantZone3();
        dataAirconInfo.filterCleanStatus = msg.getFilterCleanStatus();
        // Update zones map if needed
        if (dataAircon.getZones() != null) {

            int numZones = msg.getNumZones();
            // Create missing zones
            for (int i = 1; i <= numZones; i++) {
                String zoneKey = String.format("z%02d", i);
                if (!dataAircon.getZones().containsKey(zoneKey)) {
                    DataZone newZone = new DataZone();
                    newZone.number = i;
                    newZone.name = "Zone " + i;
                    newZone.type = 0;
                    newZone.state = DataAircon.ZoneState.close; // default state
                    newZone.measuredTemp = 0.0f; // default temp
                    newZone.setTemp = 0.0f; // default set temp
                    newZone.value = 100; // default damper
                    newZone.maxDamper = 100; // default max damper
                    newZone.minDamper = 0; // default min damper

                    dataAircon.getZones().put(zoneKey, newZone);
                }
            }
            // Remove zones beyond the count
            int currentSize = dataAircon.getZones().size();
            if (numZones < currentSize) {
                for (int i = numZones + 1; i <= currentSize; i++) {
                    String zoneKey = String.format("z%02d", i);
                    dataAircon.getZones().remove(zoneKey);
                }
            }
        }
        LOG.debug("Valid CB JZ7 message. UID - " + msg.getUid() +
                " noOfZones - " + msg.getNumZones() +
                " noOfConstants - " + msg.getNumConstantZones() +
                " constant1 - " + msg.getConstantZone1() +
                " constant2 - " + msg.getConstantZone2() +
                " constant3 - " + msg.getConstantZone3() +
                " filterCleanStatus - " + msg.getFilterCleanStatus());
        // Store or update the dataAircon as needed

        CANMessageAircon0aMidInformation midInfo = new CANMessageAircon0aMidInformation();
        midInfo.setUid(uid);
        midInfo.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        midInfo.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        eventBus.publish("communication-send-can", midInfo);

        CANMessageAircon01ZoneInformation cbStatus = new CANMessageAircon01ZoneInformation();
        cbStatus.setUid(uid);
        cbStatus.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        cbStatus.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        cbStatus.setNumZones(dataAirconInfo.noOfZones);
        cbStatus.setNumConstantZones(dataAirconInfo.noOfConstantZones);
        cbStatus.setConstantZone1(dataAirconInfo.constantZone1);
        cbStatus.setConstantZone2(dataAirconInfo.constantZone2);
        cbStatus.setConstantZone3(dataAirconInfo.constantZone3);
        cbStatus.setFilterCleanStatus(dataAirconInfo.filterCleanStatus);

        eventBus.publish("communication-send-can", cbStatus);
    }

    // Helper to get or create DataAircon (implement as needed)
    private DataAircon getOrCreateDataAircon(String uid) {
        return myMasterData.masterData.getAirconByUID(uid);

    }

    // Validation logic based on your ZoneMessage function
    private String validateZoneMessage(CANMessageAircon01ZoneInformation msg, DataAirconInfo dataAirconInfo) {
        StringBuilder error = new StringBuilder();
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            error.append("Rejected CB status message - invalid UID\n");
        }
        int numZones = msg.getNumZones();
        if (numZones < 0 || numZones > 10) {
            error.append("Rejected CB status message - invalid number of Zones\n");
        }
        int numConstants = msg.getNumConstantZones();
        if (numConstants < 0 || numConstants > 3) {
            error.append("Rejected CB status message - invalid number of constants\n");
        }
        int constant1 = msg.getConstantZone1();
        if (constant1 < 0 || constant1 > 10) {
            error.append("Rejected CB status message - invalid constant1 value\n");
        }
        int constant2 = msg.getConstantZone2();
        if (constant2 < 0 || constant2 > 10) {
            error.append("Rejected CB status message - invalid constant2 value\n");
        }
        int constant3 = msg.getConstantZone3();
        if (constant3 < 0 || constant3 > 10) {
            error.append("Rejected CB status message - invalid constant3 value\n");
        }
        return error.toString();
    }

    /*
     *     private UnitType unitType;
    private CodeStatus activationStatus;
    private int fwMajor;
    private int fwMinor;
     */
    private void process(CANMessageAircon02UnitTypeInformation msg) {
        // Attempt to get the UID from context or message (if available)
        // If UID is not available in msg, you may need to pass it from previous context
        // For this example, assume UID is available as msg.getUid() or similar
        String uid = null;
        if (msg instanceof CANMessageAircon) {
            uid = ((CANMessageAircon) msg).getUid();
        }
        if (uid == null || uid.isEmpty()) {
            LOG.debug("UnitTypeInformation: UID not found, cannot update DataAirconInfo");
            return;
        }
        DataAircon dataAircon = getOrCreateDataAircon(uid);

        if (dataAircon == null) {
            return;
        }
        DataAirconInfo dataAirconInfo = dataAircon.airconInfo;

        if (msg.getUnitType() != null && msg.getUnitType() != UnitType.UNKNOWN) {
            dataAirconInfo.unitType = msg.getUnitType().getValue();
        }

        switch (msg.getActivationStatus()) {
            case NO_CODE -> dataAirconInfo.activationCodeStatus = DataAircon.CodeStatus.noCode;
            case CODE_ENABLED -> dataAirconInfo.activationCodeStatus = DataAircon.CodeStatus.codeEnabled;
            case EXPIRED -> dataAirconInfo.activationCodeStatus = DataAircon.CodeStatus.expired;
        }
        if (msg.getFwMajor() != 0) {
            dataAirconInfo.cbFWRevMajor = msg.getFwMajor();
            dataAirconInfo.cbFWRevMinor = msg.getFwMinor();
        }
    }

    private void process(CANMessageAircon03ZoneState msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
        String zoneName = String.format("z%02d", msg.getZoneNumber());
        DataZone zone = dataAircon.zones.get(zoneName);
        if (zone != null) 
        {
            zone.number = msg.getZoneNumber();
            if (msg.getZoneState() == CANMessageAircon03ZoneState.ZoneState.OPEN) {
                zone.state = DataAircon.ZoneState.open;
            } else {
                zone.state = DataAircon.ZoneState.close;
            }
            zone.value = msg.getZonePercent();

            //zone.type = msg.getZoneType();
            if (msg.getSetTemp() > 0.0) {
            zone.setTemp = msg.getSetTemp();
            }
            if (msg.getMeasuredTemp() > 0.0) {
                zone.measuredTemp = msg.getMeasuredTemp();
            }
        }

    }

    private void process(CANMessageAircon04ZoneConfiguration msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
        String zoneName = String.format("z%02d", msg.getZoneNumber());
        DataZone zone = dataAircon.zones.get(zoneName);
        if (zone != null) 
        {
            zone.number = msg.getZoneNumber();
            zone.minDamper = msg.getMinDamper();
            zone.maxDamper = msg.getMaxDamper();
            zone.motion = msg.getMotionStatus();
            zone.motionConfig = msg.getMotionConfig();
            zone.error = msg.getZoneError();
            zone.rssi = msg.getRssi();
        }
    }

    private void process(CANMessageAircon05AirconState msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
        switch (msg.getSystemState())
        {
            case OFF -> dataAircon.airconInfo.state = DataAircon.SystemState.off;
            case ON -> dataAircon.airconInfo.state = DataAircon.SystemState.on;
        }
        dataAircon.airconInfo.setTemp = msg.getSetTemp();
        if (msg.getMyZoneId() >0) {
            dataAircon.airconInfo.myZone = msg.getMyZoneId();
        }
        switch (msg.getSystemMode())
        {
            case MYAUTO -> dataAircon.airconInfo.mode = AirconMode.myauto;
            case AUTO -> dataAircon.airconInfo.mode = AirconMode.auto;
            case COOL -> dataAircon.airconInfo.mode = AirconMode.cool;
            case DRY -> dataAircon.airconInfo.mode = AirconMode.dry;
            case VENT -> dataAircon.airconInfo.mode = AirconMode.vent;
            case HEAT -> dataAircon.airconInfo.mode = AirconMode.heat;
        }
        switch (msg.getSystemFan())
        {
            case OFF -> dataAircon.airconInfo.fan = DataAircon.FanStatus.off;
            case LOW -> dataAircon.airconInfo.fan = DataAircon.FanStatus.low;
            case MEDIUM -> dataAircon.airconInfo.fan = DataAircon.FanStatus.medium;
            case HIGH -> dataAircon.airconInfo.fan = DataAircon.FanStatus.high;
            case AUTO -> dataAircon.airconInfo.fan = DataAircon.FanStatus.auto;
            case AUTOAA -> dataAircon.airconInfo.fan = DataAircon.FanStatus.autoAA;
        }
        switch (msg.getFreshAirStatus())
        {
            case OFF -> dataAircon.airconInfo.freshAirStatus = DataAircon.FreshAirStatus.off;
            case ON -> dataAircon.airconInfo.freshAirStatus = DataAircon.FreshAirStatus.on;
            case NONE -> dataAircon.airconInfo.freshAirStatus = DataAircon.FreshAirStatus.none;
        }
        dataAircon.airconInfo.rfSysID = msg.getRfSysId();
        
    }

    private void process(CANMessageAircon06CBStatus msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
        uid = dataAircon.airconInfo.uid;
        CANMessageAircon0aMidInformation midInfo = new CANMessageAircon0aMidInformation();
        midInfo.setUid(uid);
        midInfo.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        midInfo.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        eventBus.publish("communication-send-can", midInfo);

        CANMessageAircon06CBStatus cbStatus = new CANMessageAircon06CBStatus();
        cbStatus.setUid(uid);
        cbStatus.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        cbStatus.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        cbStatus.setCbFwMajor(dataAircon.airconInfo.cbFWRevMajor);
        cbStatus.setCbFwMinor(dataAircon.airconInfo.cbFWRevMinor);
        cbStatus.setCbType(dataAircon.airconInfo.cbType);
        eventBus.publish("communication-send-can", cbStatus);

    }

    private void process(CANMessageAircon07CbStatusMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }

        uid = dataAircon.airconInfo.uid;
        CANMessageAircon0aMidInformation midInfo = new CANMessageAircon0aMidInformation();
        midInfo.setUid(uid);
        midInfo.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        midInfo.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        eventBus.publish("communication-send-can", midInfo);

        CANMessageAircon02UnitTypeInformation unitTypeInfo = new CANMessageAircon02UnitTypeInformation();
        unitTypeInfo.setUid(uid);
        unitTypeInfo.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        unitTypeInfo.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        unitTypeInfo.setUnitType(CANMessageAircon02UnitTypeInformation.UnitType.FUJITSU);
        unitTypeInfo.setActivationStatus(CodeStatus.NO_CODE);
        eventBus.publish("communication-send-can", unitTypeInfo);

        CANMessageAircon01ZoneInformation zoneInfo = new CANMessageAircon01ZoneInformation();
        zoneInfo.setUid(uid);
        zoneInfo.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        zoneInfo.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        zoneInfo.setDestination(0x0e);
        zoneInfo.setNumZones(dataAircon.airconInfo.noOfZones);
        zoneInfo.setNumConstantZones(dataAircon.airconInfo.noOfConstantZones);
        zoneInfo.setConstantZone1(dataAircon.airconInfo.constantZone1);
        zoneInfo.setConstantZone2(dataAircon.airconInfo.constantZone2);
        zoneInfo.setConstantZone3(dataAircon.airconInfo.constantZone3);
        zoneInfo.setFilterCleanStatus(dataAircon.airconInfo.filterCleanStatus);
        eventBus.publish("communication-send-can", zoneInfo);

        // Send 05 message (AirconState)
        CANMessageAircon05AirconState airconState = new CANMessageAircon05AirconState();
        airconState.setUid(uid);
        airconState.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        airconState.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        
        // Set airconState properties based on dataAircon
        if (dataAircon.airconInfo.state != null) {
            if (dataAircon.airconInfo.state == DataAircon.SystemState.on) {
                airconState.setSystemState(CANMessageAircon05AirconState.SystemState.ON);
            } else {
                airconState.setSystemState(CANMessageAircon05AirconState.SystemState.OFF);
            }
        }
        
        // Set temperature
        airconState.setSetTemp(dataAircon.airconInfo.setTemp != null ? dataAircon.airconInfo.setTemp : 25.0f);
        
        // Convert mode if available
        if (dataAircon.airconInfo.mode != null) {
            switch (dataAircon.airconInfo.mode) {
                case cool:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.COOL);
                    break;
                case heat:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.HEAT);
                    break;
                case vent:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.VENT);
                    break;
                case dry:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.DRY);
                    break;
                case auto:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.AUTO);
                    break;
                case myauto:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.MYAUTO);
                    break;
                default:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.AUTO);
                    break;

            }
        }
        
        // Set fan status
        if (dataAircon.airconInfo.fan != null) {
            switch (dataAircon.airconInfo.fan) {
                case low:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.LOW);
                    break;
                case medium:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.MEDIUM);
                    break;
                case high:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.HIGH);
                    break;
                case auto:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.AUTO);
                    break;
                case autoAA:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.AUTOAA);
                    break;
                default:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.AUTO);
                    break;
            }
        }

        if (dataAircon.airconInfo.freshAirStatus != null) {
            switch (dataAircon.airconInfo.freshAirStatus) {
                case none:
                    airconState.setFreshAirStatus(CANMessageAircon05AirconState.FreshAirStatus.NONE);
                    break;
                case on:
                    airconState.setFreshAirStatus(CANMessageAircon05AirconState.FreshAirStatus.ON);
                    break;
                case off:
                    airconState.setFreshAirStatus(CANMessageAircon05AirconState.FreshAirStatus.OFF);
                    break;
            }
        }
        
        // Set myZone (if available)
        airconState.setMyZoneId(dataAircon.airconInfo.myZone != null ? dataAircon.airconInfo.myZone : 0);
        
        eventBus.publish("communication-send-can", airconState);

        for (DataZone zone : dataAircon.getZones().values()) {
            if (zone == null) continue;
            CANMessageAircon12ZoneSensorPairing zoneSensorPairing = new CANMessageAircon12ZoneSensorPairing();
            zoneSensorPairing.setUid(uid);
            zoneSensorPairing.setDeviceType(CANMessage.DeviceType.AIRCON_1);
            zoneSensorPairing.setSystemType(CANMessage.SystemType.CAN_AIRCON);
            zoneSensorPairing.setSensorUID(zone.sensorUid);
            zoneSensorPairing.setInfoByte(zone.number);
            zoneSensorPairing.setSensorMajorRev(0);
            eventBus.publish("communication-send-can", zoneSensorPairing);

            CANMessageAircon03ZoneState zoneState = new CANMessageAircon03ZoneState();
            zoneState.setUid(uid);
            zoneState.setDeviceType(CANMessage.DeviceType.AIRCON_1);
            zoneState.setSystemType(CANMessage.SystemType.CAN_AIRCON);
            zoneState.setZoneNumber(zone.number);
            zoneState.setZoneState(CANMessageAircon03ZoneState.ZoneState.fromValue(zone.state.getValue()));
            zoneState.setZonePercent(zone.value);
            zoneState.setSensorType(zone.type);
            zoneState.setMeasuredTemp(zone.measuredTemp);
            zoneState.setSetTemp(zone.setTemp);
            
            eventBus.publish("communication-send-can", zoneState);

            CANMessageAircon04ZoneConfiguration zoneConfig = new CANMessageAircon04ZoneConfiguration();
            zoneConfig.setUid(uid);
            zoneConfig.setDeviceType(CANMessage.DeviceType.AIRCON_1);
            zoneConfig.setSystemType(CANMessage.SystemType.CAN_AIRCON);
            
            // Handle primitive values safely, avoiding unboxing errors
            int zoneNumber = 0;
            int minDamper = DataZone.DEFAULT_MINDAMPER;
            int maxDamper = DataZone.DEFAULT_MAXDAMPER;
            int motionStatus = DataZone.MOTION_STATE_NO_SENSOR;
            int motionConfig = DataZone.DEFAULT_SETMOTIONCFG;
            int zoneError = 0;
            int rssi = 0;
            
            // Safely get values only if not null
            if (zone.number != null) zoneNumber = zone.number;
            if (zone.minDamper != null) minDamper = zone.minDamper;
            if (zone.maxDamper != null) maxDamper = zone.maxDamper;
            if (zone.motion != null) motionStatus = zone.motion;
            if (zone.motionConfig != null) motionConfig = zone.motionConfig;
            if (zone.error != null) zoneError = zone.error;
            if (zone.rssi != null) rssi = zone.rssi;
            
            zoneConfig.setZoneNumber(zoneNumber);
            zoneConfig.setMinDamper(minDamper);
            zoneConfig.setMaxDamper(maxDamper);
            zoneConfig.setMotionStatus(motionStatus);
            zoneConfig.setMotionConfig(motionConfig);
            zoneConfig.setZoneError(zoneError);
            zoneConfig.setRssi(rssi);
            eventBus.publish("communication-send-can", zoneConfig);
        }

    }

    private void process(CANMessageAircon08CBErrorStatus msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
    }

    private void process(CANMessageAircon09ActivationCodeInformation msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
    }

    private void process(CANMessageAircon0aMidInformation msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
    }

    private void process(CANMessageAircon12ZoneSensorPairing msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
        
        String zoneName = String.format("z%02d", msg.getInfoByte());
        DataZone zone = dataAircon.getZones().get(zoneName);
        if (zone == null) {
            return;
        }
        
        // Send 0a message (MidInformation)
        CANMessageAircon0aMidInformation midInfo = new CANMessageAircon0aMidInformation();
        midInfo.setUid(uid);
        midInfo.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        midInfo.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        eventBus.publish("communication-send-can", midInfo);
        
        // Send 01 message (ZoneInformation)
        CANMessageAircon01ZoneInformation zoneInfo = new CANMessageAircon01ZoneInformation();
        zoneInfo.setUid(uid);
        zoneInfo.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        zoneInfo.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        zoneInfo.setDestination(0x0e); // Standard destination
        
        // Set zone information properties
        int numZones = dataAircon.airconInfo.noOfZones != null ? dataAircon.airconInfo.noOfZones : 0;
        int numConstantZones = dataAircon.airconInfo.noOfConstantZones != null ? dataAircon.airconInfo.noOfConstantZones : 0;
        int constantZone1 = dataAircon.airconInfo.constantZone1 != null ? dataAircon.airconInfo.constantZone1 : 0;
        int constantZone2 = dataAircon.airconInfo.constantZone2 != null ? dataAircon.airconInfo.constantZone2 : 0;
        int constantZone3 = dataAircon.airconInfo.constantZone3 != null ? dataAircon.airconInfo.constantZone3 : 0;
        int filterCleanStatus = dataAircon.airconInfo.filterCleanStatus != null ? dataAircon.airconInfo.filterCleanStatus : 0;
        
        zoneInfo.setNumZones(numZones);
        zoneInfo.setNumConstantZones(numConstantZones);
        zoneInfo.setConstantZone1(constantZone1);
        zoneInfo.setConstantZone2(constantZone2);
        zoneInfo.setConstantZone3(constantZone3);
        zoneInfo.setFilterCleanStatus(filterCleanStatus);
        eventBus.publish("communication-send-can", zoneInfo);
        
        // Send 02 message (UnitTypeInformation)
        CANMessageAircon02UnitTypeInformation unitTypeInfo = new CANMessageAircon02UnitTypeInformation();
        unitTypeInfo.setUid(uid);
        unitTypeInfo.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        unitTypeInfo.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        unitTypeInfo.setUnitType(CANMessageAircon02UnitTypeInformation.UnitType.FUJITSU); // Default to FUJITSU
        unitTypeInfo.setActivationStatus(CANMessageAircon02UnitTypeInformation.CodeStatus.NO_CODE); // Default to NO_CODE
        
        // Set firmware versions if available
        int fwMajor = dataAircon.airconInfo.cbFWRevMajor != null ? dataAircon.airconInfo.cbFWRevMajor : 0;
        int fwMinor = dataAircon.airconInfo.cbFWRevMinor != null ? dataAircon.airconInfo.cbFWRevMinor : 0;
        unitTypeInfo.setFwMajor(fwMajor);
        unitTypeInfo.setFwMinor(fwMinor);
        eventBus.publish("communication-send-can", unitTypeInfo);
        
        // Send 05 message (AirconState)
        CANMessageAircon05AirconState airconState = new CANMessageAircon05AirconState();
        airconState.setUid(uid);
        airconState.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        airconState.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        
        // Set airconState properties based on dataAircon
        if (dataAircon.airconInfo.state != null) {
            if (dataAircon.airconInfo.state == DataAircon.SystemState.on) {
                airconState.setSystemState(CANMessageAircon05AirconState.SystemState.ON);
            } else {
                airconState.setSystemState(CANMessageAircon05AirconState.SystemState.OFF);
            }
        }
        
        // Set temperature
        airconState.setSetTemp(dataAircon.airconInfo.setTemp != null ? dataAircon.airconInfo.setTemp : 25.0f);
        
        // Convert mode if available
        if (dataAircon.airconInfo.mode != null) {
            switch (dataAircon.airconInfo.mode) {
                case cool:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.COOL);
                    break;
                case heat:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.HEAT);
                    break;
                case vent:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.VENT);
                    break;
                case dry:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.DRY);
                    break;
                case auto:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.AUTO);
                    break;
                case myauto:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.MYAUTO);
                    break;
                default:
                    airconState.setSystemMode(CANMessageAircon05AirconState.SystemMode.AUTO);
                    break;

            }
        }
        
        // Set fan status
        if (dataAircon.airconInfo.fan != null) {
            switch (dataAircon.airconInfo.fan) {
                case low:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.LOW);
                    break;
                case medium:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.MEDIUM);
                    break;
                case high:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.HIGH);
                    break;
                case auto:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.AUTO);
                    break;
                case autoAA:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.AUTOAA);
                    break;
                default:
                    airconState.setSystemFan(CANMessageAircon05AirconState.FanState.AUTO);
                    break;
            }
        }

        if (dataAircon.airconInfo.freshAirStatus != null) {
            switch (dataAircon.airconInfo.freshAirStatus) {
                case none:
                    airconState.setFreshAirStatus(CANMessageAircon05AirconState.FreshAirStatus.NONE);
                    break;
                case on:
                    airconState.setFreshAirStatus(CANMessageAircon05AirconState.FreshAirStatus.ON);
                    break;
                case off:
                    airconState.setFreshAirStatus(CANMessageAircon05AirconState.FreshAirStatus.OFF);
                    break;
            }
        }
        
        // Set myZone (if available)
        airconState.setMyZoneId(dataAircon.airconInfo.myZone != null ? dataAircon.airconInfo.myZone : 0);
        
        eventBus.publish("communication-send-can", airconState);
        
        // Send 03 message (ZoneState)
        CANMessageAircon03ZoneState zoneState = new CANMessageAircon03ZoneState();
        zoneState.setUid(uid);
        zoneState.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        zoneState.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        
        // Set zone properties
        int zoneNumber = zone.number != null ? zone.number : 0;
        zoneState.setZoneNumber(zoneNumber);
        
        // Convert zone state
        CANMessageAircon03ZoneState.ZoneState zoneStateEnum = 
            (zone.state != null && zone.state == DataAircon.ZoneState.open) ? 
            CANMessageAircon03ZoneState.ZoneState.OPEN : 
            CANMessageAircon03ZoneState.ZoneState.CLOSE;
        zoneState.setZoneState(zoneStateEnum);
        
        // Set zone values
        zoneState.setZonePercent(zone.value != null ? zone.value : 100);
        zoneState.setSensorType(zone.type != null ? zone.type : 0);
        zoneState.setSetTemp(zone.setTemp != null ? zone.setTemp : 25.0f);
        zoneState.setMeasuredTemp(zone.measuredTemp != null ? zone.measuredTemp : 0.0f);
        
        eventBus.publish("communication-send-can", zoneState);
        
        // Send 04 message (ZoneConfiguration)
        CANMessageAircon04ZoneConfiguration zoneConfig = new CANMessageAircon04ZoneConfiguration();
        zoneConfig.setUid(uid);
        zoneConfig.setDeviceType(CANMessage.DeviceType.AIRCON_1);
        zoneConfig.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        
        // Set zone config properties safely to avoid null pointer exceptions
        zoneConfig.setZoneNumber(zone.number != null ? zone.number : 0);
        zoneConfig.setMinDamper(zone.minDamper != null ? zone.minDamper : DataZone.DEFAULT_MINDAMPER);
        zoneConfig.setMaxDamper(zone.maxDamper != null ? zone.maxDamper : DataZone.DEFAULT_MAXDAMPER);
        zoneConfig.setMotionStatus(zone.motion != null ? zone.motion : DataZone.MOTION_STATE_NO_SENSOR);
        zoneConfig.setMotionConfig(zone.motionConfig != null ? zone.motionConfig : DataZone.DEFAULT_SETMOTIONCFG);
        zoneConfig.setZoneError(zone.error != null ? zone.error : 0);
        zoneConfig.setRssi(zone.rssi != null ? zone.rssi : 0);
        
        eventBus.publish("communication-send-can", zoneConfig);
    }

    private void process(CANMessageAircon13CBInfoByte msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (dataAircon == null) {
            return;
        }
    }

    private void process(CANMessageAircon26RfDevicePairing msg) {
        String uid = msg.getUid();
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (uid == null || uid.isEmpty()) return;
        if (dataAircon == null) {
            return;
        }
    }

    private void process(CANMessageAircon27RfDeviceCalibration msg) {
        String uid = msg.getUid();
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        if (uid == null || uid.isEmpty()) return;
        if (dataAircon == null) {
            return;
        }
    }
}
