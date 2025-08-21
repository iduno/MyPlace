# MyPlace Message Format Documentation

This document provides detailed information about the message formats used in the MyPlace system. The MyPlace system uses a variety of data structures to manage different components such as air conditioners, lights, zones, things, groups, and more.

## Table of Contents

1. [System Overview](#system-overview)
2. [Message Path Structure](#message-path-structure)
3. [Data Types](#data-types)
   - [Aircon](#aircon)
   - [Zones](#zones)
   - [Things](#things)
   - [Groups](#groups)
   - [Lights](#lights)
   - [Monitors](#monitors)
   - [Scenes](#scenes)
   - [Snapshots](#snapshots)
4. [Message Broadcasting](#message-broadcasting)

## System Overview

The MyPlace system is designed to control and manage various smart home components, primarily air conditioning systems, lights, and other smart devices. The communication between these components is facilitated through structured message formats that are documented in this file.

Constants used throughout the system are defined in `Constants.java` (`com.air.advantage.data.j`).

## Message Path Structure

Messages in the MyPlace system follow a hierarchical path structure. The main paths include:

| Path | Description |
|------|-------------|
| `/aircons/` | Air conditioner related data |
| `/aircons/{airconKey}/info/` | Information about a specific air conditioner |
| `/aircons/{airconKey}/zones/{zoneId}/` | Zone-specific data for an air conditioner |
| `/myLights/` | Light system data |
| `/myLights/lights/{lightId}/` | Information about a specific light |
| `/myLights/groups/{groupId}/` | Information about a light group |
| `/myLights/alarms/{alarmId}/` | Light alarm data |
| `/myThings/` | General "things" data |
| `/myThings/things/{thingId}/` | Information about a specific thing |
| `/myThings/groups/{groupId}/` | Thing group information |
| `/myScenes/` | Scene data |
| `/myScenes/scenes/{sceneId}/` | Information about a specific scene |
| `/myMonitors/` | Monitor data |
| `/myMonitors/monitors/{monitorId}/` | Information about a specific monitor |
| `/myMonitors/monitors/{monitorId}/actions/` | Monitor actions |
| `/myMonitors/monitors/{monitorId}/events/` | Monitor events |
| `/snapshots/{snapshotId}/` | System snapshots |
| `/system/` | System-wide settings |
| `/myView/` | User interface view settings |
| `/myAddOns/` | Add-on related data |
| `/myAddOns/hueBridges/{bridgeId}/` | Hue bridge data |
| `/myGarageRFControllers/garageControllers/{controllerId}/` | Garage controller data |
| `/mySensors/` | Sensor data |
| `/mySensors/sensors/{sensorId}/` | Information about a specific sensor |

## Data Types

### Aircon

The Aircon data structure contains information about an air conditioner unit (`com.air.advantage.data.d` / `Aircon.java`):

| Field | Type | Description |
|-------|------|-------------|
| `info` | `DataAirconInfo` | Air conditioner information |
| `zones` | `HashMap<String, Zone>` | Map of zones for the air conditioner |

#### DataAirconInfo

DataAirconInfo (`com.air.advantage.data.e` / `DataAirconInfo.java`) contains detailed information about an air conditioner:

| Field | Type | Description |
|-------|------|-------------|
| `aaAutoFanModeEnabled` | `Boolean` | Whether auto fan mode is enabled |
| `activationCodeStatus` | `CodeStatus` | Activation code status |
| `airconErrorCode` | `String` | Error code if any |
| `cbFWRevMajor` | `Integer` | Control board firmware major revision |
| `cbFWRevMinor` | `Integer` | Control board firmware minor revision |
| `cbType` | `Integer` | Control board type |
| `climateControlModeEnabled` | `Boolean` | Whether climate control mode is enabled |
| `climateControlModeIsRunning` | `Boolean` | Whether climate control mode is running |
| `constantZone1` | `Integer` | Constant zone 1 setting |
| `constantZone2` | `Integer` | Constant zone 2 setting |
| `constantZone3` | `Integer` | Constant zone 3 setting |
| `countDownToOff` | `Integer` | Countdown timer to turn off |
| `countDownToOn` | `Integer` | Countdown timer to turn on |
| `dbFWRevMajor` | `Integer` | Dashboard firmware major revision |
| `dbFWRevMinor` | `Integer` | Dashboard firmware minor revision |
| `enabled` | `Boolean` | Whether the aircon is enabled |
| `fan` | `FanStatus` | Fan status |
| `filterCleanStatus` | `Integer` | Filter clean status |
| `freshAirStatus` | `FreshAirStatus` | Fresh air status |
| `mode` | `AirconMode` | Current mode (cool, heat, etc.) |
| `myAutoCoolTargetTemp` | `Integer` | Auto cool target temperature |
| `myAutoHeatTargetTemp` | `Integer` | Auto heat target temperature |
| `myAutoModeCurrentSetMode` | `AirconMode` | Current auto mode setting |
| `myAutoModeEnabled` | `Boolean` | Whether auto mode is enabled |
| `myAutoModeIsRunning` | `Boolean` | Whether auto mode is running |
| `myFanSpeedIsRunning` | `Boolean` | Whether fan speed control is running |
| `myZone` | `Integer` | Current zone setting |
| `name` | `String` | Aircon name |
| `noOfConstantZones` | `Integer` | Number of constant zones |
| `noOfZones` | `Integer` | Total number of zones |
| `quietNightModeEnabled` | `Boolean` | Whether quiet night mode is enabled |
| `quietNightModeIsRunning` | `Boolean` | Whether quiet night mode is running |
| `rfFWRevMajor` | `Integer` | RF firmware major revision |
| `rfSysID` | `Integer` | RF system ID |
| `setTemp` | `Float` | Set temperature |
| `state` | `SystemState` | Current state |
| `uid` | `String` | Unique identifier |
| `unitType` | `Integer` | Unit type |

### Zones

Zones (`DataZone.java`) represent different areas controlled by the air conditioning system (part of the aircon data structure):

| Field | Type | Description |
|-------|------|-------------|
| `name` | `String` | Zone name |
| `state` | `ZoneState` | Current zone state |
| `value` | `Integer` | Zone value (e.g., temperature setting) |
| `min` | `Integer` | Minimum zone value |
| `max` | `Integer` | Maximum zone value |

### Things

The Things data structure (`com.air.advantage.data.w0` / `DataThings.java`) contains information about various smart devices:

| Field | Type | Description |
|-------|------|-------------|
| `things` | `TreeMap<String, DataMyThing>` | Map of things |
| `backupThings` | `ArrayList<BackupThing>` | Backup of things |
| `groups` | `TreeMap<String, DataGroupThing>` | Groups of things |
| `groupsOrder` | `ArrayList<String>` | Order of groups |
| `system` | `DataThingsSystem` | System information |

#### DataMyThing

DataMyThing represents an individual smart device:

| Field | Type | Description |
|-------|------|-------------|
| `buttonType` | `String` | Type of button control |
| `id` | `String` | Thing ID |
| `name` | `String` | Thing name |
| `value` | `Integer` | Current value |
| `state` | `State` (`com.air.advantage.data.c1` / `State.java`) | Current state |

### Groups

Groups (`DataGroupThing`) are collections of related devices:

| Field | Type | Description |
|-------|------|-------------|
| `id` | `String` | Group ID |
| `name` | `String` | Group name |
| `thingsOrder` | `ArrayList<String>` | Order of things in the group |
| `buttonType` | `String` | Type of button control |
| `state` | `State` (`com.air.advantage.data.c1` / `State.java`) | Current state |

### Lights

The Lights data structure (`DataMyLights`) contains information about lighting devices:

| Field | Type | Description |
|-------|------|-------------|
| `lights` | `TreeMap<String, DataLight>` | Map of lights |
| `groups` | `TreeMap<String, DataGroup>` | Groups of lights |
| `groupsOrder` | `ArrayList<String>` | Order of light groups |
| `system` | `DataLightsSystem` | Light system information |
| `alarms` | `TreeMap<String, LightAlarm>` | Light alarms |
| `alarmsOrder` | `ArrayList<String>` | Order of light alarms |

### Monitors

The Monitors data structure (`DataMyMonitors` / `Monitor.java`) contains information about monitoring devices:

| Field | Type | Description |
|-------|------|-------------|
| `monitors` | `TreeMap<String, Monitor>` | Map of monitors |
| `monitorsOrder` | `ArrayList<String>` | Order of monitors |
| `actions` | `ArrayList<Action>` | Monitor actions |
| `events` | `ArrayList<Event>` | Monitor events |

### Scenes

The Scenes data structure (`DataMyScene` / `Scene.java`) contains information about predefined scenes:

| Field | Type | Description |
|-------|------|-------------|
| `scenes` | `TreeMap<String, Scene>` | Map of scenes |
| `scenesOrder` | `ArrayList<String>` | Order of scenes |

### Snapshots

Snapshots (`SnapShot.java`) represent saved system states:

| Field | Type | Description |
|-------|------|-------------|
| `id` | `String` | Snapshot ID |
| `name` | `String` | Snapshot name |
| `timestamp` | `Long` | Timestamp when snapshot was taken |
| `data` | `Map<String, Object>` | Snapshot data |

## Message Broadcasting

The system uses a broadcasting mechanism to communicate changes in the system state. The `DataManager` class (`com.air.advantage.data.l` / `DataManager.java`) manages these broadcasts:

| Broadcast Type | Description |
|---------------|-------------|
| `SYSTEM_DATA_UPDATE` | System data has been updated |
| `ZONE_DATA_UPDATE` | Zone data has been updated |
| `SCHEDULE_DATA_UPDATE` | Schedule data has been updated |

Each broadcast contains relevant information about the update, such as the affected air conditioner, zone, light, or other component. The broadcast info is represented by the `BroadcastInfo` inner class (`com.air.advantage.data.l$a`):

| Field | Type | Description |
|-------|------|-------------|
| `broadcastString` | `String` | Type of broadcast |
| `airconKey` | `String` | Air conditioner identifier (if applicable) |
| `zoneId` | `String` | Zone identifier (if applicable) |
| `snapshotId` | `String` | Snapshot identifier (if applicable) |
| `lightId` | `String` | Light identifier (if applicable) |
| `groupId` | `String` | Group identifier (if applicable) |
| `sceneId` | `String` | Scene identifier (if applicable) |
