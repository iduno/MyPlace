package com.air.advantage.service;

import java.util.TreeMap;

import com.air.advantage.aaservice.data.DataAircon;
import com.air.advantage.aaservice.data.JsonExporterViews;
import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.servicehandler.HandlerAircon;
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
        // Generic success response as in WebServer.java1
        String xml = "<iZS10.3><request>changeName</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setSystemData")
    public Response setSystemData(String body) {
        String xml = "<iZS10.3><request>setSystemData</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setClock")
    public Response setClock(String body) {
        String xml = "<iZS10.3><request>setClock</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setZoneData")
    public Response setZoneData(String body) {
        String xml = "<iZS10.3><request>setZoneData</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setZoneTimer")
    public Response setZoneTimer(String body) {
        String xml = "<iZS10.3><request>setZoneTimer</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setScheduleData")
    public Response setScheduleData(String body) {
        String xml = "<iZS10.3><request>setScheduleData</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setLight")
    public Response setLight(String body) {
        String xml = "<iZS10.3><request>setLight</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setLightName")
    public Response setLightName(String body) {
        String xml = "<iZS10.3><request>setLightName</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setLightToGroup")
    public Response setLightToGroup(String body) {
        String xml = "<iZS10.3><request>setLightToGroup</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setLightScene")
    public Response setLightScene(String body) {
        String xml = "<iZS10.3><request>setLightScene</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/runLightScene")
    public Response runLightScene(String body) {
        // Not supported
        String xml = "<iZS10.3><request>No longer supported</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setLightGroupName")
    public Response setLightGroupName(String body) {
        String xml = "<iZS10.3><request>setLightGroupName</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setLightGroup")
    public Response setLightGroup(String body) {
        String xml = "<iZS10.3><request>setLightGroup</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setAircon")
    public Response setAircon(TreeMap<String, DataAircon> airconMap) {
        try {
            HandlerAircon handlerAircon = new HandlerAircon(myMasterData);
            var result = handlerAircon.setAircon(airconMap,true);
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error processing aircon update: " + e.getMessage())
                .build();
        }
    }

    @POST
    @Path("/setSnapShot")
    public Response setSnapShot(String body) {
        String xml = "<iZS10.3><request>setSnapShot</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setMySystem")
    public Response setMySystem(String body) {
        String xml = "<iZS10.3><request>setMySystem</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setThing")
    public Response setThing(String body) {
        String xml = "<iZS10.3><request>setThing</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setGroupThing")
    public Response setGroupThing(String body) {
        String xml = "<iZS10.3><request>setGroupThing</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setGroupThingName")
    public Response setGroupThingName(String body) {
        String xml = "<iZS10.3><request>setGroupThingName</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setNewGroupThingName")
    public Response setNewGroupThingName(String body) {
        String xml = "<iZS10.3><request>setNewGroupThingName</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setThingToGroupThing")
    public Response setThingToGroupThing(String body) {
        String xml = "<iZS10.3><request>setThingToGroupThing</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setThingToNewGroupThing")
    public Response setThingToNewGroupThing(String body) {
        String xml = "<iZS10.3><request>setThingToNewGroupThing</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/runScene")
    public Response runScene(String body) {
        String xml = "<iZS10.3><request>runScene</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setScene")
    public Response setScene(String body) {
        String xml = "<iZS10.3><request>setScene</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setSensor")
    public Response setSensor(String body) {
        String xml = "<iZS10.3><request>setSensor</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/setBackupDataToRestore")
    public Response setBackupDataToRestore(String body) {
        String xml = "<iZS10.3><request>setBackupDataToRestore</request></iZS10.3>";
        return Response.ok(xml, MediaType.APPLICATION_XML).build();
    }

    @POST
    @Path("/loginRequest")
    @Produces(MediaType.APPLICATION_XML)
    public Response loginRequest(String body) {
        String xmlResponse = "<iZS10.3><request>login</request><mac></mac><ack>1</ack><authenticated>1</authenticated></iZS10.3>";
        return Response.ok(xmlResponse, MediaType.APPLICATION_XML).build();
    }
}
