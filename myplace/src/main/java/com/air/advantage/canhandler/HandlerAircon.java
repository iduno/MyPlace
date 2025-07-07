package com.air.advantage.canhandler;

import com.air.advantage.aaservice.data.DataAircon;
import com.air.advantage.aaservice.data.DataAirconInfo;
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

// TODO: Import or define DataAirconInfo and Zone classes for this handler
// import your.package.DataAirconInfo;
// import your.package.Zone;

public class HandlerAircon extends Handler {
    // Reference to AirconState data class (should be injected or managed)
    // private AirconState airconState;

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
        // Add additional mappings as needed
        // dataAirconInfo.noOfZones = msg.getNumZones();
        // dataAirconInfo.noOfConstantZones = msg.getNumConstantZones();
        // dataAirconInfo.constantZone1 = msg.getConstantZone1();
        // dataAirconInfo.constantZone2 = msg.getConstantZone2();
        // dataAirconInfo.constantZone3 = msg.getConstantZone3();
        // dataAirconInfo.filterCleanStatus = msg.getFilterCleanStatus();
        // Update zones map if needed
        if (dataAircon.getZones() != null) {
            int size = dataAircon.getZones().size();
            if (msg.getNumZones() != size) {
                for (int i = msg.getNumZones() + 1; i < 11; i++) {
                    dataAircon.getZones().remove("zone" + i); // Replace with your actual zone key logic
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
        return myMasterData.masterData.aircons.putIfAbsent(uid, DataAircon.create());

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
        // TODO: Add business logic for CANMessageAircon02UnitTypeInformation
    }

    private void process(CANMessageAircon03ZoneState msg) {
        // TODO: Add business logic for CANMessageAircon03ZoneState
    }

    private void process(CANMessageAircon04ZoneConfiguration msg) {
        // TODO: Add business logic for CANMessageAircon04ZoneConfiguration
    }

    private void process(CANMessageAircon05AirconState msg) {
        // TODO: Add business logic for CANMessageAircon05AirconState
    }

    private void process(CANMessageAircon06CBStatus msg) {
        // TODO: Add business logic for CANMessageAircon06CBStatus
    }

    private void process(CANMessageAircon07CbStatusMessage msg) {
        // TODO: Add business logic for CANMessageAircon07CbStatusMessage
    }

    private void process(CANMessageAircon08CBErrorStatus msg) {
        // TODO: Add business logic for CANMessageAircon08CBErrorStatus
    }

    private void process(CANMessageAircon09ActivationCodeInformation msg) {
        // TODO: Add business logic for CANMessageAircon09ActivationCodeInformation
    }

    private void process(CANMessageAircon0aMidInformation msg) {
        // TODO: Add business logic for CANMessageAircon0aMidInformation
    }

    private void process(CANMessageAircon12ZoneSensorPairing msg) {
        // TODO: Add business logic for CANMessageAircon12ZoneSensorPairing
    }

    private void process(CANMessageAircon13CBInfoByte msg) {
        // TODO: Add business logic for CANMessageAircon13CBInfoByte
    }

    private void process(CANMessageAircon26RfDevicePairing msg) {
        // TODO: Add business logic for CANMessageAircon26RfDevicePairing
    }

    private void process(CANMessageAircon27RfDeviceCalibration msg) {
        // TODO: Add business logic for CANMessageAircon27RfDeviceCalibration
    }
}
