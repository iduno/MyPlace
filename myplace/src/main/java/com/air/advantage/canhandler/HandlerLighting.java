package com.air.advantage.canhandler;

import org.jboss.logging.Logger;

import com.air.advantage.aaservice.data.DataLight;
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
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("Rejected old LM status message - invalid UID");
            return;
        }
        
        LOG.warn("Warning: Received deprecated JZ0 message for UID " + uid + 
                ", consider upgrading firmware to use JZ2");
        
        // Process as best we can with old format
    }
    
    // JZ1 - LM Control Message
    private void process(CANMessageLighting01LmControlMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("Rejected LM control message - invalid UID");
            return;
        }
        
        int roomNumber = msg.getRoomNumber();
        if (roomNumber < 1 || roomNumber > 63) {
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
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("Rejected LM status message - invalid UID");
            return;
        }
        
        boolean isRF = isRFModule(msg.getSystemType());
        long expiryTime = System.currentTimeMillis() + 
            ((isRF ? RF_EXPIRY_TIME_SECONDS : NON_RF_EXPIRY_TIME_SECONDS) * 1000);
        
        boolean[] roomExists = msg.getRoomExists();
        boolean[] validRooms = msg.getValidRooms();
        boolean[] relayRooms = msg.getRelayRooms();
        int version = msg.getMajorFWVersion();
        
        LOG.debug("Valid LM setup message (JZ2). UID - " + uid + 
                " version - " + version + "." + msg.getMinorFWVersion());
        
        // Process each room
        for (int i = 0; i < roomExists.length && i < 8; i++) {
            if (roomExists[i]) {
                int roomNumber = i + 1;
                String lightId = uid + String.format("%02d", roomNumber);
                
                DataLight light = getOrCreateLight(lightId);
                light.moduleType = "LM";
                light.deviceType = relayRooms[i] ? "relay" : "dimmer";
                light.nextPollTime = expiryTime;
                
                // Initialize state if new
                if (light.state == null) {
                    light.state = "off";
                }
                if (light.value == null && !relayRooms[i]) {
                    light.value = 80; // Default brightness
                }
                
                LOG.debug("Enabled light - room " + roomNumber + 
                        " type: " + light.deviceType + 
                        " valid: " + validRooms[i]);
            }
        }
    }
    
    // JZ15 - RM2 Control Message (Thing state)
    private void process(CANMessageLighting15Rm2ControlMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("Rejected RM2 control message - invalid UID");
            return;
        }
        
        int roomNumber = msg.getRoomNumber();
        if (roomNumber < 1 || roomNumber > 6) {
            LOG.debug("Rejected RM2 control message - invalid room/channel number: " + roomNumber);
            return;
        }
        
        String lightId = uid + String.format("%02d", roomNumber);
        
        DataLight light = getOrCreateLight(lightId);
        
        // Map RM2 states
        int state = msg.getLightState();
        int dimLevel = msg.getDimLevel();
        
        if (state == 0) {
            light.state = "off";
            light.value = 0;
        } else if (state == 1) {
            light.state = "on";
            light.value = (dimLevel > 0 && dimLevel <= 100) ? dimLevel : 100;
        } else if (state == 2) {
            // Stop command - maintain current state
        }
        
        light.moduleType = "RM2";
        
        LOG.debug("Valid RM2 control message. UID - " + uid + 
                " channel - " + roomNumber + 
                " state - " + state + 
                " dimLevel - " + dimLevel);
    }
    
    // JZ16 - RM2 Status Message (Module DIP configuration)
    private void process(CANMessageLighting16Rm2StatusMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("Rejected RM2 status message - invalid UID");
            return;
        }
        
        int infoByte = msg.getInfoByte();
        
        LOG.debug("JZ38 RM2 module info byte: " + infoByte + " for uid:" + uid);
        
        boolean isRF = isRFModule(msg.getSystemType());
        long expiryTime = System.currentTimeMillis() + 
            ((isRF ? RF_EXPIRY_TIME_SECONDS : NON_RF_EXPIRY_TIME_SECONDS) * 1000);
        
        // Process each channel (1-6)
        int[] dipStates = {msg.getDip1State(), msg.getDip2State(), msg.getDip3State(), 
                           msg.getDip4State(), msg.getDip5State(), msg.getDip6State()};
        for (int i = 0; i < 6; i++) {
            int roomNumber = i + 1;
            int dipState = dipStates[i];
            
            // Check if channel is enabled in infoByte (bit i)
            boolean isEnabled = ((infoByte >> i) & 1) == 1;
            
            if (isEnabled) {
                String lightId = uid + String.format("%02d", roomNumber);
                
                // Map DIP state to device type
                String deviceType = mapDipStateToDeviceType(dipState);
                
                DataLight light = getOrCreateLight(lightId);
                light.deviceType = deviceType;
                light.moduleType = "RM2";
                light.nextPollTime = expiryTime;
                
                // Initialize state if new
                if (light.state == null) {
                    light.state = "off";
                }
                
                LOG.debug("Enabled RM2 channel " + roomNumber + 
                        " dipState: " + dipState + 
                        " deviceType: " + deviceType);
            }
        }
    }
    
    // JZ17 - RM2 Add Device (Version/setup info)
    private void process(CANMessageLighting17Rm2AddDevice msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("Rejected RM2 add device message - invalid UID");
            return;
        }
        
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
}
