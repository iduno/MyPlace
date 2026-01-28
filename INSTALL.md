# Raspberry Pi Zero W — AirconPi Setup Guide

This guide describes preparing a Raspberry Pi Zero W to run the MyPlace server (airconpi) using an RS485/CAN HAT. It covers writing the OS image, basic host/user setup, enabling SSH + Wi‑Fi, installing OpenJDK 17, Maven and Git, pulling the code, building the project and installing a systemd service to start the service on boot.


**Hardware Requirements**
[IMAGE: pizero-rs485hat.jpg]
## Raspberry PI
A PI Zero W or PI Zero 2 W has sufficient capability for this project:
https://core-electronics.com.au/raspberry-pi-zero-w-wireless.html
https://core-electronics.com.au/raspberry-pi-zero-2-w-wireless.html

## RS485
A RS485 adaptor of some sort is required. I've used the following:
https://core-electronics.com.au/rs485-can-hat-for-raspberry-pi.html

An alternate may be the following. This could be powered using the power from the control board.
This appears to need drivers to use and I have not used one.
https://core-electronics.com.au/isolated-rs485-can-hat-b-for-raspberry-pi-2-ch-rs485-and-1-ch-can-multi-protections.html

A USB adaptor is also usable. I've been using the following:
https://www.amazon.com.au/DTECH-Converter-Adapter-Supports-Windows/dp/B076WVFXN8

## SD Card
A high endurance SD card is recommended to reduce the chance of corruption.

## RJ45 Breakout Board
Not required but makes the connections easier. 
I went with the dual connector for easier traffic sniffing. Just make sure its not a crossover type.
https://www.amazon.com.au/Zicojia-Ethernet-Terminal-Breakout-Connector/dp/B0DK73V23D
A single connector would probably be better for a final product.
https://www.amazon.com.au/Hexchuang-Ethernet-Connector-Breakout-Interface/dp/B0G2XR57G8

**Prerequisites**
- A Raspberry Pi Zero W, microSD card (at least 8GB, prefer 16GB+), microSD card reader.
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

---

## 1. Write the Raspberry Pi OS image (Raspberry Pi Imager)

1. Download and install Raspberry Pi Imager:
   - Windows/macOS/Linux: https://www.raspberrypi.com/software/

2. Open Raspberry Pi Imager and choose the OS:
   - Recommended: "Raspberry Pi OS LITE (32-bit)" (Lite or Desktop depending on whether you need a GUI)

3. Choose your microSD card and click "Write".

4. Optional — pre-configure (if supported by your imager) or manually add files to the boot partition before ejecting:
   - Create an empty file named `ssh` in the boot partition to enable SSH on first boot.
   - Create a `wpa_supplicant.conf` file in the boot partition to automatically configure Wi‑Fi (see next section).

[IMAGE: raspberry-pi-imager.png]
[IMAGE: raspberry-pi-imager-edit.png]
[IMAGE: raspberry-pi-imager-settings.png]
[IMAGE: raspberry-pi-imager-enablessh.png]

---

## 2. Configure hostname, user and SSH (quick steps)

Preferred hostname for this project: `airconpi`.

There are two common approaches:

A) Pre-configure on the SD card (recommended for headless setup)
- In the SD card boot partition create a file called `ssh` (empty) — this enables SSH on first boot.
- Create `wpa_supplicant.conf` in the boot partition with the following contents (replace SSID/PASSWORD):

```conf
country=AU
ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1

network={
  ssid="YOUR_SSID"
  psk="YOUR_WIFI_PASSWORD"
  key_mgmt=WPA-PSK
}
```

- To set the hostname and default user/pass you can either:
  - Edit `userconf` or `userconf.txt` in the boot partition (depending on imager options), or
  - After first boot SSH in as `pi` and run `sudo raspi-config` to set hostname and change password.

B) Configure after first boot via `raspi-config`:
- Boot the Pi, SSH into it (default user `pi`, default password `raspberry` unless changed by imager), then run:

```bash
sudo raspi-config
```

- In `raspi-config`:
  - System Options → Hostname → set `airconpi`
  - System Options → Password → change default password
  - Interface Options → SSH → Enable
  - Localisation options → Set locale/timezone

Restart if prompted.

---

## 3. SSH & Wi‑Fi verification

- If you pre-created the `ssh` file and `wpa_supplicant.conf`, find the Pi's IP on your router or use a network scanner.
- SSH in:

```bash
ssh pi@airconpi.local
# or ssh pi@<ip-address>
```

If mDNS (`.local`) doesn't resolve, use the IP address.

---

## 4. Install OpenJDK 17, Maven and Git

Update packages and install required software:

```bash
sudo apt update && sudo apt upgrade -y
# Install OpenJDK 17
sudo apt install -y openjdk-17-jdk
# Verify java
java -version
# Install Maven and Git
sudo apt install -y maven git
# Verify
mvn -v
git --version
```

Note: Raspberry Pi OS package repos often provide OpenJDK 17. If you need a specific JDK build (Amazon Corretto, Azul, etc.) follow vendor instructions.

---

## 5. Pull the code from GitHub

Clone the repo under a suitable directory (e.g. `/home/pi/projects`):

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
User=pi
WorkingDirectory=/home/pi/projects/MyPlace/myplace
# Example using the assembled quarkus app launcher
# ExecStart=/usr/bin/java -jar /home/pi/projects/MyPlace/myplace/target/quarkus-app/quarkus-run.jar
# Or if the build produces a runner jar or custom launcher, point to it.
ExecStart=/usr/bin/java -jar /home/pi/projects/MyPlace/myplace/target/quarkus-app/quarkus-run.jar
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

Placeholder: [IMAGE: systemd-service-example.png]

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

Placeholder: [IMAGE: hat-orientation.jpg]

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

## 11. Example quick-start checklist

- [ ] Write Raspberry Pi OS to SD
- [ ] Create `ssh` file and `wpa_supplicant.conf` in boot partition
- [ ] Boot Pi, SSH in and set hostname `airconpi`
- [ ] Install OpenJDK 17, Maven, Git
- [ ] Clone MyPlace and build (or copy built artifacts)
- [ ] Place and enable systemd service
- [ ] Attach HAT and verify serial/SPI device nodes
- [ ] Start service and verify logs

---

If you want, I can:
- Add a ready-made systemd service tailored to the exact artifact your build produces (tell me whether you prefer the assembled `quarkus-app` launcher, a fat jar, or a custom runner jar),
- Add exact device tree overlay examples for a specific RS485/CAN HAT model (tell me the HAT model),
- Add example `config.json` entries used by `MyPlace` to configure serial ports.

