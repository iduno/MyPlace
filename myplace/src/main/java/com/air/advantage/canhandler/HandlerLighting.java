package com.air.advantage.canhandler;

import org.jboss.logging.Logger;

import com.air.advantage.aaservice.data.DataLight;
import com.air.advantage.aaservice.data.DataGroup;
import com.air.advantage.aaservice.data.MasterData;
import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessageLighting;
import com.air.advantage.cbmessages.CANMessageLighting00LmStatusMessageOld;
import com.air.advantage.cbmessages.CANMessageLighting01LmControlMessage;
import com.air.advantage.cbmessages.CANMessageLighting02LmStatusMessage;
import com.air.advantage.cbmessages.CANMessageLighting15Rm2ControlMessage;
import com.air.advantage.cbmessages.CANMessageLighting16Rm2StatusMessage;
import com.air.advantage.cbmessages.CANMessageLighting17Rm2AddDevice;

import io.vertx.mutiny.core.eventbus.EventBus;

public class HandlerLighting extends Handler {

        /**
         * Pre-processing for all message types. Returns UID if valid, else null.
         * Checks UID and optionally system type. No deviceType logic.
         */
        private String preProcess(CANMessage msg) {
            String uid = null;
            try {
                uid = msg.getUid();
            } catch (Exception e) {
                LOG.debug("Failed to extract UID: " + e.getMessage());
            }
            if (uid == null || uid.isEmpty()) {
                LOG.debug("Rejected message - invalid UID");
                return null;
            }
            return uid;
        }

    private static final Logger LOG = Logger.getLogger(HandlerLighting.class);
    private static final long RF_EXPIRY_TIME_SECONDS = 70;
    private static final long NON_RF_EXPIRY_TIME_SECONDS = 260;
    
    public HandlerLighting(MyMasterData myMasterData, EventBus eventBus) {
        this.myMasterData = myMasterData;
        this.eventBus = eventBus;
    }

    @Override
    public void process(CANMessage message) {
        if (message instanceof CANMessageLighting00LmStatusMessageOld) {
            process((CANMessageLighting00LmStatusMessageOld) message);
        } else if (message instanceof CANMessageLighting01LmControlMessage) {
            process((CANMessageLighting01LmControlMessage) message);
        } else if (message instanceof CANMessageLighting02LmStatusMessage) {
            process((CANMessageLighting02LmStatusMessage) message);
        } else if (message instanceof CANMessageLighting15Rm2ControlMessage) {
            process((CANMessageLighting15Rm2ControlMessage) message);
        } else if (message instanceof CANMessageLighting16Rm2StatusMessage) {
            process((CANMessageLighting16Rm2StatusMessage) message);
        } else if (message instanceof CANMessageLighting17Rm2AddDevice) {
            process((CANMessageLighting17Rm2AddDevice) message);
        } else if (message instanceof CANMessageLighting) {
            processLighting((CANMessageLighting) message);
        }
    }

    private void processLighting(CANMessageLighting lightingMsg) {
        // Fallback for unhandled lighting message types
        LOG.warn("Unhandled lighting message type: " + lightingMsg.getClass().getSimpleName());
    }
    
    // JZ0 - Old LM Status Message (deprecated, use JZ2)
    private void process(CANMessageLighting00LmStatusMessageOld msg) {
        String uid = preProcess(msg);
        if (uid == null) return;
        
        LOG.warn("Warning: Received deprecated JZ0 message for UID " + uid + ", consider upgrading firmware to use JZ2");

        // Parse bitfields for roomsExist and validRooms
        Integer roomsExist = msg.getRoomExists(); 
        Integer validRooms = msg.getValidRooms(); 
        Integer version = msg.getVersion(); 

        if (roomsExist > 63) {
            LOG.debug("Rejected LM status message - invalid roomsExist");
            return;
        }
        if (validRooms > 63) {
            LOG.debug("Rejected LM status message - invalid validRooms");
            return;
        }

        LOG.debug("Valid LM setup message old. UID - " + uid + " roomsExist - " + roomsExist + " validRooms - " + validRooms + " version - " + version);

        String binaryString = String.format("%6s", Integer.toBinaryString(roomsExist)).replace(' ', '0');
        char[] charArray = binaryString.toCharArray();
        long expiryTime = System.currentTimeMillis() + (NON_RF_EXPIRY_TIME_SECONDS * 1000);
        for (int roomNumber = 1; roomNumber <= 6; roomNumber++) {
            int bitIndex = charArray.length - roomNumber;
            String lightId = uid + String.format("%02d", roomNumber);
            if (bitIndex < 0 || charArray[bitIndex] == '0') {
                // Disabled room
                LOG.debug("Disabled room " + roomNumber);
            } else {
                boolean isRelay = false;
                registerOrUpdateLight(myMasterData.getMasterData(), lightId, isRelay, "LM", false);
                DataLight light = getOrCreateLight(lightId);
                light.deviceType = "dimmer";
                light.moduleType = "LM";
                light.nextPollTime = expiryTime;
                if (light.state == null) {
                    light.state = "off";
                }
                if (light.value == null) {
                    light.value = 80;
                }
                LOG.debug("Enabled room " + roomNumber);
            }
        }
    }
    
    // JZ1 - LM Control Message
    private void process(CANMessageLighting01LmControlMessage msg) {
        String uid = preProcess(msg);
        if (uid == null) return;
        
        int roomNumber = msg.getRoomNumber();
        if (roomNumber < 1 || roomNumber > 6) {
            LOG.debug("Rejected LM control message - invalid room number: " + roomNumber);
            return;
        }
        
        CANMessageLighting01LmControlMessage.LightState lightState = msg.getLightState();
        int brightnessLevel = msg.getBrightnessLevel();
        
        // Brightness level 0-100 maps to percentages (level is already 0-100 by index*5)
        int percentValue = Math.min(100, Math.max(0, brightnessLevel));
        
        String lightId = uid + String.format("%02d", roomNumber);
        
        DataLight light = getOrCreateLight(lightId);
        light.state = (lightState == CANMessageLighting01LmControlMessage.LightState.ON) ? "on" : "off";
        light.value = percentValue;
        light.moduleType = "LM";
        
        LOG.debug("Valid LM control message. UID - " + uid + 
                " room - " + roomNumber + 
                " state - " + lightState + 
                " brightness - " + brightnessLevel);
    }
    
    // JZ2 - LM Status Message (setup message with room configuration)
    private void process(CANMessageLighting02LmStatusMessage msg) {
        String uid = preProcess(msg);
        if (uid == null) return;
        
        boolean isRF = isRFModule(msg.getSystemType());
        long expiryTime = System.currentTimeMillis() + 
            ((isRF ? RF_EXPIRY_TIME_SECONDS : NON_RF_EXPIRY_TIME_SECONDS) * 1000);
        
        int version = msg.getMajorFWVersion();
        
        LOG.debug("Valid LM setup message (JZ2). UID - " + uid + 
                " version - " + version + "." + msg.getMinorFWVersion());
        
        // Process each room (max 6 rooms)
        for (int i = 0; i < 6; i++) {
            if (msg.getRoomExists(i)) {
                int roomNumber = i + 1;
                String lightId = uid + String.format("%02d", roomNumber);
                boolean isRelay = msg.getRelayRoom(i);
                registerOrUpdateLight(myMasterData.getMasterData(), lightId, isRelay, "LM", isRF);
                // Optionally set deviceType and state/value as before
                DataLight light = getOrCreateLight(lightId);
                light.deviceType = isRelay ? "relay" : "dimmer";
                light.nextPollTime = expiryTime;
                if (light.state == null) {
                    light.state = "off";
                }
                if (light.value == null && !isRelay) {
                    light.value = 80;
                }
                LOG.debug("Enabled light - room " + roomNumber + 
                        " type: " + light.deviceType + 
                        " valid: " + msg.getValidRoom(i));
            }
        }
    }
    
    // JZ15 - RM2 Control Message (Thing state)
    private void process(CANMessageLighting15Rm2ControlMessage msg) {
        String uid = preProcess(msg);
        if (uid == null) return;

        int roomNumber = msg.getRoomNumber();
        if (roomNumber < 1 || roomNumber > 6) {
            LOG.debug("Rejected RM2 control message - invalid room/channel number: " + roomNumber);
            return;
        }

        String lightId = uid + String.format("%02d", roomNumber);
        int state = msg.getLightState();
        int dimLevel = msg.getDimLevel();
        int switchState = msg.getSwitchState();
        int nodeDipState = msg.getNodeDipState();
        int dimOffset = msg.getDimOffset();
        int statusState = msg.getStatusState();
        boolean lowBattery = msg.isLowBattery();
        boolean isCalibrated = msg.isCalibrated();
        boolean isPoll = msg.isPoll();

        // DIP state-specific logic
        switch (nodeDipState) {
            case 4: // Blind/curtain/awning
                // For blinds, state: 0=down/close, 1=up/open, 2=stop, 3=in-progress-down, 4=in-progress-up
                DataLight blind = getOrCreateLight(lightId);
                blind.moduleType = "RM2";
                blind.deviceType = "blind";
                switch (state) {
                    case 0:
                        blind.state = "down";
                        blind.value = 0;
                        break;
                    case 1:
                        blind.state = "up";
                        blind.value = 100;
                        break;
                    case 2:
                        blind.state = "stop";
                        break;
                    case 3:
                        blind.state = "in-progress-down";
                        blind.value = 25;
                        break;
                    case 4:
                        blind.state = "in-progress-up";
                        blind.value = 75;
                        break;
                    default:
                        blind.state = "unknown";
                        break;
                }
                LOG.debug("RM2 blind logic: UID=" + uid + ", channel=" + roomNumber + ", state=" + state);
                break;
            case 5: // Light (dimmable)
                DataLight light = getOrCreateLight(lightId);
                light.moduleType = "RM2";
                light.deviceType = "dimmer";
                if (state == 1) {
                    light.state = "on";
                    light.value = dimLevel * 10;
                } else {
                    light.state = "off";
                    light.value = 0;
                }
                light.dimOffset = dimOffset;
                LOG.debug("RM2 dimmer logic: UID=" + uid + ", channel=" + roomNumber + ", state=" + state + ", dimLevel=" + dimLevel + ", dimOffset=" + dimOffset);
                break;
            case 9: // Fan
                DataLight fan = getOrCreateLight(lightId);
                fan.moduleType = "RM2";
                fan.deviceType = "fan";
                // Fan value logic: 0=off, 1=low, 2=med, 3=high, etc.
                fan.state = (state == 1) ? "on" : "off";
                fan.value = (state == 1) ? dimLevel : 0;
                fan.dimOffset = dimOffset;
                LOG.debug("RM2 fan logic: UID=" + uid + ", channel=" + roomNumber + ", state=" + state + ", dimLevel=" + dimLevel + ", dimOffset=" + dimOffset);
                break;
            case 10: // Generic thing (button, etc.)
                // Not a light, but a generic thing; skip DataLight update, but log
                LOG.debug("RM2 thing logic: UID=" + uid + ", channel=" + roomNumber + ", state=" + state + " (no DataLight update)");
                break;
            default:
                // Default: treat as light (relay/dimmer)
                DataLight defLight = getOrCreateLight(lightId);
                defLight.moduleType = "RM2";
                if (state == 0) {
                    defLight.state = "off";
                    defLight.value = 0;
                } else if (state == 1) {
                    defLight.state = "on";
                    defLight.value = (dimLevel > 0 && dimLevel <= 100) ? dimLevel : 100;
                } else if (state == 2) {
                    // Stop command - maintain current state
                } else {
                    LOG.debug("Unknown RM2 state: " + state + " for UID " + uid + ", channel " + roomNumber);
                }
                break;
        }

        // Log status byte info
        LOG.debug("Valid RM2 control message. UID - " + uid +
                " channel - " + roomNumber +
                " state - " + state +
                " dimLevel - " + dimLevel +
                ", switchState: " + switchState +
                ", nodeDipState: " + nodeDipState +
                ", dimOffset: " + dimOffset +
                ", statusState: " + statusState +
                ", lowBattery: " + lowBattery +
                ", isCalibrated: " + isCalibrated +
                ", isPoll: " + isPoll);
    }
    
    // JZ16 - RM2 Status Message (Module DIP configuration)
    private void process(CANMessageLighting16Rm2StatusMessage msg) {
        String uid = preProcess(msg);
        if (uid == null) return;

        int infoByte = msg.getInfoByte();
        LOG.debug("JZ38 RM2 module info byte: " + infoByte + " for uid:" + uid);

        boolean isRF = isRFModule(msg.getSystemType());
        long expiryTime = System.currentTimeMillis() + ((isRF ? RF_EXPIRY_TIME_SECONDS : NON_RF_EXPIRY_TIME_SECONDS) * 1000);

        // Validate and process each channel DIP state
        Integer[] dipStates = {msg.getDip1State(), msg.getDip2State(), msg.getDip3State(), msg.getDip4State(), msg.getDip5State(), msg.getDip6State()};
        for (int i = 0; i < 6; i++) {
            int roomNumber = i + 1;
            Integer dipState = dipStates[i];
            if (dipState == null) {
                LOG.debug("Rejected RM2 status message - invalid channel DIP" + (i+1) + " state");
                return;
            }
        }

        // Validate info byte
        if (infoByte < 0) {
            LOG.debug("Rejected RM2 status message - invalid info byte");
            return;
        }

        // Process each channel (1-6)
        for (int i = 0; i < 6; i++) {
            int roomNumber = i + 1;
            int dipState = dipStates[i];
            boolean isEnabled = ((infoByte >> i) & 1) == 1;
            String lightId = uid + String.format("%02d", roomNumber);
            if (isEnabled) {
                boolean isRelay = (dipState == 8);
                boolean isDimmer = (dipState == 9);
                boolean isBlind = (dipState == 1 || dipState == 2 || dipState == 3);
                String deviceType = mapDipStateToDeviceType(dipState);
                registerOrUpdateLight(myMasterData.getMasterData(), lightId, isRelay, "RM2", isRF);
                DataLight light = getOrCreateLight(lightId);
                light.deviceType = deviceType;
                light.moduleType = "RM2";
                light.nextPollTime = expiryTime;
                if (light.state == null) {
                    light.state = "off";
                }
                LOG.debug("Enabled RM2 channel " + roomNumber + " dipState: " + dipState + " deviceType: " + deviceType);
            } else {
                LOG.debug("Disabled RM2 channel " + roomNumber + " dipState: " + dipState);
            }
        }
    }
    
    // JZ17 - RM2 Add Device (Version/setup info)
    private void process(CANMessageLighting17Rm2AddDevice msg) {
        String uid = preProcess(msg);
        if (uid == null) return;
        
        int majorVersion = msg.getMajorFWVersion();
        int minorVersion = msg.getMinorFWVersion();
        int infoByte = msg.getInfoByte();
        
        LOG.debug("JZ39 info byte: " + infoByte + " for uid:" + uid);
        LOG.debug("Valid RM2 setup message JZ39. UID - " + uid + 
                " version - " + majorVersion + "." + minorVersion);
        
        // Determine module type from infoByte
        String moduleType;
        if ((infoByte & 0x80) == 0x80) {
            moduleType = "DM"; // Bit 7 set = DM module
        } else if ((infoByte & 0x10) == 0x10) {
            moduleType = "GDM"; // Bit 4 set = Garage Door Module
        } else {
            moduleType = "RM2";
        }
        
        LOG.debug("Module type: " + moduleType + " firmware: " + majorVersion + "." + minorVersion);
    }
    
    // Helper methods
    
    private boolean isRFModule(CANMessage.SystemType systemType) {
        // RF modules use RF_AIRCON system type; LIGHTING is for wired modules
        return systemType == CANMessage.SystemType.RF_AIRCON;
    }
    
    private DataLight getOrCreateLight(String lightId) {
        if (myMasterData != null && MyMasterData.masterData != null) {
            DataLight light = MyMasterData.masterData.myLights.lights.get(lightId);
            if (light == null) {
                light = new DataLight();
                light.id = lightId;
                MyMasterData.masterData.myLights.lights.put(lightId, light);
            }
            MyMasterData.masterData.system.hasLights = true;
            MyMasterData.masterData.system.drawLightsTab = true;
            return light;
        }
        // Fallback if no master data
        DataLight light = new DataLight();
        light.id = lightId;
        return light;
    }
    
    private String mapDipStateToDeviceType(int dipState) {
        // Map DIP switch state to device type
        switch (dipState) {
            case 1:
            case 2:
            case 3:
                return "blind"; // Up/down control (blinds, curtains, awnings)
            case 8:
                return "relay"; // On/off toggle
            case 9:
                return "dimmer"; // Dimmable control
            case 10:
                return "disabled"; // Disabled channel
            default:
                return "unknown";
        }
    }

    /**
     * Add a DataLight to a group, restoring group membership and order.
     * If groupId is null or invalid, adds to default group.
     * @param masterData The master data object
     * @param groupId The group ID (may be null or empty)
     * @param dataLight The DataLight to add
     * @param position The position in the group (use 99 for append)
     */
    private void addToGroup(MasterData masterData, String groupId, DataLight dataLight, int position) {
        if (masterData == null || dataLight == null || dataLight.id == null) return;
        String lightId = dataLight.id;
        String currentGroupId = masterData.myLights.getGroupId(lightId);
        DataGroup group = masterData.myLights.groups.get(groupId);
        if (group == null) {
            if (masterData.myLights.groups.size() == 0) {
                group = new DataGroup();
                group.id = "g0";
                group.name = "Living";
                masterData.myLights.groups.put(group.id, group);
                masterData.myLights.groupsOrder.add(group.id);
            } else {
                group = masterData.myLights.groups.get("g0");
            }
        }
        if (group == null) return;
        if (position >= group.lightsOrder.size()) position = group.lightsOrder.size();
        if (!group.id.equals(currentGroupId)) {
            group.lightsOrder.add(position, lightId);
            masterData.myLights.lights.put(lightId, dataLight);
            if (currentGroupId != null) {
                DataGroup oldGroup = masterData.myLights.groups.get(currentGroupId);
                if (oldGroup != null) oldGroup.lightsOrder.remove(lightId);
            }
            return;
        }
        int lightPosition = masterData.myLights.getLightPosition(lightId);
        if (position >= group.lightsOrder.size()) position = group.lightsOrder.size() - 1;
        if (position < 0) {
            LOG.debug("addToGroup: invalid move position");
        } else {
            // Move within group
            group.lightsOrder.remove(lightPosition);
            group.lightsOrder.add(position, lightId);
        }
    }

    /**
     * Register or update a DataLight instance, handling relay/light conversion, expiry, group restoration, and backup restore.
     * @param masterData The master data object
     * @param lightId The light ID
     * @param isRelayModule True if relay, false if dimmer
     * @param moduleType The module type string
     * @param isRFDevice True if RF device
     * @return true if converted or restored from backup, false otherwise
     */
    private boolean registerOrUpdateLight(MasterData masterData, String lightId, boolean isRelayModule, String moduleType, boolean isRFDevice) {
        if (masterData == null || lightId == null) return false;
        DataLight lightData = masterData.myLights.lights.get(lightId);
        boolean changed = false;
        if (lightData != null) {
            if (!isRelayModule || lightData.value == null) {
                if (!isRelayModule && lightData.value == null) {
                    LOG.debug("Converting from a relay to a light");
                    lightData.value = 80;
                    lightData.relay = null;
                }
                lightData.moduleType = moduleType;
                long defaultExpiry = 70;
                lightData.nextPollTime = System.currentTimeMillis() + (isRFDevice ? defaultExpiry * 1000 : NON_RF_EXPIRY_TIME_SECONDS * 1000);
                lightData.thisIsRFDevice = isRFDevice;
                return changed;
            }
            LOG.debug("Converting from a light to a relay");
            lightData.value = null;
            lightData.relay = Boolean.TRUE;
            changed = true;
            lightData.moduleType = moduleType;
            long defaultExpiry = 70;
            lightData.nextPollTime = System.currentTimeMillis() + (isRFDevice ? defaultExpiry * 1000 : NON_RF_EXPIRY_TIME_SECONDS * 1000);
            lightData.thisIsRFDevice = isRFDevice;
            return changed;
        }
        // If not found, restore from backup if possible
        if (!changed) {
            DataLight dataLight = new DataLight();
            dataLight.id = lightId;
            dataLight.moduleType = moduleType;
            dataLight.thisIsRFDevice = isRFDevice;
            boolean restored = false;
            if (masterData.myLights.backupLights != null) {
                java.util.Iterator<com.air.advantage.aaservice.data.DataMyLights.BackupLight> it = masterData.myLights.backupLights.iterator();
                while (it.hasNext()) {
                    com.air.advantage.aaservice.data.DataMyLights.BackupLight b = it.next();
                    if (b != null && lightId.equals(b.id)) {
                        dataLight.name = b.name;
                        // Default state for new/backup
                        dataLight.state = "off";
                        if (isRelayModule) {
                            dataLight.value = null;
                            dataLight.relay = Boolean.TRUE;
                        } else {
                            dataLight.value = 80;
                            dataLight.relay = null;
                        }
                        addToGroup(masterData, b.groupId, dataLight, 99);
                        it.remove();
                        restored = true;
                        break;
                    }
                }
            }
            if (!restored) {
                dataLight.name = "Light " + (masterData.myLights.lights.size() + 1 + (masterData.myLights.backupLights != null ? masterData.myLights.backupLights.size() : 0));
                dataLight.state = "off";
                if (isRelayModule) {
                    dataLight.value = null;
                    dataLight.relay = Boolean.TRUE;
                } else {
                    dataLight.value = 80;
                    dataLight.relay = null;
                }
                addToGroup(masterData, "", dataLight, 99);
            }
            dataLight.nextPollTime = System.currentTimeMillis() + (isRFDevice ? 70 * 1000 : NON_RF_EXPIRY_TIME_SECONDS * 1000);
            masterData.myLights.lights.put(lightId, dataLight);
            return true;
        }
        return changed;
    }
}
