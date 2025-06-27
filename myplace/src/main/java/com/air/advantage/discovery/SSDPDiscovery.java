package com.air.advantage.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SSDPDiscovery {
    private static final Logger LOGGER = Logger.getLogger(SSDPDiscovery.class.getName());
    
    private static final String MULTICAST_ADDRESS = "239.255.255.250";
    private static final int MULTICAST_PORT = 1900;
    private static final int DISCOVERY_TIMEOUT = 20000; // 5 seconds
    
    private static final String DISCOVERY_MESSAGE =
        "M-SEARCH * HTTP/1.1\r\n" +
        "HOST: 239.255.255.250:1900\r\n" +
        "MAN: \"ssdp:discover\"\r\n" +
        "MX: 1\r\n" +
        "ST: urn:schemas-upnp-org:device:MyPlace:1\r\n" + // Search target for MyPlace devices
        "\r\n";

    private final List<DeviceInfo> discoveredDevices = new CopyOnWriteArrayList<>();
    
    // Add a field to store the socket for testing
    private MulticastSocket socket;

    public List<DeviceInfo> discoverDevices() throws IOException {
        discoveredDevices.clear();
        
        // Use the existing socket if it's set (for testing), otherwise create a new one
        boolean createNewSocket = (socket == null);
        
        try (MulticastSocket multicastSocket = createNewSocket ? new MulticastSocket() : socket) {
            if (createNewSocket) {
                multicastSocket.setTimeToLive(4);
                multicastSocket.setSoTimeout(DISCOVERY_TIMEOUT);
            }
            
            // Send discovery message
            byte[] requestBytes = DISCOVERY_MESSAGE.getBytes(StandardCharsets.UTF_8);
            InetAddress multicastAddress = InetAddress.getByName(MULTICAST_ADDRESS);
            DatagramPacket request = new DatagramPacket(
                requestBytes,
                requestBytes.length,
                multicastAddress,
                MULTICAST_PORT
            );
            multicastSocket.send(request);

            // Listen for responses
            byte[] responseBuffer = new byte[1024];
            long endTime = System.currentTimeMillis() + DISCOVERY_TIMEOUT;

            while (System.currentTimeMillis() < endTime) {
                try {
                    DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);
                    multicastSocket.receive(response);
                    processResponse(response);
                } catch (SocketTimeoutException e) {
                    break;
                }
            }
            
            // Only close if we created it
            if (createNewSocket) {
                multicastSocket.close();
            }
        }

        return new ArrayList<>(discoveredDevices);
    }

    private void processResponse(DatagramPacket response) {
        String responseData = new String(
            response.getData(),
            response.getOffset(),
            response.getLength(),
            StandardCharsets.UTF_8
        );
        LOGGER.info(responseData);

        if (responseData.contains("MyPlace")) {
            DeviceInfo device = new DeviceInfo(
                response.getAddress().getHostAddress(),
                extractServerInfo(responseData),
                extractLocation(responseData)
            );
            if (!discoveredDevices.contains(device)) {
                discoveredDevices.add(device);
            }
        }
    }

    private String extractServerInfo(String response) {
        String[] lines = response.split("\r\n");
        for (String line : lines) {
            if (line.startsWith("SERVER:")) {
                return line.substring(8).trim();
            }
        }
        return "Unknown";
    }

    private String extractLocation(String response) {
        String[] lines = response.split("\r\n");
        for (String line : lines) {
            if (line.startsWith("LOCATION:")) {
                return line.substring(9).trim();
            }
        }
        return "";
    }

    public static class DeviceInfo {
        private final String ipAddress;
        private final String serverInfo;
        private final String location;

        public DeviceInfo(String ipAddress, String serverInfo, String location) {
            this.ipAddress = ipAddress;
            this.serverInfo = serverInfo;
            this.location = location;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public String getServerInfo() {
            return serverInfo;
        }

        public String getLocation() {
            return location;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof DeviceInfo)) return false;
            DeviceInfo other = (DeviceInfo) obj;
            return ipAddress.equals(other.ipAddress);
        }

        @Override
        public int hashCode() {
            return ipAddress.hashCode();
        }
    }
}
