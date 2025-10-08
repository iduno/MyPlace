package com.air.advantage.canhandler;

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

public class HandlerAircon extends Handler {
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
            System.out.println("Invalid CB JZ7 msg - " + error);
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
            int currentSize = dataAircon.getZones().size();
            int numZones = msg.getNumZones();
            if (numZones < currentSize) {
                for (int i = numZones + 1; i <= currentSize; i++) {
                    dataAircon.getZones().remove("zone" + i); // Replace with your actual zone key logic if needed
                }
            }
        }
        System.out.println("Valid CB JZ7 message. UID - " + msg.getUid() +
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
            System.out.println("UnitTypeInformation: UID not found, cannot update DataAirconInfo");
            return;
        }
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo dataAirconInfo = dataAircon.airconInfo;
        // Map fields from msg to dataAirconInfo
        dataAirconInfo.cbType = msg.getUnitType() != null ? msg.getUnitType().getValue() : 0;
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
        dataAirconInfo.cbFWRevMajor = msg.getFwMajor();
        dataAirconInfo.cbFWRevMinor = msg.getFwMinor();
        System.out.println("Processed UnitTypeInformation for UID " + uid +
                ": cbType=" + dataAirconInfo.cbType +
                ", activationCodeStatus=" + dataAirconInfo.activationCodeStatus +
                ", cbFWRevMajor=" + dataAirconInfo.cbFWRevMajor +
                ", cbFWRevMinor=" + dataAirconInfo.cbFWRevMinor);
    }

    private void process(CANMessageAircon03ZoneState msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        int zoneNum = msg.getZoneNumber();
        String zoneKey = "zone" + zoneNum;
        DataZone zone = dataAircon.getZones().get(zoneKey);
        if (zone == null) {
            zone = new DataZone();
            dataAircon.getZones().put(zoneKey, zone);
        }
        // Map fields
        zone.name = zoneKey;
        switch (msg.getZoneState()) {
            case CLOSE:
                zone.state = ZoneState.close;
                break;
            case OPEN:
                zone.state = ZoneState.open;
                break;
            default:
                zone.state = null; // or some default state
        }
        // zone.state = msg.getZoneState(); // If you have a state field
        // zone.percent = msg.getZonePercent(); // If you have a percent field
        // zone.type = msg.getZoneType(); // If you have a type field
        zone.measuredTemp = msg.getMeasuredTemp();
        // zone.setTemp = msg.getSetTemp(); // If you have a setTemp field
        dataAircon.getZones().replace(zoneKey, zone);
        System.out.println("Processed ZoneState for UID " + uid + ", zone " + zoneKey);
    }

    private void process(CANMessageAircon04ZoneConfiguration msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        int zoneNum = msg.getZoneNumber();
        String zoneKey = "zone" + zoneNum;
        DataZone zone = dataAircon.getZones().get(zoneKey);
        if (zone == null) {
            zone = new DataZone();
            dataAircon.getZones().put(zoneKey, zone);
        }
        zone.minDamper = msg.getMinDamper();
        zone.maxDamper = msg.getMaxDamper();
        zone.motion = msg.getMotionStatus();
        zone.motionConfig = msg.getMotionConfig();
        zone.error = msg.getZoneError();
        System.out.println("Processed ZoneConfiguration for UID " + uid + ", zone " + zoneKey);
    }

    private void process(CANMessageAircon05AirconState msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        // Map fields
        // info.systemState = msg.getSystemState(); // If you have a systemState field
        // info.mode = msg.getSystemMode(); // If you have a mode field
        // info.fan = msg.getSystemFan(); // If you have a fan field
        // info.setTemp = msg.getSetTemp(); // If you have a setTemp field
        // info.myZoneId = msg.getMyZoneId(); // If you have a myZoneId field
        // info.freshAirStatus = msg.getFreshAirStatus();
        // info.rfSysId = msg.getRfSysId();
        System.out.println("Processed AirconState for UID " + uid);
    }

    private void process(CANMessageAircon06CBStatus msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        info.cbFWRevMajor = msg.getCbFwMajor();
        info.cbFWRevMinor = msg.getCbFwMinor();
        info.cbType = msg.getCbType();
        // info.rfFWRevMajor = msg.getRfFwMajor(); // If you have this field
        System.out.println("Processed CBStatus for UID " + uid);
    }

    private void process(CANMessageAircon07CbStatusMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        info.cbFWRevMajor = msg.getCbFwMajor();
        info.cbFWRevMinor = msg.getCbFwMinor();
        info.cbType = msg.getCbType();
        // info.rfFWRevMajor = msg.getRfFwMajor(); // If you have this field
        System.out.println("Processed CBStatusMessage for UID " + uid);
    }

    private void process(CANMessageAircon08CBErrorStatus msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        info.airconErrorCode = msg.getErrorCode();
        System.out.println("Processed CBErrorStatus for UID " + uid + ", errorCode=" + info.airconErrorCode);
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
        System.out.println("Processed ActivationCodeInformation for UID " + uid +
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
        System.out.println("Processed MidInformation for UID " + uid /*+ ", mid=" + msg.getMid()*/);
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
        System.out.println("Processed ZoneSensorPairing for UID " + uid + ", sensorUID=" + msg.getSensorUID() + ", infoByte=" + msg.getInfoByte() + ", sensorMajorRev=" + msg.getSensorMajorRev());
    }

    private void process(CANMessageAircon13CBInfoByte msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataAircon dataAircon = getOrCreateDataAircon(uid);
        DataAirconInfo info = dataAircon.airconInfo;
        // info.infoByte = msg.getInfoByte(); // Uncomment if field exists
        System.out.println("Processed CBInfoByte for UID " + uid + ", infoByte=" + msg.getInfoByte());
    }

    private void process(CANMessageAircon26RfDevicePairing msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        // You may want to update pairing info in DataAircon or DataZone
        // info.pairingControl = msg.getPairingControl(); // Uncomment if field exists
        // info.rfDeviceType = msg.getRfDeviceType(); // Uncomment if field exists
        // info.channelNo = msg.getChannelNo(); // Uncomment if field exists
        System.out.println("Processed RfDevicePairing for UID " + uid + ", pairingControl=" + msg.getPairingControl() + ", rfDeviceType=" + msg.getRfDeviceType() + ", channelNo=" + msg.getChannelNo());
    }

    private void process(CANMessageAircon27RfDeviceCalibration msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        // You may want to update calibration info in DataAircon or DataZone
        // info.calibrationControl = msg.getCalibrationControl(); // Uncomment if field exists
        // info.channelNo = msg.getChannelNo(); // Uncomment if field exists
        // info.upDownPosition = msg.getUpDownPosition(); // Uncomment if field exists
        System.out.println("Processed RfDeviceCalibration for UID " + uid + ", calibrationControl=" + msg.getCalibrationControl() + ", channelNo=" + msg.getChannelNo() + ", upDownPosition=" + msg.getUpDownPosition());
    }
}
