# MyPlace CAN Message Format Documentation

## Overview

This document describes the Controller Area Network (CAN) message formats used in the MyPlace system for communication between various components such as air conditioners, lighting systems, and controllers. CAN messages in this system follow a fixed-length format with specific fields for system identification, device type, and payload data.

## Message Structure

Every CAN message follows a fixed record length format with the following structure:

| Field | Width | Description |
|---|---:|---|
| System type | 2 characters | Hex code identifying the subsystem |
| Device type | 2 characters | Hex code identifying the device represented by the message |
| UID | 5 characters | Device identifier copied as text |
| Message type | 2 characters | Hex code selecting the message within the subsystem |
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

## Message System/Device Applicability

The device type is selected by the caller and is not intrinsic to a message ID. Constructors default to UNKNOWN (00). Current air-conditioning handlers use AIRCON_1 (03) or CONTROL_BOARD (01) depending on the sender/path. The lighting handler sends its lighting-module and RM2 messages with RF_CONTROLLER (02); other callers can provide another value. The air-conditioning message definitions are shared by system types 07 and 08; constructors default to 07, while deserialization preserves the type from the incoming header. Lighting messages use system type 02.

| Message class | Message type | System type | Device type |
|---|---|---|---|
| CANMessageAircon00Unknown | 00 UNKNOWN | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon01ZoneInformation | 01 ZONE_INFORMATION | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon02UnitTypeInformation | 02 UNIT_TYPE_INFORMATION | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon03ZoneState | 03 ZONE_STATE | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon04ZoneConfiguration | 04 ZONE_CONFIGURATION | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon05AirconState | 05 AIRCON_STATE | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon06CBStatus | 06 CB_STATUS | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon07CbStatusMessage | 07 CB_STATUS_MESSAGE | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon08CBErrorStatus | 08 CB_ERROR | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon09ActivationCodeInformation | 09 ACTIVATION_CODE_INFORMATION | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon0aMidInformation | 0a MID_INFORMATION | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon12ZoneSensorPairing | 12 ZONE_SENSOR_PAIRING | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon13CBInfoByte | 13 INFO_BYTE | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon26RfDevicePairing | 26 RF_DEVICE_PAIRING | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageAircon27RfDeviceCalibration | 27 RF_DEVICE_CALIBRATION | 07 or 08 | Caller-selected; commonly 01 or 03 |
| CANMessageLighting00LmStatusMessageOld | 00 LM_SETUP_OLD | 02 | Caller-selected; defaults to 00 |
| CANMessageLighting01LmControlMessage | 01 LM_UPDATE_BRIGHTNESS_LEVEL | 02 | 02 RF_CONTROLLER in lighting handler; otherwise caller-selected |
| CANMessageLighting02LmStatusMessage | 02 LM_SETUP | 02 | 02 RF_CONTROLLER in lighting handler; otherwise caller-selected |
| CANMessageLighting14DmControlMessage | 14 DM_UPDATE_BRIGHTNESS_LEVEL | 02 | Caller-selected; defaults to 00 |
| CANMessageLighting15Rm2ControlMessage | 15 RM2_THING_STATE | 02 | 02 RF_CONTROLLER in lighting handler; otherwise caller-selected |
| CANMessageLighting16Rm2StatusMessage | 16 RM2_DIP_THING | 02 | 02 RF_CONTROLLER in lighting handler; otherwise caller-selected |
| CANMessageLighting17Rm2AddDevice | 17 RM2_STATUS_ADD_DEVICE | 02 | 02 RF_CONTROLLER in lighting handler; otherwise caller-selected |
| CANMessageLighting1dRm2ControlMessage | 1d RM2_UPDATE_BRIGHTNESS_LEVEL | 02 | Caller-selected; defaults to 00 |

## Air-Conditioning Message Types

| Message type | Hex code | Description |
|---|---|---|
| ZONE_INFORMATION | 01 | Information about zones in the air-conditioning system |
| UNIT_TYPE_INFORMATION | 02 | Air-conditioner unit type and firmware information |
| ZONE_STATE | 03 | Current state of a specific zone |
| ZONE_CONFIGURATION | 04 | Configuration details for a specific zone |
| AIRCON_STATE | 05 | Current air-conditioner state |
| CB_STATUS | 06 | Control board status |
| CB_STATUS_MESSAGE | 07 | Extended control board status |
| CB_ERROR | 08 | Control board error information |
| ACTIVATION_CODE_INFORMATION | 09 | Activation-code details |
| MID_INFORMATION | 0a | MID signaling information |
| ZONE_SENSOR_PAIRING | 12 | Zone sensor pairing information |
| INFO_BYTE | 13 | Information byte |
| RF_DEVICE_PAIRING | 26 | RF device pairing information |
| RF_DEVICE_CALIBRATION | 27 | RF device calibration information |
| UNKNOWN | 00 | Unknown message type |

## Lighting Message Types

| Message type | Hex code | Description |
|---|---|---|
| LM_SETUP_OLD | 00 | Legacy lighting-module setup information |
| LM_UPDATE_BRIGHTNESS_LEVEL | 01 | Update brightness level for a light |
| LM_SETUP | 02 | Lighting-module setup information |
| DM_UPDATE_BRIGHTNESS_LEVEL | 14 | DM brightness update |
| RM2_THING_STATE | 15 | RM2 thing state information |
| RM2_DIP_THING | 16 | RM2 DIP-switch configuration |
| RM2_STATUS_ADD_DEVICE | 17 | RM2 status for adding a device |
| RM2_UPDATE_BRIGHTNESS_LEVEL | 1d | RM2 brightness update |

## Air-Conditioning Message Formats

The following payload fields begin immediately after the two-character message type.

### 00 - Unknown (CANMessageAircon00Unknown)

No message-specific fields are defined. The seven-byte payload is zero-filled when serialized. The class is used for message type 00.

### 01 - Zone information (CANMessageAircon01ZoneInformation)

This message provides information about zones in the air-conditioning system.

| Field | Type | Description |
|---|---|---|
| destination | int | Destination identifier (CB=11, TABLET=20) |
| numZones | int | Total number of zones |
| numConstantZones | int | Number of constant zones |
| constantZone1 | int | First constant zone identifier |
| constantZone2 | int | Second constant zone identifier |
| constantZone3 | int | Third constant zone identifier |
| filterCleanStatus | int | Filter clean status |

### 02 - Unit type information (CANMessageAircon02UnitTypeInformation)

This message provides information about the air-conditioner unit type and firmware.

| Field | Type | Description |
|---|---|---|
| unitType | UnitType | DAIKIN=0x11, PANASONIC=0x12, FUJITSU=0x13, SAMSUNG_DVM=0x19 |
| activationStatus | CodeStatus | NO_CODE=0, CODE_ENABLED=1, EXPIRED=2 |
| fwMajor | int | Firmware major version |
| fwMinor | int | Firmware minor version |

### 03 - Zone state (CANMessageAircon03ZoneState)

This message provides the current state and temperatures of a specific zone.

| Field | Type | Description |
|---|---|---|
| zoneNumber | int | Zone identifier |
| zoneState | ZoneState | CLOSE=0, OPEN=1; encoded in bit 7 of the packed state/percentage byte |
| zonePercent | int | Zone percentage open; encoded in bits 0-6 of the packed byte |
| sensorType | int | Sensor type: 0 none, 1 RF, 2 wired, 3 RF-to-CAN booster, 4 RF_X |
| setTemp | float | Set temperature in half-degree increments; wire value is temperature multiplied by 2 |
| measuredTemp | float | Measured temperature encoded as an integer byte and a tenths-digit byte |

### 04 - Zone configuration (CANMessageAircon04ZoneConfiguration)

This message provides configuration details for a specific zone.

| Field | Type | Description |
|---|---|---|
| zoneNumber | int | Zone identifier |
| minDamper | int | Minimum damper setting |
| maxDamper | int | Maximum damper setting |
| motionStatus | int | Motion status |
| motionConfig | int | Motion configuration |
| zoneError | int | Zone error code |
| rssi | int | Signal strength indicator |

### 05 - Aircon state (CANMessageAircon05AirconState)

This message provides the current state of the air conditioner.

| Field | Type | Description |
|---|---|---|
| systemState | SystemState | OFF=0, ON=1 |
| systemMode | SystemMode | COOL=1, HEAT=2, VENT=3, AUTO=4, DRY=5, MYAUTO=6 |
| systemFan | FanState | OFF=0, LOW=1, MEDIUM=2, HIGH=3, AUTO=4, AUTOAA=5 |
| setTemp | float | Set temperature in half-degree increments; wire value is temperature multiplied by 2 |
| myZoneId | int | ID of the MyZone |
| freshAirStatus | FreshAirStatus | NONE=0, OFF=1, ON=2 |
| rfSysId | int | RF system ID |

### 06 - Control board status (CANMessageAircon06CBStatus)

This message provides control board and RF firmware status.

| Field | Type | Description |
|---|---|---|
| cbFwMajor | int | Control board firmware major version |
| cbFwMinor | int | Control board firmware minor version |
| cbType | int | Control board type |
| rfFwMajor | int | RF firmware major version |

The remaining three payload bytes are reserved and zero-filled.

### 07 - Control board status message (CANMessageAircon07CbStatusMessage)

This message provides an extended control board status message.

| Field | Type | Description |
|---|---|---|
| cbFwMajor | int | Control board firmware major version |
| cbFwMinor | int | Control board firmware minor version |
| cbType | int | Control board type |
| rfFwMajor | int | RF firmware major version |


### 08 - Control board error (CANMessageAircon08CBErrorStatus)

This message provides control board error information.

| Field | Type | Description |
|---|---|---|
| errorCode | String | Error-code string, up to five characters |


### 09 - Activation-code information (CANMessageAircon09ActivationCodeInformation)

This message provides activation-code details.

| Field | Type | Description |
|---|---|---|
| action | int | Action type: 1 set a new code, 2 unlock |
| unlockCode | int | Two-byte unlock code, most significant byte first |
| activationTimeDays | int | Activation duration in days |

### 0a - MID information (CANMessageAircon0aMidInformation)

This message provides MID information. It has no additional fields and is used as a signaling message.

### 12 - Zone-sensor pairing (CANMessageAircon12ZoneSensorPairing)

This message provides zone-sensor pairing information.

| Field | Type | Description |
|---|---|---|
| sensorUID | String | Six-character sensor UID |
| infoByte | int | Information byte |
| sensorMajorRev | int | Sensor major revision |

### 13 - Control board info byte (CANMessageAircon13CBInfoByte)

This message provides an information byte for the control board.

| Field | Type | Description |
|---|---|---|
| infoByte | int | Information byte |

### 26 - RF device pairing (CANMessageAircon26RfDevicePairing)

This message provides RF device pairing information.

| Field | Type | Description |
|---|---|---|
| pairingControl | int | Pairing control value |
| rfDeviceType | int | RF device type |
| channelNo | int | Channel number |

### 27 - RF device calibration (CANMessageAircon27RfDeviceCalibration)

This message provides RF device calibration information.

| Field | Type | Description |
|---|---|---|
| calibrationControl | int | Calibration control value |
| channelNo | int | Channel number |
| upDownPosition | int | Up/down position value |

## Lighting Message Formats

### 00 - Legacy lighting-module setup (CANMessageLighting00LmStatusMessageOld)

This message provides legacy lighting-module setup information.

| Field | Type | Description |
|---|---|---|
| roomExists | int bitmask | Flags indicating which rooms exist; helper methods expose bits 0-5 |
| validRooms | int bitmask | Flags indicating which rooms are valid; helper methods expose bits 0-5 |
| version | int | Version number |

Payload byte 0 is reserved; these fields occupy payload bytes 1-3. The remaining payload bytes are zero-filled. These values are integer bitmasks, not boolean arrays.

### 01 - Lighting-module brightness (CANMessageLighting01LmControlMessage)

This message updates the brightness level for a light.

| Field | Type | Description |
|---|---|---|
| roomNumber | int | Room number |
| lightState | LightState | OFF=0, ON=1; encoded in bit 7 of the brightness whole-part byte |
| brightnessLevel | int | Brightness lookup-table index in increments of 5, from 0 through 100 |

Brightness wire levels represented by the lookup table are 0.0, 0.1, 0.2, 0.3, 0.8, 1.6, 2.7, 4.3, 6.4, 9.1, 12.5, 16.6, 21.6, 27.5, 34.3, 42.2, 51.2, 61.4, 72.9, 85.7, and 100.0. The serialized brightness whole part and tenths mantissa occupy separate bytes. Deserialization selects the nearest lookup-table value.

### 02 - Lighting-module setup (CANMessageLighting02LmStatusMessage)

This message provides lighting-module setup information.

| Field | Type | Description |
|---|---|---|
| majorFWVersion | int | Major firmware version |
| minorFWVersion | int | Minor firmware version |
| roomExists | int bitmask | Flags indicating which rooms exist; helper methods expose bits 0-5 |
| validRooms | int bitmask | Flags indicating which rooms are valid; helper methods expose bits 0-5 |
| relayRooms | int bitmask | Flags indicating which rooms have a relay; helper methods expose bits 0-5 |
| infoByte | int | Information byte |

### 14 - DM brightness update (CANMessageLighting14DmControlMessage)

This message carries a DM brightness update.

| Field | Type | Description |
|---|---|---|
| roomNumber | int | Room number; serialized in payload byte 0 |
| infoByte | int | Information byte; serialized in payload byte 1 |
| lightState | boolean | Flag to indicate on/off state bit 7 of infoByte |
| dimLevel | int | Bits 0-6 of infoByte |


### 15 - RM2 thing state (CANMessageLighting15Rm2ControlMessage)

This message provides RM2 thing state information.

| Field | Type | Description |
|---|---|---|
| roomNumber | int | Room number |
| lightState | int | Light state |
| switchState | int | Switch state: 0, 1, 2, 3, 8, 9, or 10 |
| dimLevel | int | Dimming level |
| nodeDipState | int | Node DIP-switch state |
| dimOffset | int | Dimming offset |
| statusState | int | Status bit field |
| lowBattery | boolean | Low-battery indicator, reflected by bit 7 of statusState |
| isCalibrated | boolean | Calibration status, reflected by bit 6 of statusState |
| isPoll | boolean | Poll status, reflected by bit 5 of statusState |

The three boolean accessors mirror their corresponding bits in statusState.

### 16 - RM2 DIP configuration (CANMessageLighting16Rm2StatusMessage)

This message provides RM2 DIP-switch configuration.

| Field | Type | Description |
|---|---|---|
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

### 17 - RM2 add-device status (CANMessageLighting17Rm2AddDevice)

This message provides RM2 status for adding a device.

| Field | Type | Description |
|---|---|---|
| majorFWVersion | int | Major firmware version |
| minorFWVersion | int | Minor firmware version |
| infoByte | int | Information byte |

### 1d - RM2 brightness update (CANMessageLighting1dRm2ControlMessage)

This message updates the RM2 dimming level for a room.

| Field | Type | Description |
|---|---|---|
| roomNumber | int | Room number |
| dimLevel | int | Dimming level |


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