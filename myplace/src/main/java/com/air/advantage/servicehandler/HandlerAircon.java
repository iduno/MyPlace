package com.air.advantage.servicehandler;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.air.advantage.aaservice.data.DataAircon;
import com.air.advantage.aaservice.data.DataZone;
import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessageAircon;
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

public class HandlerAircon extends Handler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerAircon.class);
    private final MyMasterData myMasterData;

    public HandlerAircon(MyMasterData myMasterData) {
        this.myMasterData = myMasterData;
    }

    /**
     * Process changes between current and new DataAircon state and generate
     * appropriate CAN messages.
     * 
     * @param message The new DataAircon state
     * @param uid The unit ID
     * @param sanitiseData If true, will generate activation code message
     * @return List of CAN messages to send
     */
    public List<CANMessageAircon> getUpdateMessagesForCB(DataAircon message, String uid, boolean sanitiseData) {
        DataAircon dataAirconMaster = myMasterData.masterData.aircons.get(uid);
        DataAircon dataAirconSystem = message;
        List<CANMessageAircon> canMessages = new ArrayList<>();

        LOGGER.info("Processing aircon state changes for unit: " + uid);

        // 1. Generate ZoneInformation message if needed
        if (hasZoneInfoChanged(dataAirconMaster, dataAirconSystem)) {
            CANMessageAircon01ZoneInformation zoneInfoMsg = new CANMessageAircon01ZoneInformation();
            // Set properties using available methods
            if (dataAirconSystem.airconInfo.noOfZones != null) {
                zoneInfoMsg.setNumZones(dataAirconSystem.airconInfo.noOfZones);
            }
            if (dataAirconSystem.airconInfo.noOfConstantZones != null) {
                zoneInfoMsg.setNumConstantZones(dataAirconSystem.airconInfo.noOfConstantZones);
            }
            if (dataAirconSystem.airconInfo.constantZone1 != null) {
                zoneInfoMsg.setConstantZone1(dataAirconSystem.airconInfo.constantZone1);
            }
            if (dataAirconSystem.airconInfo.constantZone2 != null) {
                zoneInfoMsg.setConstantZone2(dataAirconSystem.airconInfo.constantZone2);
            }
            if (dataAirconSystem.airconInfo.constantZone3 != null) {
                zoneInfoMsg.setConstantZone3(dataAirconSystem.airconInfo.constantZone3);
            }
            if (dataAirconSystem.airconInfo.filterCleanStatus != null) {
                zoneInfoMsg.setFilterCleanStatus(dataAirconSystem.airconInfo.filterCleanStatus);
            }
            canMessages.add(zoneInfoMsg);
            LOGGER.debug("Added zone information message");
        }

        // 2. Generate UnitTypeInformation message if needed
        if (hasUnitTypeInfoChanged(dataAirconMaster, dataAirconSystem)) {
            CANMessageAircon02UnitTypeInformation unitTypeMsg = new CANMessageAircon02UnitTypeInformation();
            // Set properties using available methods
            if (dataAirconSystem.airconInfo.unitType != null) {
                unitTypeMsg.setUnitType(UnitType.fromValue(dataAirconSystem.airconInfo.unitType));
            }
            if (dataAirconSystem.airconInfo.activationCodeStatus != null) {
                try {
                    DataAircon.CodeStatus status = dataAirconSystem.airconInfo.activationCodeStatus;
                    if (status != null) {
                        int value = status.getValue();
                        unitTypeMsg.setActivationStatus(
                            CANMessageAircon02UnitTypeInformation.CodeStatus.fromValue(value));
                    }
                } catch (Exception e) {
                    LOGGER.error("Error mapping activation status", e);
                }
            }
            if (dataAirconSystem.airconInfo.cbFWRevMajor != null) {
                unitTypeMsg.setFwMajor(dataAirconSystem.airconInfo.cbFWRevMajor);
            }
            if (dataAirconSystem.airconInfo.cbFWRevMinor != null) {
                unitTypeMsg.setFwMinor(dataAirconSystem.airconInfo.cbFWRevMinor);
            }
            canMessages.add(unitTypeMsg);
            LOGGER.debug("Added unit type information message");
        }

        // 3. Generate zone state messages for changed zones
        if (dataAirconSystem.getZones() != null) {
            for (String zoneKey : dataAirconSystem.getZones().keySet()) {
                DataZone systemZone = dataAirconSystem.getZones().get(zoneKey);
                DataZone masterZone = (dataAirconMaster == null) ? null : dataAirconMaster.getZones().get(zoneKey);
                
                if (hasZoneStateChanged(masterZone, systemZone)) {
                    CANMessageAircon03ZoneState zoneStateMsg = new CANMessageAircon03ZoneState();
                    // Set properties using available methods
                    if (systemZone.number != null) {
                        zoneStateMsg.setZoneNumber(systemZone.number);
                    }
                    if (systemZone.state != null) {
                        zoneStateMsg.setZoneState(mapZoneState(systemZone.state));
                    }
                    if (systemZone.value != null) {
                        zoneStateMsg.setZonePercent(systemZone.value);
                    }
                    if (systemZone.setTemp != null) {
                        zoneStateMsg.setSetTemp(systemZone.setTemp);
                    }
                    if (systemZone.measuredTemp != null) {
                        zoneStateMsg.setMeasuredTemp(systemZone.measuredTemp);
                    }
                    canMessages.add(zoneStateMsg);
                    LOGGER.debug("Added zone state message for zone: " + zoneKey);
                }
            }
        }

        // 4. Generate zone configuration messages for changed zones
        if (dataAirconSystem.getZones() != null) {
            for (String zoneKey : dataAirconSystem.getZones().keySet()) {
                DataZone systemZone = dataAirconSystem.getZones().get(zoneKey);
                DataZone masterZone = (dataAirconMaster == null) ? null : dataAirconMaster.getZones().get(zoneKey);
                
                if (hasZoneConfigChanged(masterZone, systemZone)) {
                    CANMessageAircon04ZoneConfiguration zoneConfigMsg = new CANMessageAircon04ZoneConfiguration();
                    // Set properties using available methods
                    if (systemZone.number != null) {
                        zoneConfigMsg.setZoneNumber(systemZone.number);
                    }
                    if (systemZone.minDamper != null) {
                        zoneConfigMsg.setMinDamper(systemZone.minDamper);
                    }
                    if (systemZone.maxDamper != null) {
                        zoneConfigMsg.setMaxDamper(systemZone.maxDamper);
                    }
                    if (systemZone.motion != null) {
                        zoneConfigMsg.setMotionStatus(systemZone.motion);
                    }
                    if (systemZone.motionConfig != null) {
                        zoneConfigMsg.setMotionConfig(systemZone.motionConfig);
                    }
                    if (systemZone.error != null) {
                        zoneConfigMsg.setZoneError(systemZone.error);
                    }
                    if (systemZone.rssi != null) {
                        zoneConfigMsg.setRssi(systemZone.rssi);
                    }
                    canMessages.add(zoneConfigMsg);
                    LOGGER.debug("Added zone configuration message for zone: " + zoneKey);
                }
            }
        }

        // 5. Generate aircon state message if needed
        if (hasAirconStateChanged(dataAirconMaster, dataAirconSystem)) {
            CANMessageAircon05AirconState airconStateMsg = new CANMessageAircon05AirconState();
            
            // Set properties if available
            if (dataAirconSystem.airconInfo.state != null) {
                airconStateMsg.setSystemState(mapSystemState(dataAirconSystem.airconInfo.state));
            }
            
            if (dataAirconSystem.airconInfo.mode != null) {
                airconStateMsg.setSystemMode(mapSystemMode(dataAirconSystem.airconInfo.mode));
            }
            
            if (dataAirconSystem.airconInfo.fan != null) {
                airconStateMsg.setSystemFan(mapFanState(dataAirconSystem.airconInfo.fan));
            }
            
            if (dataAirconSystem.airconInfo.setTemp != null) {
                airconStateMsg.setSetTemp(dataAirconSystem.airconInfo.setTemp);
            }
            
            if (dataAirconSystem.airconInfo.myZone != null) {
                airconStateMsg.setMyZoneId(dataAirconSystem.airconInfo.myZone);
            }
            
            if (dataAirconSystem.airconInfo.freshAirStatus != null) {
                airconStateMsg.setFreshAirStatus(mapFreshAirStatus(dataAirconSystem.airconInfo.freshAirStatus));
            }
            
            if (dataAirconSystem.airconInfo.rfSysID != null) {
                airconStateMsg.setRfSysId(dataAirconSystem.airconInfo.rfSysID);
            }
            
            canMessages.add(airconStateMsg);
            LOGGER.debug("Added aircon state message");
        }

        // 6. Generate CB Status message if needed
        if (hasCBStatusChanged(dataAirconMaster, dataAirconSystem)) {
            CANMessageAircon06CBStatus cbStatusMsg = new CANMessageAircon06CBStatus();
            // Set properties using available methods
            if (dataAirconSystem.airconInfo.cbFWRevMajor != null) {
                cbStatusMsg.setCbFwMajor(dataAirconSystem.airconInfo.cbFWRevMajor);
            }
            if (dataAirconSystem.airconInfo.cbFWRevMinor != null) {
                cbStatusMsg.setCbFwMinor(dataAirconSystem.airconInfo.cbFWRevMinor);
            }
            if (dataAirconSystem.airconInfo.cbType != null) {
                cbStatusMsg.setCbType(dataAirconSystem.airconInfo.cbType);
            }
            if (dataAirconSystem.airconInfo.rfFWRevMajor != null) {
                cbStatusMsg.setRfFwMajor(dataAirconSystem.airconInfo.rfFWRevMajor);
            }
            canMessages.add(cbStatusMsg);
            LOGGER.debug("Added CB status message");
        }

        // 7. Generate CB status message 2 if needed
        if (hasCBStatusMessage2Changed(dataAirconMaster, dataAirconSystem)) {
            CANMessageAircon07CbStatusMessage cbStatusMsg2 = new CANMessageAircon07CbStatusMessage();
            // Set properties using available methods
            if (dataAirconSystem.airconInfo.cbFWRevMajor != null) {
                cbStatusMsg2.setCbFwMajor(dataAirconSystem.airconInfo.cbFWRevMajor);
            }
            if (dataAirconSystem.airconInfo.cbFWRevMinor != null) {
                cbStatusMsg2.setCbFwMinor(dataAirconSystem.airconInfo.cbFWRevMinor);
            }
            if (dataAirconSystem.airconInfo.cbType != null) {
                cbStatusMsg2.setCbType(dataAirconSystem.airconInfo.cbType);
            }
            if (dataAirconSystem.airconInfo.rfFWRevMajor != null) {
                cbStatusMsg2.setRfFwMajor(dataAirconSystem.airconInfo.rfFWRevMajor);
            }
            canMessages.add(cbStatusMsg2);
            LOGGER.debug("Added CB status message 2");
        }

        // 8. Generate error message if needed
        if (dataAirconSystem.airconInfo.airconErrorCode != null) {
            CANMessageAircon08CBErrorStatus errorStatusMsg = new CANMessageAircon08CBErrorStatus();
            errorStatusMsg.setErrorCode(dataAirconSystem.airconInfo.airconErrorCode);
            canMessages.add(errorStatusMsg);
            LOGGER.debug("Added error message");
        }

        // // 9. Generate activation code message if z7 is true or specific conditions
        // if (generateActivationCode || dataAirconSystem.airconInfo.setActivationCode != null || 
        //     dataAirconSystem.airconInfo.unlockCode != null || 
        //     dataAirconSystem.airconInfo.setActivationTime != null) {
        //     addActivationCodeMessage(dataAirconSystem, canMessages);
        //     LOGGER.debug("Added activation code message");
        // }

        LOGGER.info("Generated " + canMessages.size() + " CAN messages for unit: " + uid);
        return canMessages;
    }

    /**
     * Update aircon state from a map of DataAircon and return update results.
     * @param airconMap Map of aircon UID to DataAircon
     * @param sanitiseData        Business logic flag
     * @return List of update results
     */
    public List<CANMessageAircon> setAircon(TreeMap<String, DataAircon> airconMap, boolean sanitiseData) throws Exception {
        List<CANMessageAircon> result = new ArrayList<>();
        if (airconMap == null || airconMap.isEmpty()) {
            return result;
        }
        String uid = airconMap.firstKey(); // Get the first key (UID)
        DataAircon message = airconMap.firstEntry().getValue(); // Get the first entry's value
        
        DataAircon dataAirconMaster = myMasterData.masterData.aircons.get(uid);
        if (dataAirconMaster != null) {
            DataAircon dataAirconCopy = DataAircon.create();
            if (message.getZones() != null) {
                for (String zoneKey : message.getZones().keySet()) {
                    DataZone zone = message.getZones().get(zoneKey);
                    if (zone != null) {
                        DataZone zoneCopy = new DataZone();
                        // Copy only grouping fields
                        zoneCopy.followers = zone.followers != null ? new ArrayList<>(zone.followers) : null;
                        zoneCopy.following = zone.following;
                        // Optionally copy the zone number or name if needed for identification
                        zoneCopy.number = zone.number;
                        zoneCopy.name = zone.name;
                        dataAirconCopy.getZones().put(zoneKey, zoneCopy);
                    }
                }
            }
            // The following methods must exist or be implemented elsewhere:
            // if (processDataZoneGroupMessageFromJSON(myMasterData.masterData, dataAirconCopy)) {
            //     HandlerJson.getInstance().processData(myMasterData.masterData, "processDataZoneGroupMessageFromJSON");
            // }
        }
        
        result= getUpdateMessagesForCB(message, uid, sanitiseData);
        return result;
    }

    /**
     * Checks if zone information has changed
     */
    private boolean hasZoneInfoChanged(DataAircon master, DataAircon system) {
        if (master == null) return true;
        
        // Check if zone count changed
        return master.getZones().size() != system.getZones().size();
    }

    /**
     * Checks if unit type information has changed
     */
    private boolean hasUnitTypeInfoChanged(DataAircon master, DataAircon system) {
        if (master == null) return true;
        
        return !areEqual(master.airconInfo.unitType, system.airconInfo.unitType) ||
               !areEqual(master.airconInfo.activationCodeStatus, system.airconInfo.activationCodeStatus) ||
               !areEqual(master.airconInfo.cbFWRevMajor, system.airconInfo.cbFWRevMajor) ||
               !areEqual(master.airconInfo.cbFWRevMinor, system.airconInfo.cbFWRevMinor);
    }

    /**
     * Checks if aircon state has changed
     */
    private boolean hasAirconStateChanged(DataAircon master, DataAircon system) {
        if (master == null) return true;
        
        return !areEqual(master.airconInfo.state, system.airconInfo.state) ||
               !areEqual(master.airconInfo.mode, system.airconInfo.mode) ||
               !areEqual(master.airconInfo.fan, system.airconInfo.fan) ||
               !areEqual(master.airconInfo.setTemp, system.airconInfo.setTemp) ||
               !areEqual(master.airconInfo.myZone, system.airconInfo.myZone) ||
               !areEqual(master.airconInfo.freshAirStatus, system.airconInfo.freshAirStatus);
    }

    /**
     * Checks if CB status has changed
     */
    private boolean hasCBStatusChanged(DataAircon master, DataAircon system) {
        if (master == null) return true;
        
        return !areEqual(master.airconInfo.cbType, system.airconInfo.cbType) ||
               !areEqual(master.airconInfo.cbFWRevMajor, system.airconInfo.cbFWRevMajor) ||
               !areEqual(master.airconInfo.cbFWRevMinor, system.airconInfo.cbFWRevMinor) ||
               !areEqual(master.airconInfo.rfFWRevMajor, system.airconInfo.rfFWRevMajor);
    }

    /**
     * Checks if CB status message 2 fields have changed
     */
    private boolean hasCBStatusMessage2Changed(DataAircon master, DataAircon system) {
        if (master == null) return true;
        
        return !areEqual(master.airconInfo.cbFWRevMajor, system.airconInfo.cbFWRevMajor) ||
               !areEqual(master.airconInfo.cbFWRevMinor, system.airconInfo.cbFWRevMinor) ||
               !areEqual(master.airconInfo.cbType, system.airconInfo.cbType);
    }

    /**
     * Checks if zone state has changed
     */
    private boolean hasZoneStateChanged(DataZone master, DataZone system) {
        if (master == null) return true;
        
        return !areEqual(master.state, system.state) ||
               !areEqual(master.value, system.value) ||
               !areEqual(master.setTemp, system.setTemp) ||
               !areEqual(master.measuredTemp, system.measuredTemp);
    }

    /**
     * Checks if zone configuration has changed
     */
    private boolean hasZoneConfigChanged(DataZone master, DataZone system) {
        if (master == null) return true;
        
        return !areEqual(master.name, system.name) ||
               !areEqual(master.minDamper, system.minDamper) ||
               !areEqual(master.maxDamper, system.maxDamper) ||
               !areEqual(master.motion, system.motion) ||
               !areEqual(master.motionConfig, system.motionConfig) ||
               !areEqual(master.error, system.error) ||
               !areEqual(master.rssi, system.rssi);
    }

    /**
     * Checks if the aircon structure is valid: at least one zone has a non-null 'following' field.
     */
    private boolean isValidAirconStructure(DataAircon dataAircon) {
        if (dataAircon == null || dataAircon.getZones() == null) {
            return false;
        }
        for (DataZone dataZone : dataAircon.getZones().values()) {
            if (dataZone != null && dataZone.following != null) {
                return true;
            }
        }
        return false;
    }

    // Helper method to add activation code message
    private void addActivationCodeMessage(DataAircon dataAirconSystem, List<CANMessageAircon> canMessages) {
        CANMessageAircon09ActivationCodeInformation activationCodeMsg = new CANMessageAircon09ActivationCodeInformation();
        
        // Set properties using available methods
        if (dataAirconSystem.airconInfo.activationCodeStatus != null) {
            try {
                DataAircon.CodeStatus status = dataAirconSystem.airconInfo.activationCodeStatus;
                if (status != null) {
                    int statusValue = status.getValue();
                    activationCodeMsg.setAction(statusValue);
                }
            } catch (Exception e) {
                LOGGER.error("Error getting activation code status value", e);
            }
        }
        
        if (dataAirconSystem.airconInfo.unlockCode != null) {
            try {
                activationCodeMsg.setUnlockCode(Integer.parseInt(dataAirconSystem.airconInfo.unlockCode));
            } catch (NumberFormatException e) {
                LOGGER.error("Error parsing unlock code: " + dataAirconSystem.airconInfo.unlockCode, e);
                activationCodeMsg.setUnlockCode(0);
            }
        }
        
        if (dataAirconSystem.airconInfo.setActivationTime != null) {
            activationCodeMsg.setActivationTimeDays(dataAirconSystem.airconInfo.setActivationTime);
        }
        
        canMessages.add(activationCodeMsg);
    }

    // Helper method to compare two objects for equality (null-safe)
    private boolean areEqual(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) return true;
        if (obj1 == null || obj2 == null) return false;
        return obj1.equals(obj2);
    }

    // Map SystemState to CANMessageAircon05AirconState.SystemState
    private CANMessageAircon05AirconState.SystemState mapSystemState(Object state) {
        if (state == null) return null;
        try {
            String stateName = state.toString().toUpperCase();
            return CANMessageAircon05AirconState.SystemState.valueOf(stateName);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error mapping system state: " + state, e);
            return null;
        }
    }

    // Map AirconMode to CANMessageAircon05AirconState.SystemMode
    private CANMessageAircon05AirconState.SystemMode mapSystemMode(Object mode) {
        if (mode == null) return null;
        try {
            String modeName = mode.toString().toUpperCase();
            return CANMessageAircon05AirconState.SystemMode.valueOf(modeName);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error mapping system mode: " + mode, e);
            return null;
        }
    }

    // Map FanStatus to CANMessageAircon05AirconState.FanState
    private CANMessageAircon05AirconState.FanState mapFanState(Object fan) {
        if (fan == null) return null;
        try {
            String fanName = fan.toString().toUpperCase();
            return CANMessageAircon05AirconState.FanState.valueOf(fanName);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error mapping fan state: " + fan, e);
            return null;
        }
    }

    // Map FreshAirStatus to CANMessageAircon05AirconState.FreshAirStatus
    private CANMessageAircon05AirconState.FreshAirStatus mapFreshAirStatus(Object status) {
        if (status == null) return null;
        try {
            String statusName = status.toString().toUpperCase();
            return CANMessageAircon05AirconState.FreshAirStatus.valueOf(statusName);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error mapping fresh air status: " + status, e);
            return null;
        }
    }

    // Map ZoneState to CANMessageAircon03ZoneState.ZoneState
    private CANMessageAircon03ZoneState.ZoneState mapZoneState(Object state) {
        if (state == null) return null;
        try {
            String stateName = state.toString().toUpperCase();
            return CANMessageAircon03ZoneState.ZoneState.valueOf(stateName);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error mapping zone state: " + state, e);
            return null;
        }
    }
}
