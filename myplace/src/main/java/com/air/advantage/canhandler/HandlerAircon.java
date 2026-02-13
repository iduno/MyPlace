package com.air.advantage.canhandler;

import org.jboss.logging.Logger;

import com.air.advantage.aaservice.data.DataAircon;
import com.air.advantage.aaservice.data.DataAircon.ZoneState;
import com.air.advantage.aaservice.data.DataAirconInfo;
import com.air.advantage.aaservice.data.DataZone;
import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessageAircon;
import com.air.advantage.cbmessages.CANMessageAircon00Unknown;
import com.air.advantage.cbmessages.CANMessageAircon01ZoneInformation;
import com.air.advantage.cbmessages.CANMessageAircon02UnitTypeInformation;
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

public class HandlerAircon extends Handler {
    private static final Logger LOG = Logger.getLogger(HandlerAircon.class);
    private static final long EXPIRY_TIME_SECONDS = 80;
    // Reference to AirconState data class (should be injected or managed)
    // private AirconState airconState;
     public HandlerAircon(MyMasterData myMasterData, EventBus eventBus) {
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
        // Use DataAircon as the top-level reference
        DataAircon dataAircon = getOrCreateDataAircon(msg.getUid());
        DataAirconInfo dataAirconInfo = dataAircon.airconInfo;
        String error = validateZoneMessage(msg, dataAirconInfo);
        if (!error.isEmpty()) {
            LOG.warn("Invalid CB JZ7 msg - " + error);
            return;
        }
        
        // Update expiry time
        dataAirconInfo.expireTime = System.currentTimeMillis() + (EXPIRY_TIME_SECONDS * 1000);
        
        // Map fields from msg to dataAirconInfo
        dataAirconInfo.uid = msg.getUid();
        dataAirconInfo.noOfZones = msg.getNumZones();
        dataAirconInfo.noOfConstantZones = msg.getNumConstantZones();
        dataAirconInfo.constantZone1 = msg.getConstantZone1();
        dataAirconInfo.constantZone2 = msg.getConstantZone2();
        dataAirconInfo.constantZone3 = msg.getConstantZone3();
        dataAirconInfo.filterCleanStatus = msg.getFilterCleanStatus();
        // Update zones map - create zones if they don't exist
        if (dataAircon.getZones() != null) {
            int numZones = msg.getNumZones();
            // Create missing zones
            for (int i = 1; i <= numZones; i++) {
                String zoneKey = String.format("z%02d", i);
                if (!dataAircon.getZones().containsKey(zoneKey)) {
                    DataZone newZone = new DataZone();
                    newZone.number = i;
                    newZone.name = "Zone " + i;
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
        DataAirconInfo dataAirconInfo = dataAircon.airconInfo;
        // Map fields from msg to dataAirconInfo
        if (msg.getUnitType() != null && msg.getUnitType() != UnitType.UNKNOWN) {
            dataAirconInfo.unitType = msg.getUnitType().getValue();
        }
        // Map activationStatus enum if needed
        if (msg.getActivationStatus() != null) {
            switch (msg.getActivationStatus()) {
                case NO_CODE:
                    dataAirconInfo.activationCodeStatus = DataAircon.CodeStatus.noCode;
                    break;
                case CODE_ENABLED:
                    dataAirconInfo.activationCodeStatus = DataAircon.CodeStatus.codeEnabled;
                    break;
                case EXPIRED:
                    dataAirconInfo.activationCodeStatus = DataAircon.CodeStatus.expired;
                    break;
                default:
                    dataAirconInfo.activationCodeStatus = null;
            }
        } else {
            dataAirconInfo.activationCodeStatus = null;
        }
        if (msg.getFwMajor() != 0 || msg.getFwMinor() != 0) {
            dataAirconInfo.cbFWRevMajor = msg.getFwMajor();
            dataAirconInfo.cbFWRevMinor = msg.getFwMinor();        
        }
        // Update expiry time
        dataAirconInfo.expireTime = System.currentTimeMillis() + (EXPIRY_TIME_SECONDS * 1000);
        
        LOG.debug("Processed UnitTypeInformation for UID " + uid +
                ": unitType=" + dataAirconInfo.unitType +
                ", activationCodeStatus=" + dataAirconInfo.activationCodeStatus +
                ", cbFWRevMajor=" + dataAirconInfo.cbFWRevMajor +
                ", cbFWRevMinor=" + dataAirconInfo.cbFWRevMinor);
    }

    private void process(CANMessageAircon03ZoneState msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        
        // Update expiry time
        info.expireTime = System.currentTimeMillis() + (EXPIRY_TIME_SECONDS * 1000);
        
        // Validate zone number
        int zoneNumber = msg.getZoneNumber();
        if (zoneNumber < 1 || zoneNumber > 10) {
            LOG.debug("Rejected CB JZ11 - invalid zoneNumber: " + zoneNumber);
            return;
        }
        
        if (info.noOfZones != null && zoneNumber > info.noOfZones) {
            LOG.debug("Rejected CB JZ11 - zoneNumber too high: " + zoneNumber);
            return;
        }
        
        String zoneKey = String.format("z%02d", zoneNumber);
        DataZone zone = dataAircon.getZones().get(zoneKey);
        if (zone == null) {
            zone = new DataZone();
            zone.number = zoneNumber;
            zone.name = "Zone " + zoneNumber;
            dataAircon.getZones().put(zoneKey, zone);
        }
        
        // Map zone state
        if (msg.getZoneState() != null) {
            switch (msg.getZoneState()) {
                case CLOSE:
                    zone.state = ZoneState.close;
                    break;
                case OPEN:
                    zone.state = ZoneState.open;
                    break;
            }
        }
        
        // Map zone percent (damper value)
        int zonePercent = msg.getZonePercent();
        if (zonePercent >= 0 && zonePercent <= 100) {
            zone.value = zonePercent;
        }
        
        // Map sensor type
        zone.type = msg.getSensorType();
        
        // Map temperatures
        float setTemp = msg.getSetTemp();
        if (setTemp >= 10.0f && setTemp <= 50.0f) {
            zone.setTemp = setTemp;
        }
        
        float measuredTemp = msg.getMeasuredTemp();
        if (measuredTemp > 0.0f && measuredTemp < 100.0f) {
            zone.measuredTemp = measuredTemp;
        }
        
        LOG.debug("Valid CB JZ11 message. UID - " + uid + 
                " zoneNumber - " + zoneNumber + 
                " state - " + zone.state +
                " value - " + zone.value + "%" +
                " type - " + zone.type +
                " setTemp - " + zone.setTemp +
                " measuredTemp - " + zone.measuredTemp);
    }

    private void process(CANMessageAircon04ZoneConfiguration msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        
        // Update expiry time
        info.expireTime = System.currentTimeMillis() + (EXPIRY_TIME_SECONDS * 1000);
        
        // Validate zone number
        int zoneNumber = msg.getZoneNumber();
        if (zoneNumber < 1 || zoneNumber > 10) {
            LOG.debug("Rejected CB JZ13 - invalid zoneNumber: " + zoneNumber);
            return;
        }
        
        if (info.noOfZones != null && zoneNumber > info.noOfZones) {
            LOG.debug("Rejected CB JZ13 - zoneNumber too high: " + zoneNumber);
            return;
        }
        
        String zoneKey = String.format("z%02d", zoneNumber);
        DataZone zone = dataAircon.getZones().get(zoneKey);
        if (zone == null) {
            zone = new DataZone();
            zone.number = zoneNumber;
            zone.name = "Zone " + zoneNumber;
            dataAircon.getZones().put(zoneKey, zone);
        }
        
        // Map configuration fields
        zone.minDamper = msg.getMinDamper();
        zone.maxDamper = msg.getMaxDamper();
        zone.motion = msg.getMotionStatus();
        zone.motionConfig = msg.getMotionConfig();
        zone.error = msg.getZoneError();
        zone.rssi = msg.getRssi();
        
        LOG.debug("Valid CB JZ13 message. UID - " + uid +
                " zoneNumber - " + zoneNumber +
                " minDamper - " + zone.minDamper +
                " maxDamper - " + zone.maxDamper +
                " motion - " + zone.motion +
                " motionConfig - " + zone.motionConfig +
                " error - " + zone.error +
                " rssi - " + zone.rssi);
    }

    private void process(CANMessageAircon05AirconState msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        // Map fields
        switch (msg.getSystemState()) {
            case OFF -> info.state = DataAircon.SystemState.off;
            case ON -> info.state = DataAircon.SystemState.on;
        }
        switch (msg.getSystemMode()) {
            
            case COOL -> info.mode = DataAircon.AirconMode.cool;
            case HEAT -> info.mode = DataAircon.AirconMode.heat;
            case VENT -> info.mode = DataAircon.AirconMode.vent;
            case AUTO -> info.mode = DataAircon.AirconMode.auto;
            case DRY -> info.mode = DataAircon.AirconMode.dry;
            case MYAUTO -> info.mode = DataAircon.AirconMode.myauto;
        }

        switch (msg.getSystemFan()) {
            case OFF -> info.fan = DataAircon.FanStatus.off;
            case LOW -> info.fan = DataAircon.FanStatus.low;
            case MEDIUM -> info.fan = DataAircon.FanStatus.medium;
            case HIGH -> info.fan = DataAircon.FanStatus.high;
            case AUTO -> info.fan = DataAircon.FanStatus.auto;
            case AUTOAA -> info.fan = DataAircon.FanStatus.autoAA;
        }

        info.setTemp = msg.getSetTemp(); // If you have a setTemp field
        info.myZone = msg.getMyZoneId(); // If you have a myZoneId field

        switch (msg.getFreshAirStatus()) {
            case NONE -> info.freshAirStatus = DataAircon.FreshAirStatus.none;
            case OFF -> info.freshAirStatus = DataAircon.FreshAirStatus.off;
            case ON -> info.freshAirStatus = DataAircon.FreshAirStatus.on;
        }

        info.rfSysID = msg.getRfSysId();
        
        // Update expiry time
        info.expireTime = System.currentTimeMillis() + (EXPIRY_TIME_SECONDS * 1000);
        
        LOG.debug("Valid CB JZ15 message. UID - " + uid +
                " state - " + info.state +
                " mode - " + info.mode +
                " fan - " + info.fan +
                " setTemp - " + info.setTemp +
                " myZone - " + info.myZone +
                " freshAirStatus - " + info.freshAirStatus +
                " rfSysID - " + info.rfSysID);
    }

    private void process(CANMessageAircon06CBStatus msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        if (msg.getCbFwMajor() != 0 || msg.getCbFwMinor() != 0) {
            info.cbFWRevMajor = msg.getCbFwMajor();
            info.cbFWRevMinor = msg.getCbFwMinor();
            info.cbType = msg.getCbType();
            info.rfFWRevMajor = msg.getRfFwMajor();
        }

        CANMessageAircon07CbStatusMessage cbStatus = new CANMessageAircon07CbStatusMessage();
        cbStatus.setDeviceType(CANMessage.DeviceType.CONTROL_BOARD);
        cbStatus.setSystemType(CANMessage.SystemType.CAN_AIRCON);
        cbStatus.setUid(uid);
        eventBus.publish("communication-send-can", cbStatus);

        LOG.debug("Processed CBStatus for UID " + uid);
    }

    private void process(CANMessageAircon07CbStatusMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        
        if (msg.getCbFwMajor() != 0 || msg.getCbFwMinor() != 0) {
            info.cbFWRevMajor = msg.getCbFwMajor();
            info.cbFWRevMinor = msg.getCbFwMinor();
            info.cbType = msg.getCbType();
            info.rfFWRevMajor = msg.getRfFwMajor();
        }
        
        // Update expiry time
        info.expireTime = System.currentTimeMillis() + (EXPIRY_TIME_SECONDS * 1000);
        
        LOG.debug("Valid CB JZ17 message. UID - " + uid +
                " cbFWRevMajor - " + info.cbFWRevMajor +
                " cbFWRevMinor - " + info.cbFWRevMinor +
                " cbType - " + info.cbType +
                " rfFWRevMajor - " + info.rfFWRevMajor);
    }

    private void process(CANMessageAircon08CBErrorStatus msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        
        info.airconErrorCode = msg.getErrorCode();
        
        // Update expiry time
        info.expireTime = System.currentTimeMillis() + (EXPIRY_TIME_SECONDS * 1000);
        
        LOG.debug("Valid CB JZ22 message. UID - " + uid + " errorCode - " + info.airconErrorCode);
    }

    private void process(CANMessageAircon09ActivationCodeInformation msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        // Map action to activationCode if the field exists
        if (msg.getAction() == 1) {
            // info.activationCode = DataAircon.ActivationCode.setNewCode; // Uncomment if field exists
        } else if (msg.getAction() == 2) {
            // info.activationCode = DataAircon.ActivationCode.unlock; // Uncomment if field exists
        }
        // Map unlockCode and activationTimeDays if fields exist
        // info.unlockCode = msg.getUnlockCode(); // Uncomment if field exists
        // info.activationTimeDays = msg.getActivationTimeDays(); // Uncomment if field exists
        LOG.debug("Processed ActivationCodeInformation for UID " + uid +
            ", action=" + msg.getAction() +
            ", unlockCode=" + msg.getUnlockCode() +
            ", activationTimeDays=" + msg.getActivationTimeDays());
    }

    private void process(CANMessageAircon0aMidInformation msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        // info.mid = msg.getMid(); // Uncomment if field exists
        LOG.debug("Processed MidInformation for UID " + uid);
    }

    private void process(CANMessageAircon12ZoneSensorPairing msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        // You may need to determine the correct zone for this sensor
        // For now, just print the sensor UID and infoByte
        // DataZone zone = dataAircon.getZones().get(...); // Find by sensor UID if possible
        // zone.sensorUID = msg.getSensorUID(); // Uncomment if field exists
        // zone.infoByte = msg.getInfoByte(); // Uncomment if field exists
        // zone.sensorMajorRev = msg.getSensorMajorRev(); // Uncomment if field exists
        LOG.debug("Processed ZoneSensorPairing for UID " + uid + ", sensorUID=" + msg.getSensorUID() + ", infoByte=" + msg.getInfoByte() + ", sensorMajorRev=" + msg.getSensorMajorRev());
    }

    private void process(CANMessageAircon13CBInfoByte msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        // info.infoByte = msg.getInfoByte(); // Uncomment if field exists
        LOG.debug("Processed CBInfoByte for UID " + uid + ", infoByte=" + msg.getInfoByte());
    }

    private void process(CANMessageAircon26RfDevicePairing msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        // You may want to update pairing info in DataAircon or DataZone
        // info.pairingControl = msg.getPairingControl(); // Uncomment if field exists
        // info.rfDeviceType = msg.getRfDeviceType(); // Uncomment if field exists
        // info.channelNo = msg.getChannelNo(); // Uncomment if field exists
        LOG.debug("Processed RfDevicePairing for UID " + uid + ", pairingControl=" + msg.getPairingControl() + ", rfDeviceType=" + msg.getRfDeviceType() + ", channelNo=" + msg.getChannelNo());
    }

    private void process(CANMessageAircon27RfDeviceCalibration msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        // You may want to update calibration info in DataAircon or DataZone
        // info.calibrationControl = msg.getCalibrationControl(); // Uncomment if field exists
        // info.channelNo = msg.getChannelNo(); // Uncomment if field exists
        // info.upDownPosition = msg.getUpDownPosition(); // Uncomment if field exists
        LOG.debug("Processed RfDeviceCalibration for UID " + uid + ", calibrationControl=" + msg.getCalibrationControl() + ", channelNo=" + msg.getChannelNo() + ", upDownPosition=" + msg.getUpDownPosition());
    }
}
