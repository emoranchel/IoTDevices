package org.iot.raspberry.pi.dio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.DeviceManager;
import jdk.dio.i2cbus.I2CDevice;
import jdk.dio.i2cbus.I2CDeviceConfig;
import org.iot.raspberry.pi.devices.BMP085;

public class BMP085Dio extends BMP085 {

  private I2CDevice bmp085;

  public BMP085Dio() throws IOException {
    I2CDeviceConfig config = new I2CDeviceConfig(i2cBus, address, addressSizeBits, serialClock);
    this.bmp085 = DeviceManager.open(config);
    this.initialize();
  }

  @Override
  public int read(int startAddress, int subAddressSize, ByteBuffer data) throws IOException {
    return bmp085.read(startAddress, subAddressSize, data);
  }

  @Override
  public void write(int startAddress, int subAddressSize, ByteBuffer data) throws IOException {
    bmp085.write(startAddress, subAddressSize, data);
  }

  @Override
  public void close() {
    try {
      bmp085.close();
    } catch (IOException ex) {
      Logger.getLogger("RaspberryPi").log(Level.SEVERE, null, ex);
    }
  }

}
