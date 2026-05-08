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

public class HandlerLightingCB extends Handler {
    private static final Logger LOG = Logger.getLogger(HandlerLightingCB.class);
    private static final CANMessage.DeviceType DEVICE_TYPE = CANMessage.DeviceType.RF_CONTROLLER;
    private static final CANMessage.SystemType SYSTEM_TYPE = CANMessage.SystemType.LIGHTING;
    
    public HandlerLightingCB(MyMasterData myMasterData, EventBus eventBus) {
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
        LOG.warn("Unhandled lighting CB message type: " + lightingMsg.getClass().getSimpleName());
    }
    
    // JZ0 - Old LM Status Message (deprecated, use JZ2)
    private void process(CANMessageLighting00LmStatusMessageOld msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("CB: Rejected old LM status message request - invalid UID");
            return;
        }
        
        LOG.debug("CB: Processing old LM status message request for UID " + uid);
        // Generate response based on master data - legacy format
        // Not implemented as JZ2 is preferred
    }
    
    // JZ1 - LM Control Message (Controller -> CB)
    private void process(CANMessageLighting01LmControlMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("CB: Rejected LM control message - invalid UID");
            return;
        }
        
        int roomNumber = msg.getRoomNumber();
        LOG.debug("CB: Received LM control message for UID " + uid + " room " + roomNumber);
        // CB would apply control commands to lights - mock implementation
    }
    
    // JZ2 - LM Status Message (CB -> Controller - setup message with room configuration)
    private void process(CANMessageLighting02LmStatusMessage msg) {
        // Iterate over all groups and lights in myLights
        if (myMasterData == null || MyMasterData.masterData == null ||
            MyMasterData.masterData.myLights == null ||
            MyMasterData.masterData.myLights.groups == null ||
            MyMasterData.masterData.myLights.groupsOrder == null ||
            MyMasterData.masterData.myLights.lights == null) {
            LOG.debug("CB: No lighting data available for LM status message");
            return;
        }

        // For each group in order
        for (String groupId : MyMasterData.masterData.myLights.groupsOrder) {
            var group = MyMasterData.masterData.myLights.groups.get(groupId);
            if (group == null || group.lightsOrder == null) continue;

            // For each light in group order
            for (int i = 0; i < group.lightsOrder.size(); i++) {
                String lightId = group.lightsOrder.get(i);
                DataLight light = MyMasterData.masterData.myLights.lights.get(lightId);
                if (light == null) continue;

                // Parse UID and light number from lightId
                String uid = lightId.length() >= 7 ? lightId.substring(0, 5) : lightId;
                int roomNumber = 1;
                try {
                    roomNumber = Integer.parseInt(lightId.substring(lightId.length() - 2));
                } catch (Exception e) {
                    // fallback to 1 if parse fails
                }

                // Compose status message for this light
                int roomExists = 1;
                int validRooms = 1;
                int relayRooms = ("relay".equals(light.deviceType)) ? 1 : 0;
                boolean isRM2 = "RM2".equals(light.moduleType) || "RM".equals(light.moduleType);
                String moduleType = (light.moduleType != null) ? light.moduleType : "LM";

                CANMessageLighting02LmStatusMessage statusMsg = new CANMessageLighting02LmStatusMessage();
                statusMsg.setUid(uid);
                statusMsg.setDeviceType(DEVICE_TYPE);
                statusMsg.setSystemType(SYSTEM_TYPE);
                statusMsg.setMajorFWVersion(2);
                statusMsg.setMinorFWVersion(1);
                statusMsg.setRoomExists(roomExists);
                statusMsg.setValidRooms(validRooms);
                statusMsg.setRelayRooms(relayRooms);
                int infoByte = isRM2 ? 0x80 : 0x00;
                statusMsg.setInfoByte(infoByte);

                LOG.debug("CB: Sending JZ2 status for group " + groupId + " light " + lightId +
                        " - roomExists: 0x" + Integer.toHexString(roomExists) +
                        " validRooms: 0x" + Integer.toHexString(validRooms) +
                        " relayRooms: 0x" + Integer.toHexString(relayRooms) +
                        " infoByte: 0x" + Integer.toHexString(infoByte));
                eventBus.publish("communication-send-can", statusMsg);

                // Send JZ1 (Control Message) for this light if LM
                if ("LM".equals(moduleType)) {
                    CANMessageLighting01LmControlMessage controlMsg = new CANMessageLighting01LmControlMessage();
                    controlMsg.setUid(uid);
                    controlMsg.setDeviceType(DEVICE_TYPE);
                    controlMsg.setSystemType(SYSTEM_TYPE);
                    controlMsg.setRoomNumber(roomNumber);
                    if ("on".equals(light.state)) {
                        controlMsg.setLightState(CANMessageLighting01LmControlMessage.LightState.ON);
                    } else {
                        controlMsg.setLightState(CANMessageLighting01LmControlMessage.LightState.OFF);
                    }
                    int brightness = (light.value != null) ? light.value : 0;
                    controlMsg.setBrightnessLevel(Math.min(100, Math.max(0, brightness)));
                    LOG.debug("CB: Sending JZ1 control for group " + groupId + " light " + lightId +
                            " state: " + light.state + " brightness: " + brightness);
                    eventBus.publish("communication-send-can", controlMsg);
                }

                // If RM2, send RM2-specific messages
                if (isRM2) {
                    // JZ16 (RM2 DIP Configuration)
                    CANMessageLighting16Rm2StatusMessage dipMsg = new CANMessageLighting16Rm2StatusMessage();
                    dipMsg.setUid(uid);
                    dipMsg.setDeviceType(DEVICE_TYPE);
                    dipMsg.setSystemType(SYSTEM_TYPE);
                    int rm2InfoByte = 1; // Only this channel enabled
                    int[] dipStates = new int[6];
                    for (int d = 0; d < 6; d++) dipStates[d] = 10; // default disabled
                    // Set DIP state for this channel
                    switch (light.deviceType) {
                        case "blind": dipStates[0] = 1; break;
                        case "relay": dipStates[0] = 8; break;
                        case "dimmer": dipStates[0] = 9; break;
                        case "disabled": dipStates[0] = 10; break;
                        default: dipStates[0] = 8;
                    }
                    dipMsg.setDip1State(dipStates[0]);
                    dipMsg.setDip2State(dipStates[1]);
                    dipMsg.setDip3State(dipStates[2]);
                    dipMsg.setDip4State(dipStates[3]);
                    dipMsg.setDip5State(dipStates[4]);
                    dipMsg.setDip6State(dipStates[5]);
                    dipMsg.setInfoByte(rm2InfoByte);
                    LOG.debug("CB: Sending JZ16 RM2 DIP config for group " + groupId + " light " + lightId);
                    eventBus.publish("communication-send-can", dipMsg);

                    // JZ17 (RM2 Add Device / Version Info)
                    CANMessageLighting17Rm2AddDevice addDeviceMsg = new CANMessageLighting17Rm2AddDevice();
                    addDeviceMsg.setUid(uid);
                    addDeviceMsg.setDeviceType(DEVICE_TYPE);
                    addDeviceMsg.setSystemType(SYSTEM_TYPE);
                    addDeviceMsg.setMajorFWVersion(2);
                    addDeviceMsg.setMinorFWVersion(1);
                    int rm2AddDeviceInfo = 0;
                    if ("DM".equals(moduleType)) {
                        rm2AddDeviceInfo = 0x80;
                    } else if ("GDM".equals(moduleType)) {
                        rm2AddDeviceInfo = 0x10;
                    }
                    addDeviceMsg.setInfoByte(rm2AddDeviceInfo);
                    LOG.debug("CB: Sending JZ17 RM2 add device for group " + groupId + " light " + lightId);
                    eventBus.publish("communication-send-can", addDeviceMsg);

                    // JZ15 (RM2 Control Message)
                    CANMessageLighting15Rm2ControlMessage rm2ControlMsg = new CANMessageLighting15Rm2ControlMessage();
                    rm2ControlMsg.setUid(uid);
                    rm2ControlMsg.setDeviceType(DEVICE_TYPE);
                    rm2ControlMsg.setSystemType(SYSTEM_TYPE);
                    rm2ControlMsg.setRoomNumber(roomNumber);
                    if ("on".equals(light.state)) {
                        rm2ControlMsg.setLightState(1);
                    } else {
                        rm2ControlMsg.setLightState(0);
                    }
                    int dimLevel = (light.value != null) ? light.value : 0;
                    rm2ControlMsg.setDimLevel(Math.min(100, Math.max(0, dimLevel)));
                    rm2ControlMsg.setSwitchState(0);
                    rm2ControlMsg.setNodeDipState(0);
                    rm2ControlMsg.setDimOffset(0);
                    rm2ControlMsg.setStatusState(0);
                    LOG.debug("CB: Sending JZ15 RM2 control for group " + groupId + " light " + lightId);
                    eventBus.publish("communication-send-can", rm2ControlMsg);
                }
            }
        }
    }
    
    // JZ15 - RM2 Control Message (Controller -> CB - Thing state)
    private void process(CANMessageLighting15Rm2ControlMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("CB: Rejected RM2 control message - invalid UID");
            return;
        }
        
        int roomNumber = msg.getRoomNumber();
        LOG.debug("CB: Received RM2 control message for UID " + uid + " channel " + roomNumber);
        // CB would apply control commands to RM2 devices - mock implementation
    }
    
    // JZ16 - RM2 Status Message (CB -> Controller - Module DIP configuration)
    private void process(CANMessageLighting16Rm2StatusMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("CB: Rejected RM2 status message request - invalid UID");
            return;
        }
        
        LOG.debug("CB: Generating RM2 DIP configuration for UID " + uid);
        // Generate DIP configuration from master data - mock implementation
    }
    
    // JZ17 - RM2 Add Device (CB -> Controller - Version/setup info)
    private void process(CANMessageLighting17Rm2AddDevice msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) {
            LOG.debug("CB: Rejected RM2 add device request - invalid UID");
            return;
        }
        
        LOG.debug("CB: Generating RM2 add device response for UID " + uid);
        // Generate add device response - mock implementation
    }
}
