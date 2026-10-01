package com.air.advantage.canhandler;

import java.util.Map;
import java.util.TreeMap;

import org.jboss.logging.Logger;

import com.air.advantage.aaservice.data.DataLight;
import com.air.advantage.aaservice.data.DataLight.LightState;
import com.air.advantage.aaservice.data.DataLight.ModuleType;
import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessageLighting;
import com.air.advantage.cbmessages.CANMessageLighting00LmStatusMessageOld;
import com.air.advantage.cbmessages.CANMessageLighting01LmControlMessage;
import com.air.advantage.cbmessages.CANMessageLighting02LmStatusMessage;
import com.air.advantage.cbmessages.CANMessageLighting14DmControlMessage;
import com.air.advantage.cbmessages.CANMessageLighting15Rm2ControlMessage;
import com.air.advantage.cbmessages.CANMessageLighting16Rm2StatusMessage;
import com.air.advantage.cbmessages.CANMessageLighting17Rm2AddDevice;
import com.air.advantage.cbmessages.CANMessageLighting1dRm2ControlMessage;

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
        } else if (message instanceof CANMessageLighting14DmControlMessage) {
            process((CANMessageLighting14DmControlMessage) message);
        } else if (message instanceof CANMessageLighting15Rm2ControlMessage) {
            process((CANMessageLighting15Rm2ControlMessage) message);
        } else if (message instanceof CANMessageLighting16Rm2StatusMessage) {
            process((CANMessageLighting16Rm2StatusMessage) message);
        } else if (message instanceof CANMessageLighting17Rm2AddDevice) {
            process((CANMessageLighting17Rm2AddDevice) message);
        } else if (message instanceof CANMessageLighting1dRm2ControlMessage) {
            process((CANMessageLighting1dRm2ControlMessage) message);
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
        DataLight light = existingLight(uid, roomNumber);
        if (light == null) return;
        light.state = msg.getLightState() == CANMessageLighting01LmControlMessage.LightState.ON
                ? LightState.on : LightState.off;
        light.value = Math.max(0, Math.min(100, msg.getBrightnessLevel()));
        light.moduleType = ModuleType.LM;
        light.reachable = true;
    }

    private void process(CANMessageLighting14DmControlMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataLight light = existingLight(uid, msg.roomNumber);
        if (light == null) return;
        light.state = msg.lightState ? LightState.on : LightState.off;
        light.value = Math.max(0, Math.min(100, msg.dimLevel));
        light.moduleType = ModuleType.DM;
        light.reachable = true;
    }

    private void process(CANMessageLighting1dRm2ControlMessage msg) {
        String uid = msg.getUid();
        if (uid == null || uid.isEmpty()) return;
        DataLight light = existingLight(uid, msg.getRoomNumber());
        if (light == null) return;
        light.value = Math.max(0, Math.min(100, msg.getDimLevel()));
        light.state = light.value > 0 ? LightState.on : LightState.off;
        light.moduleType = ModuleType.RM2;
        light.reachable = true;
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

        TreeMap<String, CANMessageLighting02LmStatusMessage> lmStatusMessages = new TreeMap<>();

        // For each group in order
        for (String lightId : MyMasterData.masterData.myLights.lights.keySet()) {
            var light = MyMasterData.masterData.myLights.lights.get(lightId);
            if (light == null) continue;
            var uid = lightId.length() >= 7 ? lightId.substring(0, 5) : lightId;
            CANMessageLighting02LmStatusMessage statusMsg = lmStatusMessages.get(uid);

            if (statusMsg == null) {
                statusMsg = new CANMessageLighting02LmStatusMessage();
                statusMsg.setUid(uid);
                statusMsg.setDeviceType(DEVICE_TYPE);
                statusMsg.setSystemType(SYSTEM_TYPE);
                statusMsg.setMajorFWVersion(2);
                statusMsg.setMinorFWVersion(1);
                lmStatusMessages.put(uid, statusMsg);
            }

            int roomNumber = 1;
            try {
                roomNumber = lightId.length() >= 7 ? Integer.parseInt(lightId.substring(lightId.length() - 2)): 1;
            } catch (Exception e) {
            }
            if (roomNumber < 1 || roomNumber > 6) continue;
            int channelIndex = roomNumber - 1;
            int channelMask = 1 << channelIndex;

            statusMsg.setRoomExists(channelIndex, true);
            statusMsg.setValidRoom(channelIndex, true);
            statusMsg.setRelayRoom(channelIndex, Boolean.TRUE.equals(light.relay));

            statusMsg.setIsRM(Boolean.TRUE.equals(light.relay));
        }
        for (Map.Entry<String, CANMessageLighting02LmStatusMessage> entry : lmStatusMessages.entrySet()) {
            String lightId = entry.getKey();
            CANMessageLighting02LmStatusMessage statusMsg = entry.getValue();

            LOG.debug("CB: Valid LM setup message JZ4 UID: " + lightId +
                    " - roomExists: 0x" + Integer.toHexString(statusMsg.getRoomExists()) +
                    " validRooms: 0x" + Integer.toHexString(statusMsg.getValidRooms()) +
                    " relayRooms: 0x" + Integer.toHexString(statusMsg.getRelayRooms()) +
                    " infoByte: 0x" + Integer.toHexString(statusMsg.getInfoByte()));
            eventBus.publish("communication-send-can", io.vertx.core.json.JsonObject.mapFrom(statusMsg));
        }


        // For each group in order
        for (String lightId : MyMasterData.masterData.myLights.lights.keySet()) {
            var light = MyMasterData.masterData.myLights.lights.get(lightId);
            if (light == null) continue;
            var uid = lightId.length() >= 7 ? lightId.substring(0, 5) : lightId;
            int roomNumber = 1;
            try {
                roomNumber = lightId.length() >= 7 ? Integer.parseInt(lightId.substring(lightId.length() - 2)): 1;
            } catch (Exception e) {
            }
            if (roomNumber < 1 || roomNumber > 6) continue;
            int channelIndex = roomNumber - 1;


            // Send JZ1 (Control Message) for this light if LM
            if (light.moduleType == ModuleType.LM) {
                CANMessageLighting01LmControlMessage controlMsg = new CANMessageLighting01LmControlMessage();
                controlMsg.setUid(uid);
                controlMsg.setDeviceType(DEVICE_TYPE);
                controlMsg.setSystemType(SYSTEM_TYPE);
                controlMsg.setRoomNumber(roomNumber);
                if (light.state == LightState.on) {
                    controlMsg.setLightState(CANMessageLighting01LmControlMessage.LightState.ON);
                } else {
                    controlMsg.setLightState(CANMessageLighting01LmControlMessage.LightState.OFF);
                }
                int brightness = (light.value != null) ? light.value : 0;
                controlMsg.setBrightnessLevel(Math.min(100, Math.max(0, brightness)));
                LOG.debug("CB: Sending JZ1 control for light " + lightId +
                        " state: " + light.state + " brightness: " + brightness);
                eventBus.publish("communication-send-can", io.vertx.core.json.JsonObject.mapFrom(controlMsg));
            }

                // If RM2, send RM2-specific messages
                if (light.moduleType == ModuleType.RM2 || light.moduleType == ModuleType.RM) {
                    // JZ16 (RM2 DIP Configuration)
                    CANMessageLighting16Rm2StatusMessage dipMsg = new CANMessageLighting16Rm2StatusMessage();
                    dipMsg.setUid(uid);
                    dipMsg.setDeviceType(DEVICE_TYPE);
                    dipMsg.setSystemType(SYSTEM_TYPE);

                    int[] dipStates = new int[6];
                    for (int d = 0; d < 6; d++) dipStates[d] = 10; // default disabled
                    // Set DIP state for this channel
                    switch (light.deviceType) {
                        case "blind": dipStates[channelIndex] = 1; break;
                        case "relay": dipStates[channelIndex] = 8; break;
                        case "dimmer": dipStates[channelIndex] = 9; break;
                        case "disabled": dipStates[channelIndex] = 10; break;
                        default: dipStates[channelIndex] = 8;
                    }
                    dipMsg.setDip1State(dipStates[0]);
                    dipMsg.setDip2State(dipStates[1]);
                    dipMsg.setDip3State(dipStates[2]);
                    dipMsg.setDip4State(dipStates[3]);
                    dipMsg.setDip5State(dipStates[4]);
                    dipMsg.setDip6State(dipStates[5]);
                    dipMsg.setInfoByte(0);
                    LOG.debug("CB: Sending JZ16 RM2 DIP config for light " + lightId);
                    eventBus.publish("communication-send-can", io.vertx.core.json.JsonObject.mapFrom(dipMsg));

                    // JZ17 (RM2 Add Device / Version Info)
                    CANMessageLighting17Rm2AddDevice addDeviceMsg = new CANMessageLighting17Rm2AddDevice();
                    addDeviceMsg.setUid(uid);
                    addDeviceMsg.setDeviceType(DEVICE_TYPE);
                    addDeviceMsg.setSystemType(SYSTEM_TYPE);
                    addDeviceMsg.setMajorFWVersion(2);
                    addDeviceMsg.setMinorFWVersion(1);
                    int rm2AddDeviceInfo = 0;
                    if (light.moduleType == ModuleType.DM) {
                        rm2AddDeviceInfo = 0x80;
                    } else if (light.moduleType == ModuleType.GDM) {
                        rm2AddDeviceInfo = 0x10;
                    }
                    addDeviceMsg.setInfoByte(rm2AddDeviceInfo);
                    LOG.debug("CB: Sending JZ17 RM2 add device for light " + lightId);
                    eventBus.publish("communication-send-can", io.vertx.core.json.JsonObject.mapFrom(addDeviceMsg));

                    // JZ15 (RM2 Control Message)
                    CANMessageLighting15Rm2ControlMessage rm2ControlMsg = new CANMessageLighting15Rm2ControlMessage();
                    rm2ControlMsg.setUid(uid);
                    rm2ControlMsg.setDeviceType(DEVICE_TYPE);
                    rm2ControlMsg.setSystemType(SYSTEM_TYPE);
                    rm2ControlMsg.setRoomNumber(roomNumber);
                    if (light.state == LightState.on) {
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
                    LOG.debug("CB: Sending JZ15 RM2 control for light " + lightId);
                    eventBus.publish("communication-send-can", io.vertx.core.json.JsonObject.mapFrom(rm2ControlMsg));
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
        DataLight light = existingLight(uid, roomNumber);
        if (light == null) return;
        int state = msg.getLightState();
        int dimLevel = Math.max(0, Math.min(100, msg.getDimLevel()));
        light.state = state == 1 ? LightState.on : LightState.off;
        light.value = state == 1 ? dimLevel : 0;
        light.moduleType = ModuleType.RM2;
        light.lowBattery = msg.isLowBattery();
        light.calibrated = msg.isCalibrated();
        light.poll = msg.isPoll();
        light.reachable = true;
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

    private DataLight existingLight(String uid, int roomNumber) {
        if (roomNumber < 1 || roomNumber > 6 || MyMasterData.masterData == null ||
                MyMasterData.masterData.myLights == null || MyMasterData.masterData.myLights.lights == null) {
            return null;
        }
        return MyMasterData.masterData.myLights.lights.get(uid + String.format("%02d", roomNumber));
    }
}
