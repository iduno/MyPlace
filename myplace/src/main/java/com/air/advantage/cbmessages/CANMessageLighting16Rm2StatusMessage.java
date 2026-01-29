package com.air.advantage.cbmessages;

public class CANMessageLighting16Rm2StatusMessage extends CANMessageLighting {
    public int dip1State;
    public int dip2State;
    public int dip3State;
    public int dip4State;
    public int dip5State;
    public int dip6State;
    public int infoByte;
    // DIP States
    // 1 BUTTON TYPE UP DOWN
    // 2 BUTTON TYPE UP DOWN
    // 3 BUTTON TYPE UP DOWN GARRAGE
    // 8 BUTTON TYPE ON OFF
    // 9 BUTTON TYPE DIMMABLE
    // 10 BUTTON TYPE NONE
    
    public CANMessageLighting16Rm2StatusMessage() {
        super();
        this.messageType = MessageType.RM2_DIP_THING;
        this.dip1State = 0;
        this.dip2State = 0;
        this.dip3State = 0;
        this.dip4State = 0;
        this.dip5State = 0;
        this.dip6State = 0;
        this.infoByte = 0;
    }
    
    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting16Rm2StatusMessage msg = new CANMessageLighting16Rm2StatusMessage();
        
        if (data.length >= offset + 2) {
            msg.dip1State = ByteArray.parseHexValue(offset, data);
            msg.dip2State = ByteArray.parseHexValue(offset + 2, data);
            msg.dip3State = ByteArray.parseHexValue(offset + 4, data);
            msg.dip4State = ByteArray.parseHexValue(offset + 6, data);
            msg.dip5State = ByteArray.parseHexValue(offset + 8, data);
            msg.dip6State = ByteArray.parseHexValue(offset + 10, data);
            msg.infoByte = ByteArray.parseHexValue(offset + 12, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(dip1State, data, offset);
        ByteArray.toHexDigits(dip2State, data, offset + 2);
        ByteArray.toHexDigits(dip3State, data, offset + 4);
        ByteArray.toHexDigits(dip4State, data, offset + 6);
        ByteArray.toHexDigits(dip5State, data, offset + 8);
        ByteArray.toHexDigits(dip6State, data, offset + 10);
        ByteArray.toHexDigits(infoByte, data, offset + 12);
        return offset + 14;
    }
    
    public int getDip1State() {
        return dip1State;
    }
    
    public void setDip1State(int dip1State) {
        this.dip1State = dip1State;
    }
    
    public int getDip2State() {
        return dip2State;
    }
    
    public void setDip2State(int dip2State) {
        this.dip2State = dip2State;
    }
    
    public int getDip3State() {
        return dip3State;
    }
    
    public void setDip3State(int dip3State) {
        this.dip3State = dip3State;
    }
    
    public int getDip4State() {
        return dip4State;
    }
    
    public void setDip4State(int dip4State) {
        this.dip4State = dip4State;
    }
    
    public int getDip5State() {
        return dip5State;
    }
    
    public void setDip5State(int dip5State) {
        this.dip5State = dip5State;
    }
    
    public int getDip6State() {
        return dip6State;
    }
    
    public void setDip6State(int dip6State) {
        this.dip6State = dip6State;
    }
    
    public int getInfoByte() {
        return infoByte;
    }
    
    public void setInfoByte(int infoByte) {
        this.infoByte = infoByte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageLighting16Rm2StatusMessage)) return false;
        if (!super.equals(o)) return false;
        CANMessageLighting16Rm2StatusMessage that = (CANMessageLighting16Rm2StatusMessage) o;
        return dip1State == that.dip1State && dip2State == that.dip2State && dip3State == that.dip3State && dip4State == that.dip4State && dip5State == that.dip5State && dip6State == that.dip6State && infoByte == that.infoByte;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), dip1State, dip2State, dip3State, dip4State, dip5State, dip6State, infoByte);
    }

}
