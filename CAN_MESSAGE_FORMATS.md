# MyPlace CAN Message Format Documentation

## Overview

This document describes the Controller Area Network (CAN) message formats used in the MyPlace system for communication between various components such as air conditioners, lighting systems, and controllers. CAN messages in this system follow a fixed-length format with specific fields for system identification, device type, and payload data.

## Message Structure

Every CAN message follows a fixed record length format with the following structure:

| Field | Size (bytes) | Description |
|-------|--------------|-------------|
| System Type | 2 | Identifies the subsystem (e.g., Aircon, Lighting) |
| Device Type | 2 | Identifies the device type within the subsystem |
| UID | 5 | Unique identifier for the device |
| Message Type | 2 | Specific message type within the subsystem |
| Payload | Variable | Message-specific data |

Total message size is generally 25 bytes, with each message serialized as a string of hexadecimal digits.

## System Types

| System Type | Hex Code | Description |
|-------------|----------|-------------|
| LIGHTING | 02 | Lighting control system |
| CAN_AIRCON | 07 | Air conditioning system (CAN protocol) |
| RF_AIRCON | 08 | Air conditioning system (RF protocol) |
| UNKNOWN | 00 | Unknown system type |

## Device Types

| Device Type | Hex Code | Description |
|-------------|----------|-------------|
| UNKNOWN | 00 | Unknown device type |
| CONTROL_BOARD | 01 | Main control board |
| RF_CONTROLLER | 02 | RF controller device |
| AIRCON_1 | 03 | Air conditioner unit type 1 |
| AIRCON_2 | 04 | Air conditioner unit type 2 |

## Air Conditioning Message Types

Air conditioning messages (`CANMessageAircon`) use the following message types:

| Message Type | Hex Code | Description |
|--------------|----------|-------------|
| ZONE_INFORMATION | 01 | Information about zones in the air conditioning system |
| UNIT_TYPE_INFORMATION | 02 | Information about the air conditioner unit type |
| ZONE_STATE | 03 | Current state of a specific zone |
| ZONE_CONFIGURATION | 04 | Configuration details for a specific zone |
| AIRCON_STATE | 05 | Current state of the air conditioner |
| CB_STATUS | 06 | Control board status |
| CB_STATUS_MESSAGE | 07 | Control board status message |
| CB_ERROR | 08 | Control board error information |
| ACTIVATION_CODE_INFORMATION | 09 | Activation code details |
| MID_INFORMATION | 0a | MID information |
| ZONE_SENSOR_PAIRING | 12 | Zone sensor pairing information |
| INFO_BYTE | 13 | Information byte |
| RF_DEVICE_PAIRING | 26 | RF device pairing information |
| RF_DEVICE_CALIBRATION | 27 | RF device calibration information |
| UNKNOWN | 00 | Unknown message type |

## Lighting Message Types

Lighting messages (`CANMessageLighting`) use the following message types:

| Message Type | Hex Code | Description |
|--------------|----------|-------------|
| LM_SETUP_OLD | 00 | Legacy lighting module setup information |
| LM_UPDATE_BRIGHTNESS_LEVEL | 01 | Update brightness level for a light |
| LM_SETUP | 02 | Lighting module setup information |
| RM2_THING_STATE | 15 | RM2 thing state information |
| RM2_DIP_THING | 16 | RM2 DIP switch configuration |
| RM2_STATUS_ADD_DEVICE | 17 | RM2 status for adding a device |

## Detailed Message Formats

### CANMessageAircon01ZoneInformation

This message provides information about zones in the air conditioning system.

| Field | Type | Description |
|-------|------|-------------|
| destination | int | Destination identifier (CB=11, TABLET=20) |
| numZones | int | Total number of zones |
| numConstantZones | int | Number of constant zones |
| constantZone1 | int | First constant zone identifier |
| constantZone2 | int | Second constant zone identifier |
| constantZone3 | int | Third constant zone identifier |
| filterCleanStatus | int | Filter clean status |

### CANMessageAircon02UnitTypeInformation

This message provides information about the air conditioner unit type.

| Field | Type | Description |
|-------|------|-------------|
| unitType | UnitType | Type of air conditioner unit (DAIKIN=0x11, PANASONIC=0x12, FUJITSU=0x13, SAMSUNG_DVM=0x19) |
| activationStatus | CodeStatus | Activation status (NO_CODE=0, CODE_ENABLED=1, EXPIRED=2) |
| fwMajor | int | Firmware major version |
| fwMinor | int | Firmware minor version |

### CANMessageAircon03ZoneState

This message provides the current state of a specific zone.

| Field | Type | Description |
|-------|------|-------------|
| zoneNumber | int | Zone identifier |
| zoneState | ZoneState | Zone state (CLOSE=0, OPEN=1) |
| zonePercent | int | Zone percentage open |
| zoneType | int | Zone type |
| setTemp | float | Set temperature |
| measuredTemp | float | Measured temperature |

### CANMessageAircon04ZoneConfiguration

This message provides configuration details for a specific zone.

| Field | Type | Description |
|-------|------|-------------|
| zoneNumber | int | Zone identifier |
| minDamper | int | Minimum damper setting |
| maxDamper | int | Maximum damper setting |
| motionStatus | int | Motion status |
| motionConfig | int | Motion configuration |
| zoneError | int | Zone error code |
| rssi | int | Signal strength indicator |

### CANMessageAircon05AirconState

This message provides the current state of the air conditioner.

| Field | Type | Description |
|-------|------|-------------|
| systemState | SystemState | System state (OFF=0, ON=1) |
| systemMode | SystemMode | System mode (COOL=1, HEAT=2, VENT=3, AUTO=4, DRY=5, MYAUTO=6) |
| systemFan | FanState | Fan state (OFF=0, LOW=1, MEDIUM=2, HIGH=3, AUTO=4, AUTOAA=5) |
| setTemp | float | Set temperature |
| myZoneId | int | ID of the "MyZone" |
| freshAirStatus | FreshAirStatus | Fresh air status (NONE=0, ON=1, OFF=2) |
| rfSysId | int | RF system ID |

### CANMessageAircon06CBStatus

This message provides the control board status.

| Field | Type | Description |
|-------|------|-------------|
| cbFwMajor | int | Control board firmware major version |
| cbFwMinor | int | Control board firmware minor version |
| cbType | int | Control board type |
| rfFwMajor | int | RF firmware major version |

### CANMessageAircon07CbStatusMessage

This message provides an extended control board status message.

| Field | Type | Description |
|-------|------|-------------|
| cbFwMajor | int | Control board firmware major version |
| cbFwMinor | int | Control board firmware minor version |
| cbType | int | Control board type |
| rfFwMajor | int | RF firmware major version |

### CANMessageAircon08CBErrorStatus

This message provides control board error information.

| Field | Type | Description |
|-------|------|-------------|
| errorCode | String | Error code string |

### CANMessageAircon09ActivationCodeInformation

This message provides activation code details.

| Field | Type | Description |
|-------|------|-------------|
| action | int | Action type (1=set new code, 2=unlock) |
| unlockCode | int | Unlock code |
| activationTimeDays | int | Activation time in days |

### CANMessageAircon0aMidInformation

This message provides MID information.

No additional fields, used as a signaling message.

### CANMessageAircon12ZoneSensorPairing

This message provides zone sensor pairing information.

| Field | Type | Description |
|-------|------|-------------|
| sensorUID | String | Sensor UID |
| infoByte | int | Information byte |
| sensorMajorRev | int | Sensor major revision |

### CANMessageAircon13CBInfoByte

This message provides an information byte for the control board.

| Field | Type | Description |
|-------|------|-------------|
| infoByte | int | Information byte |

### CANMessageAircon26RfDevicePairing

This message provides RF device pairing information.

| Field | Type | Description |
|-------|------|-------------|
| pairingControl | int | Pairing control value |
| rfDeviceType | int | RF device type |
| channelNo | int | Channel number |

### CANMessageAircon27RfDeviceCalibration

This message provides RF device calibration information.

| Field | Type | Description |
|-------|------|-------------|
| calibrationControl | int | Calibration control value |
| channelNo | int | Channel number |
| upDownPosition | int | Up/down position value |

### CANMessageLighting00LmStatusMessageOld

This message provides legacy lighting module setup information.

| Field | Type | Description |
|-------|------|-------------|
| roomExists | boolean[8] | Array indicating if rooms exist |
| validRooms | boolean[8] | Array indicating if rooms are valid |
| version | int | Version number |

### CANMessageLighting01LmControlMessage

This message updates brightness level for a light.

| Field | Type | Description |
|-------|------|-------------|
| roomNumber | int | Room number |
| lightState | LightState | Light state (OFF=0, ON=1) |
| brightnessLevel | int | Brightness level (0-100) |

### CANMessageLighting02LmStatusMessage

This message provides lighting module setup information.

| Field | Type | Description |
|-------|------|-------------|
| majorFWVersion | int | Major firmware version |
| minorFWVersion | int | Minor firmware version |
| roomExists | boolean[8] | Array indicating if rooms exist |
| validRooms | boolean[8] | Array indicating if rooms are valid |
| relayRooms | boolean[8] | Array indicating if rooms have relay |
| infoByte | int | Information byte |

### CANMessageLighting15Rm2ControlMessage

This message provides RM2 thing state information.

| Field | Type | Description |
|-------|------|-------------|
| roomNumber | int | Room number |
| lightState | int | Light state |
| switchState | int | Switch state (0,1,2,3,8,9,10) |
| dimLevel | int | Dimming level |
| nodeDipState | int | Node DIP switch state |
| dimOffset | int | Dimming offset |
| statusState | int | Status state |
| lowBattery | boolean | Low battery indicator |
| isCalibrated | boolean | Calibration status |
| isPoll | boolean | Poll status |

### CANMessageLighting16Rm2StatusMessage

This message provides RM2 DIP switch configuration.

| Field | Type | Description |
|-------|------|-------------|
| dip1State | int | State of DIP switch 1 |
| dip2State | int | State of DIP switch 2 |
| dip3State | int | State of DIP switch 3 |
| dip4State | int | State of DIP switch 4 |
| dip5State | int | State of DIP switch 5 |
| dip6State | int | State of DIP switch 6 |
| infoByte | int | Information byte |

Button types determined by DIP switch states:
- 1: UP DOWN button type
- 2: UP DOWN button type
- 3: UP DOWN GARAGE button type
- 8: ON OFF button type
- 9: DIMMABLE button type
- 10: NONE button type

### CANMessageLighting17Rm2AddDevice

This message provides RM2 status for adding a device.

| Field | Type | Description |
|-------|------|-------------|
| majorFWVersion | int | Major firmware version |
| minorFWVersion | int | Minor firmware version |
| infoByte | int | Information byte |

## Message Serialization and Deserialization

CAN messages are serialized to and deserialized from byte arrays using the following approach:

1. Serialization:
   - Convert the message object to a byte array
   - Write system type (2 bytes)
   - Write device type (2 bytes)
   - Write UID (5 bytes)
   - Write message type (2 bytes)
   - Write message-specific payload data
   
2. Deserialization:
   - Read system type (2 bytes)
   - Read device type (2 bytes)
   - Read UID (5 bytes)
   - Read message type (2 bytes)
   - Read and parse message-specific payload data based on system and message type
   - Create and populate the appropriate message object

All numeric values are encoded as hexadecimal strings in the byte array.
