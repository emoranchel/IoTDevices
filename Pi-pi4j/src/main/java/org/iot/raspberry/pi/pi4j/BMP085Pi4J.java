package org.iot.raspberry.pi.pi4j;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import java.io.IOException;
import org.iot.raspberry.pi.devices.BMP085;

public class BMP085Pi4J extends BMP085 {

  private final I2CDevice bmp085;

  public BMP085Pi4J(I2CBus bus) throws IOException {
    bmp085 = bus.getDevice(address);
    this.initialize();
  }

  @Override
  public byte[] read(int startAddress, int subAddressSize, byte[] data) throws IOException {
    bmp085.read(address, data, i2cBus, i2cBus);
    return data;
  }

  @Override
  public void write(int startAddress, int subAddressSize, byte[] data) throws IOException {
    bmp085.write(startAddress, data, 0, data.length);
  }

  @Override
  public void close() {
  }

}
