package com.air.advantage.cbmessages;

public class CANMessageLighting01LmControlMessage extends CANMessageLighting {
    public enum LightState {
        OFF(0),
        ON(1);

        private final int value;

        LightState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    private static final class BrightnessLevel {
        final int whole;
        final int mantissa; // e.g., for 0.1, whole=0, mantissa=1

        BrightnessLevel(int whole, int mantissa) {
            this.whole = whole;
            this.mantissa = mantissa;
        }

        double toDouble() {
            return whole + mantissa / 10.0;
        }
    }

    private static final BrightnessLevel[] brightnessLevels = {
        new BrightnessLevel(0, 0),
        new BrightnessLevel(0, 1),
        new BrightnessLevel(0, 2),
        new BrightnessLevel(0, 3),
        new BrightnessLevel(0, 8),
        new BrightnessLevel(1, 6),
        new BrightnessLevel(2, 7),
        new BrightnessLevel(4, 3),
        new BrightnessLevel(6, 4),
        new BrightnessLevel(9, 1),
        new BrightnessLevel(12, 5),
        new BrightnessLevel(16, 6),
        new BrightnessLevel(21, 6),
        new BrightnessLevel(27, 5),
        new BrightnessLevel(34, 3),
        new BrightnessLevel(42, 2),
        new BrightnessLevel(51, 2),
        new BrightnessLevel(61, 4),
        new BrightnessLevel(72, 9),
        new BrightnessLevel(85, 7),
        new BrightnessLevel(100, 0)
    };

    private int roomNumber;
    private LightState lightState;
    private int brightnessLevel;

    public CANMessageLighting01LmControlMessage() {
        super();
        this.roomNumber = 0;
        this.lightState = null;
        this.brightnessLevel = 0;
    }

    public static CANMessage deserialize(byte[] data) {
        return deserialize(data, 11);
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting01LmControlMessage msg = new CANMessageLighting01LmControlMessage();
        
        if (data.length >= offset + 2) {
            msg.roomNumber = ByteArray.parseHexValue(offset, data);
            int brightnessLevelWhole = ByteArray.parseHexValue(offset + 2, data);
            int brightnessLevelMantissa = ByteArray.parseHexValue(offset + 4, data);
            msg.lightState = ((brightnessLevelWhole & 128) == 128) ? LightState.ON : LightState.OFF;
            msg.brightnessLevel = getBrightnessLevelIndex(brightnessLevelWhole & 127,brightnessLevelMantissa);


        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(roomNumber, data, offset);
        BrightnessLevel brightness = getBrightnessLevel(brightnessLevel);
        int brightnessLevelWhole = brightness.whole;
        int brightnessLevelMantissa = brightness.mantissa;
        int brightnessInt = (lightState == LightState.ON ? 128 : 0) | (brightnessLevelWhole & 127);
        ByteArray.toHexDigits(brightnessInt, data, offset + 2);
        ByteArray.toHexDigits(brightnessLevelMantissa, data, offset + 4);
        return offset + 14;
    }

    /**
     * Original functionality:
     *   - The original getBrightnessLevelIndex(double d3) method checked if d3 exactly matched any value in
     *     brightnessLevels. If a match was found, it returned the index multiplied by 5. If no match was found,
     *     it always returned 5 (the second brightness level).
     *
     * Modification:
     *   - The updated method now finds the closest value to d3 in brightnessLevels, regardless of whether d3
     *     matches exactly. It returns the index of the closest value (multiplied by 5).
     *   - This ensures that even if d3 is not exactly present in the array or is outside the range, the closest
     *     valid brightness level is selected, improving robustness and usability.
     */
    private static final int getBrightnessLevelIndex(double brightnessLevel) {
        int closestIndex = 0;
        double minDiff = Math.abs(brightnessLevel - brightnessLevels[0].toDouble());
        for (int i = 1; i < brightnessLevels.length; i++) {
            double diff = Math.abs(brightnessLevel - brightnessLevels[i].toDouble());
            if (diff < minDiff) {
                minDiff = diff;
                closestIndex = i;
            }
        }
        return closestIndex * 5;
    }

    private static final int getBrightnessLevelIndex(int whole, int mantissa) {
        int closestIndex = 0;
        double brightnessLevel = whole + (mantissa / 10.0);
        double minDiff = Math.abs(brightnessLevel - brightnessLevels[0].toDouble());
        for (int i = 1; i < brightnessLevels.length; i++) {
            double diff = Math.abs(brightnessLevel - brightnessLevels[i].toDouble());
            if (diff < minDiff) {
                minDiff = diff;
                closestIndex = i;
            }
        }
        return closestIndex * 5;
    }

    private static final BrightnessLevel getBrightnessLevel(int brightnessLevel) {
        return brightnessLevels[brightnessLevel / 5];
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public LightState getLightState() {
        return lightState;
    }
    
    public void setLightState(LightState lightState) {
        this.lightState = lightState;
    }
    
    public int getBrightnessLevel() {
        return brightnessLevel;
    }
    
    public void setBrightnessLevel(int brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }
}
