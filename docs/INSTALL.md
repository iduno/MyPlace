# Raspberry Pi Zero W - AirconPi Setup Guide

This guide describes preparing a Raspberry Pi Zero W to run the MyPlace server (airconpi) using an RS485/CAN HAT. It covers writing the OS image, basic host/user setup, enabling SSH + Wi‑Fi, installing OpenJDK 17, Maven and Git, pulling the code, building the project and installing a systemd service to start the service on boot.

**Prerequisites**
- A Raspberry Pi Zero W, microSD card (at least 8GB, prefer 16GB+), microSD card reader.
- A RS485 Adaptor or PI Hat
- A host computer (Windows/macOS/Linux) to write the OS image.
- Network access credentials (Wi‑Fi SSID and password).
- USB power for the Pi and any serial/RS485/CAN adapters as needed.

**Overview**
- Write Raspberry Pi OS
- Configure hostname and user/password
- Enable SSH and configure Wi‑Fi
- Boot the Pi, install OpenJDK 17, Maven, Git
- Clone MyPlace and build
- Configure systemd service to run the server at boot
- Notes on RS485 / CAN HAT configuration

## 1. Hardware Requirements
![](images/pizero-rs485hat.jpg)

**Raspberry PI**
A PI Zero W or PI Zero 2 W has sufficient capability for this project:

https://core-electronics.com.au/raspberry-pi-zero-w-wireless.html

https://core-electronics.com.au/raspberry-pi-zero-2-w-wireless.html

**RS485**
A RS485 adaptor of some sort is required. I've used the following:

https://core-electronics.com.au/rs485-can-hat-for-raspberry-pi.html

An alternate may be the following. This could be powered using the power from the control board.
This appears to need drivers to use and I have not used one.

https://core-electronics.com.au/isolated-rs485-can-hat-b-for-raspberry-pi-2-ch-rs485-and-1-ch-can-multi-protections.html

A USB adaptor is also usable. I've been using the following:

https://www.amazon.com.au/DTECH-Converter-Adapter-Supports-Windows/dp/B076WVFXN8

**SD Card**
A high endurance SD card is recommended to reduce the chance of corruption.

**RJ45 Breakout Board**
Not required but makes the connections easier. Always test the pins match up to the correct screw terminal.
I went with the dual connector for easier traffic sniffing. Just make sure its not a crossover type.

https://www.amazon.com.au/Zicojia-Ethernet-Terminal-Breakout-Connector/dp/B0DK73V23D

A single connector would probably be better for a final product.

https://www.amazon.com.au/Hexchuang-Ethernet-Connector-Breakout-Interface/dp/B0G2XR57G8

---

## 2. Write the Raspberry Pi OS image (Raspberry Pi Imager)

1. Download and install Raspberry Pi Imager:
   - Windows/macOS/Linux: https://www.raspberrypi.com/software/

2. Follow Raspberry Pi Instructions:
   - https://www.raspberrypi.com/documentation/computers/getting-started.html
   - Recommended: "Raspberry Pi OS LITE" if using a Pi Zero W or Pi Zero 2 w
   - Set the hostname (airconpi will be used for these instructions)
   - Add a username (aircon will be used for these instructions)
   - Enable WIFI
   - Enable SSH

## 3. SSH & Wi‑Fi verification

- If you enabled ssh and wifi
- SSH in:

```bash
ssh aircon@airconpi.local
# or ssh aircon@<ip-address>
```

If mDNS (`.local`) doesn't resolve, use the IP address.

---

## 4. Install OpenJDK 17, Maven and Git

Update packages and install required software:

```bash
sudo apt update && sudo apt full-upgrade -y
# Install OpenJDK 17
sudo apt install -y openjdk-17-jdk-headless
# Verify java
java -version
# Install Maven and Git
sudo apt install -y maven git
# Verify
mvn -v
git --version
```

Note: Raspberry Pi OS package repos provide OpenJDK 17. OpenJDK 21+ are not currently packaged.

---

## 5. Pull the code from GitHub

Clone the repo under a suitable directory (e.g. `/home/aircon/projects`):

```bash
mkdir -p ~/projects
cd ~/projects
git clone https://github.com/iduno/MyPlace.git
cd MyPlace
```

---

## 6. Build the project

From the repository root run:

```bash
mvn clean install package
```

Notes:
- The build will produce artifacts in the `myplace/target` and `myplace/target/quarkus-app` directories depending on project packaging.
- Large builds may take significant time on a Pi Zero W. Consider building on a faster machine (x86) and copying the assembled `quarkus-app` folder or final jar to the Pi.

Optional faster workflow:
- Build on your PC and then `rsync` or `scp` the `quarkus-app`/jar to the Pi.


---

## 7. Create a systemd service to run the server

Below is a generic example. Adjust `ExecStart` to the actual runnable produced by your build (jar, quarkus-app runner, or a shell wrapper). Place the service file at `/etc/systemd/system/airconpi.service`.

Example service (edit paths as required):

```ini
[Unit]
Description=AirconPi MyPlace service
After=network.target

[Service]
User=aircon
WorkingDirectory=/home/aircon/projects/MyPlace/myplace
# Example using the assembled quarkus app launcher
# ExecStart=/usr/bin/java -jar /home/aircon/projects/MyPlace/myplace/target/quarkus-app/quarkus-run.jar
# Or if the build produces a runner jar or custom launcher, point to it.
ExecStart=/usr/bin/java -jar /home/aircon/projects/MyPlace/myplace/target/quarkus-app/quarkus-run.jar
SuccessExitStatus=143
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target
```

Enable and start the service:

```bash
sudo systemctl daemon-reload
sudo systemctl enable airconpi.service
sudo systemctl start airconpi.service
sudo journalctl -u airconpi -f
```

Troubleshooting:
- If the service fails to start, inspect `sudo journalctl -u airconpi -b` and `sudo systemctl status airconpi`.
- Ensure the `ExecStart` path exists and Java runs correctly.

Placeholder: ![](systemd-service-example.png)

---

## 8. RS485 / CAN HAT notes

Hardware setups vary by HAT. General suggestions:
- Ensure any device tree overlays required by the HAT are enabled in `/boot/config.txt` (check HAT docs). Example:

```conf
dtoverlay=pi3-miniuart-bt
# or a hat-specific overlay like dtoverlay=spi0-1cs
```

- Enable serial console if your HAT uses UART (disable the Linux serial console if necessary via `raspi-config` → Interface Options → Serial). Allow the serial port for application use.
- If the HAT uses SPI/CAN, enable SPI (`raspi-config` → Interface Options → SPI).
- The RS485 transceiver usually exposes a `/dev/tty*` device (e.g. `/dev/ttyAMA0` or `/dev/ttyS0`). Confirm by running `dmesg` or `ls /dev/tty*` after attaching the HAT.

Add wiring photos and hat orientation here.

Placeholder: ![](hat-orientation.jpg)

---

## 9. What to add to documentation (suggestions)

- Hardware photos: Pi Zero W with HAT attached, wiring to RS485/CAN adaptor, terminal block wiring.
- A short wiring diagram showing TX/RX/DE/RE or CAN_H/L connections.
- Example `config.json` snippets for connecting to RS485/CAN devices.
- Example log output showing successful startup and device detection.
- A small troubleshooting section (common failures and how to check serial, overlay, permissions).
- A note about building on a faster machine and copying artifacts to the Pi (speeds up deployments).
- A short section about backing up the SD card image once a working system is configured.

---

## 10. Security notes

- Change the default password for the `pi` account.
- Consider creating a dedicated system user (e.g. `aircon`) and running the service under that account.
- If exposing the service to a wider network, consider firewall rules and HTTPS.

---
