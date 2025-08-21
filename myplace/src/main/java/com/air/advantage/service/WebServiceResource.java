package com.air.advantage.service;

import com.air.advantage.aaservice.data.DataAircon;
import com.air.advantage.aaservice.data.JsonExporterViews;
import com.air.advantage.aaservice.data.MyMasterData;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WebServiceResource {

    @Inject
    private MyMasterData myMasterData;

    @GET
    @Path("/getSystemData")
    @JsonView(JsonExporterViews.Export.class) // Use JsonView to control serialization
    public Response getSystemData() {
        // Return masterData object as JSON
        // JAX-RS will automatically handle the conversion using the Jackson provider
        return Response.ok(myMasterData.masterData).build();
        
        // If you want XML response like the previous implementation, uncomment below
        // String xml = "<iZS10.3><request>getSystemData</request><mac>d8803951fa58</mac><authenticated>1</authenticated><system><type>17</type><AppStore>MyAir5</AppStore><name>AIRCON</name><hasLights>0</hasLights><rID></rID><MyAppRev>10.60</MyAppRev><CBrev>7.6</CBrev><zoneStationHasUnitControl>23</zoneStationHasUnitControl><unitcontrol><airconOnOff>0</airconOnOff><fanSpeed>1</fanSpeed><mode>2</mode><unitControlTempsSetting>0</unitControlTempsSetting><centralActualTemp>0.0</centralActualTemp><centralDesiredTemp>0.0</centralDesiredTemp><airConErrorCode>AA1</airConErrorCode><activationCodeStatus>0</activationCodeStatus><numberOfZones>10</numberOfZones><maxUserTemp>32.0</maxUserTemp><minUserTemp>16.0</minUserTemp><availableSchedules>5</availableSchedules><filterCleanWarning>0</filterCleanWarning></unitcontrol><zs103TechSettings><numberofConstantZones>2</numberofConstantZones><zsConstantZone1>1</zsConstantZone1><zsConstantZone2>2</zsConstantZone2><zsConstantZone3>0</zsConstantZone3><logoPIN></logoPIN><dealerPhoneNumber></dealerPhoneNumber><returnAirOffset>2.0</returnAirOffset><ACinfo>0</ACinfo><systemID>3</systemID><tempSensorNotConfigured>0</tempSensorNotConfigured><FAstatus>0</FAstatus><wifiStatus>0</wifiStatus><my3Gstatus>0</my3Gstatus><chucklesStatus>0</chucklesStatus></zs103TechSettings><cbType>1</cbType><upgrade>1</upgrade></system></iZS10.3>";
        // return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @GET
    @Path("/getLights")
    public Response getLights() {
        // Not supported, as in WebServer.java1
        String xml = "<iZS10.3><request>No longer supported</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/changeName")
    public Response changeName(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"changeName\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setSystemData")
    public Response setSystemData(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setSystemData\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setClock")
    public Response setClock(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setClock\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setZoneData")
    public Response setZoneData(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setZoneData\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setZoneTimer")
    public Response setZoneTimer(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setZoneTimer\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setScheduleData")
    public Response setScheduleData(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setScheduleData\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setLight")
    public Response setLight(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setLight\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setLightName")
    public Response setLightName(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setLightName\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setLightToGroup")
    public Response setLightToGroup(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setLightToGroup\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setLightScene")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setLightScene(com.air.advantage.aaservice.data.DataScene scene) {
        try {
            if (scene != null && scene.id != null) {
                MyMasterData.masterData.myLights.scenes.put(scene.id, scene);
                return Response.ok("{\"ack\":true,\"request\":\"setLightScene\"}", MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"ack\":false,\"request\":\"setLightScene\",\"error\":\"Missing scene id\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"ack\":false,\"request\":\"setLightScene\",\"error\":\"" + e.getMessage() + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
    }

    @POST
    @Path("/runLightScene")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response runLightScene(com.air.advantage.aaservice.data.DataScene scene) {
        try {
            // Update the scene in masterData.myLights.scenes
            if (scene != null && scene.id != null) {
                MyMasterData.masterData.myLights.scenes.put(scene.id, scene);
                return Response.ok("{\"ack\":true,\"request\":\"runLightScene\"}", MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"ack\":false,\"request\":\"runLightScene\",\"error\":\"Missing scene id\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"ack\":false,\"request\":\"runLightScene\",\"error\":\"" + e.getMessage() + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
    }

    @POST
    @Path("/setLightGroupName")
    public Response setLightGroupName(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setLightGroupName\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setLightGroup")
    public Response setLightGroup(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setLightGroup\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setAircon")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setAircon(java.util.Map<String, com.air.advantage.aaservice.data.DataAircon> airconMap) {
        try {
            for (var entry : airconMap.entrySet()) {
                DataAircon aircon = MyMasterData.masterData.aircons.get(entry.getKey());
                if (aircon == null) {
                    aircon = DataAircon.create();
                }
                aircon.copyFrom(entry.getValue());
                MyMasterData.masterData.aircons.put(entry.getKey(), aircon);
            }
            return Response.ok("{\"ack\":true,\"request\":\"setAircon\"}", MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"ack\":false,\"request\":\"setAircon\",\"error\":\"" + e.getMessage() + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
    }

    @POST
    @Path("/setSnapShot")
    public Response setSnapShot(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setSnapShot\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setMySystem")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setMySystem(com.air.advantage.aaservice.data.DataSystem incomingSystem) {
        try {
            MyMasterData.masterData.system.updateSystem(incomingSystem);
            return Response.ok("{\"ack\":true,\"request\":\"setMySystem\"}", MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"ack\":false,\"request\":\"setMySystem\",\"error\":\"" + e.getMessage() + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
    }

    @POST
    @Path("/setThing")
    public Response setThing(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setThing\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setGroupThing")
    public Response setGroupThing(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setGroupThing\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setGroupThingName")
    public Response setGroupThingName(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setGroupThingName\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setNewGroupThingName")
    public Response setNewGroupThingName(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setNewGroupThingName\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setThingToGroupThing")
    public Response setThingToGroupThing(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setThingToGroupThing\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setThingToNewGroupThing")
    public Response setThingToNewGroupThing(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setThingToNewGroupThing\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/runScene")
    public Response runScene(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"runScene\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setScene")
    public Response setScene(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setScene\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setSensor")
    public Response setSensor(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setSensor\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/setBackupDataToRestore")
    public Response setBackupDataToRestore(String body) {
        return Response.ok("{\"ack\":true,\"request\":\"setBackupDataToRestore\"}", MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/loginRequest")
    @Produces(MediaType.APPLICATION_XML)
    public Response loginRequest(String body) {
        String xmlResponse = "<iZS10.3><request>login</request><mac></mac><ack>1</ack><authenticated>1</authenticated></iZS10.3>";
        return Response.ok(xmlResponse, MediaType.APPLICATION_XML).build();
    }
}
