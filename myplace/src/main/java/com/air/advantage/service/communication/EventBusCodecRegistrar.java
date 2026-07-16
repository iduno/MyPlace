package com.air.advantage.service.communication;

import org.jboss.logging.Logger;

import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessageAircon;
import com.air.advantage.cbmessages.CANMessageAircon00Unknown;
import com.air.advantage.cbmessages.CANMessageAircon01ZoneInformation;
import com.air.advantage.cbmessages.CANMessageAircon02UnitTypeInformation;
import com.air.advantage.cbmessages.CANMessageAircon03ZoneState;
import com.air.advantage.cbmessages.CANMessageAircon04ZoneConfiguration;
import com.air.advantage.cbmessages.CANMessageAircon05AirconState;
import com.air.advantage.cbmessages.CANMessageAircon06CBStatus;
import com.air.advantage.cbmessages.CANMessageAircon07CbStatusMessage;
import com.air.advantage.cbmessages.CANMessageAircon08CBErrorStatus;
import com.air.advantage.cbmessages.CANMessageAircon09ActivationCodeInformation;
import com.air.advantage.cbmessages.CANMessageAircon0aMidInformation;
import com.air.advantage.cbmessages.CANMessageAircon12ZoneSensorPairing;
import com.air.advantage.cbmessages.CANMessageAircon13CBInfoByte;
import com.air.advantage.cbmessages.CANMessageAircon26RfDevicePairing;
import com.air.advantage.cbmessages.CANMessageAircon27RfDeviceCalibration;
import com.air.advantage.cbmessages.CANMessageEventBusCodec;
import com.air.advantage.cbmessages.CANMessageLighting;
import com.air.advantage.cbmessages.CANMessageLighting00LmStatusMessageOld;
import com.air.advantage.cbmessages.CANMessageLighting01LmControlMessage;
import com.air.advantage.cbmessages.CANMessageLighting02LmStatusMessage;
import com.air.advantage.cbmessages.CANMessageLighting14DmControlMessage;
import com.air.advantage.cbmessages.CANMessageLighting15Rm2ControlMessage;
import com.air.advantage.cbmessages.CANMessageLighting16Rm2StatusMessage;
import com.air.advantage.cbmessages.CANMessageLighting17Rm2AddDevice;
import com.air.advantage.cbmessages.CANMessageLighting1dRm2ControlMessage;
import com.air.advantage.cbmessages.Message;
import com.air.advantage.cbmessages.MessageAck;
import com.air.advantage.cbmessages.MessageCAN;
import com.air.advantage.cbmessages.MessageEventBusCodec;
import com.air.advantage.cbmessages.MessageGetAllZoneSensorData;
import com.air.advantage.cbmessages.MessageGetClock;
import com.air.advantage.cbmessages.MessageGetScheduleData;
import com.air.advantage.cbmessages.MessageGetSystemData;
import com.air.advantage.cbmessages.MessageGetZoneDataZone;
import com.air.advantage.cbmessages.MessageGetZoneTimer;
import com.air.advantage.cbmessages.MessageNak;
import com.air.advantage.cbmessages.MessagePing;

import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventBusCodecRegistrar {
    private static final Logger LOG = Logger.getLogger(EventBusCodecRegistrar.class);

    @Inject
    EventBus eventBus;

    void onStart(@Observes StartupEvent ev) {
        registerCodec(Message.class, new MessageEventBusCodec());
        registerMessageCodec(MessageAck.class);
        registerMessageCodec(MessageCAN.class);
        registerMessageCodec(MessageGetAllZoneSensorData.class);
        registerMessageCodec(MessageGetClock.class);
        registerMessageCodec(MessageGetScheduleData.class);
        registerMessageCodec(MessageGetSystemData.class);
        registerMessageCodec(MessageGetZoneDataZone.class);
        registerMessageCodec(MessageGetZoneTimer.class);
        registerMessageCodec(MessageNak.class);
        registerMessageCodec(MessagePing.class);

        // Register codec for CANMessage and every subclass.
        // Vert.x matches by exact class, so each type needs its own registration
        // with a unique codec name.
        registerCanCodec(CANMessage.class);
        registerCanCodec(CANMessageAircon.class);
        registerCanCodec(CANMessageAircon00Unknown.class);
        registerCanCodec(CANMessageAircon01ZoneInformation.class);
        registerCanCodec(CANMessageAircon02UnitTypeInformation.class);
        registerCanCodec(CANMessageAircon03ZoneState.class);
        registerCanCodec(CANMessageAircon04ZoneConfiguration.class);
        registerCanCodec(CANMessageAircon05AirconState.class);
        registerCanCodec(CANMessageAircon06CBStatus.class);
        registerCanCodec(CANMessageAircon07CbStatusMessage.class);
        registerCanCodec(CANMessageAircon08CBErrorStatus.class);
        registerCanCodec(CANMessageAircon09ActivationCodeInformation.class);
        registerCanCodec(CANMessageAircon0aMidInformation.class);
        registerCanCodec(CANMessageAircon12ZoneSensorPairing.class);
        registerCanCodec(CANMessageAircon13CBInfoByte.class);
        registerCanCodec(CANMessageAircon26RfDevicePairing.class);
        registerCanCodec(CANMessageAircon27RfDeviceCalibration.class);
        registerCanCodec(CANMessageLighting.class);
        registerCanCodec(CANMessageLighting00LmStatusMessageOld.class);
        registerCanCodec(CANMessageLighting01LmControlMessage.class);
        registerCanCodec(CANMessageLighting02LmStatusMessage.class);
        registerCanCodec(CANMessageLighting14DmControlMessage.class);
        registerCanCodec(CANMessageLighting15Rm2ControlMessage.class);
        registerCanCodec(CANMessageLighting16Rm2StatusMessage.class);
        registerCanCodec(CANMessageLighting17Rm2AddDevice.class);
        registerCanCodec(CANMessageLighting1dRm2ControlMessage.class);
    }

    @SuppressWarnings("unchecked")
    private void registerMessageCodec(Class<? extends Message> messageType) {
        final String codecName = "cb-message-codec-" + messageType.getSimpleName();
        MessageEventBusCodec namedCodec = new MessageEventBusCodec() {
            @Override
            public String name() {
                return codecName;
            }
        };
        registerCodec((Class<Message>) messageType, namedCodec);
    }

    @SuppressWarnings("unchecked")
    private void registerCanCodec(Class<? extends CANMessage> messageType) {
        final String codecName = "can-message-codec-" + messageType.getSimpleName();
        CANMessageEventBusCodec namedCodec = new CANMessageEventBusCodec() {
            @Override
            public String name() {
                return codecName;
            }
        };
        registerCodec((Class<CANMessage>) messageType, namedCodec);
    }

    private <T> void registerCodec(Class<T> messageType, io.vertx.core.eventbus.MessageCodec<T, T> codec) {
        try {
            eventBus.getDelegate().registerDefaultCodec(messageType, codec);
            LOG.info("Registered EventBus codec for " + messageType.getSimpleName());
        } catch (IllegalStateException ex) {
            LOG.debug("EventBus codec already registered for " + messageType.getSimpleName());
        }
    }
}
