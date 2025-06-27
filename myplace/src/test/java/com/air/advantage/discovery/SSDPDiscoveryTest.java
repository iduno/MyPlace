package com.air.advantage.discovery;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class SSDPDiscoveryTest {

    @Mock
    private MulticastSocket mockSocket;

    private SSDPDiscovery discovery;

    @BeforeEach
    public void setUp() throws Exception {
        // Create discovery instance manually
        discovery = new SSDPDiscovery();
        
        // Create a test wrapper that allows us to inject our mock socket
        mockSocketCreation();
    }
    
    private void mockSocketCreation() throws Exception {
        // Use reflection to create a custom implementation that returns our mock socket
        // Field field = SSDPDiscovery.class.getDeclaredField("socket");
        // field.setAccessible(true);
        // field.set(discovery, mockSocket);
    }

    @Test
    void testDiscoverDevices_WithValidResponse() throws Exception {
        // Prepare mock response
        String mockResponse =
            "HTTP/1.1 200 OK\r\n" +
            "CACHE-CONTROL: max-age=1800\r\n" +
            "SERVER: MyPlace/1.0\r\n" +
            "LOCATION: http://192.168.1.100:8080/description.xml\r\n" +
            "ST: urn:schemas-upnp-org:device:Basic:1\r\n" +
            "USN: uuid:MyPlace-1_0-1234567890\r\n\r\n";

        // Create a DatagramPacket with the mock response
        byte[] responseData = mockResponse.getBytes();
        InetAddress mockAddress = InetAddress.getByName("192.168.1.100");
        
        // Mock socket behavior
        doNothing().when(mockSocket).send(any(DatagramPacket.class));
        
        // Fix: Replace lambda with Answer implementation to avoid void return type error
        Answer<Void> socketReceiveAnswer = invocation -> {
            DatagramPacket packet = invocation.getArgument(0);
            System.arraycopy(responseData, 0, packet.getData(), 0, responseData.length);
            packet.setLength(responseData.length);
            packet.setAddress(mockAddress);
            return null;
        };
        
        doAnswer(socketReceiveAnswer).doThrow(new SocketTimeoutException())
            .when(mockSocket).receive(any(DatagramPacket.class));

        // Execute discovery
        List<SSDPDiscovery.DeviceInfo> devices = discovery.discoverDevices();

        // Verify results
        assertNotNull(devices);
        assertEquals(1, devices.size());

        SSDPDiscovery.DeviceInfo device = devices.get(0);
        assertEquals("192.168.1.100", device.getIpAddress());
        assertEquals("MyPlace/1.0", device.getServerInfo());
        assertEquals("http://192.168.1.100:8080/description.xml", device.getLocation());
    }

    @Test
    void testDiscoverDevices_NoDevicesFound() throws Exception {
        // Mock socket to timeout immediately
        // doThrow(new SocketTimeoutException())
        //     .when(mockSocket).receive(any(DatagramPacket.class));

        List<SSDPDiscovery.DeviceInfo> devices = discovery.discoverDevices();

        assertNotNull(devices);
        assertTrue(devices.isEmpty());
    }

    @Test
    void testDeviceInfoEquality() {
        SSDPDiscovery.DeviceInfo device1 = new SSDPDiscovery.DeviceInfo(
            "192.168.1.100",
            "MyPlace/1.0",
            "http://192.168.1.100:8080/description.xml"
        );

        SSDPDiscovery.DeviceInfo device2 = new SSDPDiscovery.DeviceInfo(
            "192.168.1.100",
            "MyPlace/1.0",
            "http://192.168.1.100:8080/description.xml"
        );

        SSDPDiscovery.DeviceInfo device3 = new SSDPDiscovery.DeviceInfo(
            "192.168.1.101",
            "MyPlace/1.0",
            "http://192.168.1.101:8080/description.xml"
        );

        assertEquals(device1, device2);
        assertNotEquals(device1, device3);
        assertEquals(device1.hashCode(), device2.hashCode());
        assertNotEquals(device1.hashCode(), device3.hashCode());
    }
}
