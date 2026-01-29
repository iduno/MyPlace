package com.air.advantage.cbmessages;

public class CANMessageLighting15Rm2ControlMessage extends CANMessageLighting {
    private int roomNumber;
    private int lightState;
    private int switchState; //0,1,2,3,8,9,10,
    private int dimLevel;
    private int nodeDipState;
    private int dimOffset;
    private int statusState;
    private boolean lowBattery;
    private boolean isCalibrated;
    private boolean isPoll;
    
    public CANMessageLighting15Rm2ControlMessage() {
        super();
        this.messageType = MessageType.RM2_THING_STATE;
        this.roomNumber = 0;
        this.lightState = 0;
        this.switchState = 0;
        this.dimLevel = 0;
        this.nodeDipState = 0;
        this.dimOffset = 0;
        this.statusState = 0;
        this.lowBattery = false;
        this.isCalibrated = false;
        this.isPoll = false;
    }
    
    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting15Rm2ControlMessage msg = new CANMessageLighting15Rm2ControlMessage();
        
        if (data.length >= offset + 2) {
            msg.roomNumber = ByteArray.parseHexValue(offset, data);
            msg.lightState = ByteArray.parseHexValue(offset + 2, data);
            msg.dimLevel = ByteArray.parseHexValue(offset + 4, data);
            msg.switchState = ByteArray.parseHexValue(offset + 6, data);
            msg.nodeDipState = ByteArray.parseHexValue(offset + 8, data);
            msg.dimOffset = ByteArray.parseHexValue(offset + 10, data);
            msg.statusState = ByteArray.parseHexValue(offset + 12, data);
            msg.lowBattery = ((msg.statusState & 0x80) == 0x80);
            msg.isCalibrated = ((msg.statusState & 0x40) == 0x40);
            msg.isPoll = ((msg.statusState & 0x20) == 0x20);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(roomNumber, data, offset);
        ByteArray.toHexDigits(lightState, data, offset + 2);
        ByteArray.toHexDigits(dimLevel, data, offset + 4);
        ByteArray.toHexDigits(switchState, data, offset + 6);
        ByteArray.toHexDigits(nodeDipState, data, offset + 8);
        ByteArray.toHexDigits(dimOffset, data, offset + 10);
        ByteArray.toHexDigits(statusState, data, offset + 12);
        return offset + 14;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public int getLightState() {
        return lightState;
    }
    
    public void setLightState(int lightState) {
        this.lightState = lightState;
    }
    
    public int getSwitchState() {
        return switchState;
    }
    
    public void setSwitchState(int switchState) {
        this.switchState = switchState;
    }
    
    public int getDimLevel() {
        return dimLevel;
    }
    
    public void setDimLevel(int dimLevel) {
        this.dimLevel = dimLevel;
    }
    
    public int getNodeDipState() {
        return nodeDipState;
    }
    
    public void setNodeDipState(int nodeDipState) {
        this.nodeDipState = nodeDipState;
    }
    
    public int getDimOffset() {
        return dimOffset;
    }
    
    public void setDimOffset(int dimOffset) {
        this.dimOffset = dimOffset;
    }
    
    public int getStatusState() {
        return statusState;
    }
    
    public void setStatusState(int statusState) {
        this.statusState = statusState;
        this.lowBattery = ((statusState & 0x80) == 0x80);
        this.isCalibrated = ((statusState & 0x40) == 0x40);
        this.isPoll = ((statusState & 0x20) == 0x20);
    }

    public void setLowBattery(boolean lowBattery) {
        this.lowBattery = lowBattery;
        if (lowBattery) {
            this.statusState |= 0x80;
        } else {
            this.statusState &= ~0x80;
        }
    }

    public void setCalibrated(boolean isCalibrated) {
        this.isCalibrated = isCalibrated;
        if (isCalibrated) {
            this.statusState |= 0x40;
        } else {
            this.statusState &= ~0x40;
        }
    }

    public void setPoll(boolean isPoll) {
        this.isPoll = isPoll;
        if (isPoll) {
            this.statusState |= 0x20;
        } else {
            this.statusState &= ~0x20;
        }
    }
    
    public boolean isLowBattery() {
        return lowBattery;
    }
    
    public boolean isCalibrated() {
        return isCalibrated;
    }
    
    public boolean isPoll() {
        return isPoll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageLighting15Rm2ControlMessage)) return false;
        if (!super.equals(o)) return false;
        CANMessageLighting15Rm2ControlMessage that = (CANMessageLighting15Rm2ControlMessage) o;
        return roomNumber == that.roomNumber &&
               lightState == that.lightState &&
               switchState == that.switchState &&
               dimLevel == that.dimLevel &&
               nodeDipState == that.nodeDipState &&
               dimOffset == that.dimOffset &&
               statusState == that.statusState &&
               lowBattery == that.lowBattery &&
               isCalibrated == that.isCalibrated &&
               isPoll == that.isPoll;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), roomNumber, lightState, switchState, dimLevel, nodeDipState, dimOffset, statusState, lowBattery, isCalibrated, isPoll);
    }

}
