package org.iot.raspberry.pi.pi4j;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.pi.devices.BMP085;

public class BMP085Pi4J extends BMP085 {

  private final I2CDevice bmp085;

  public BMP085Pi4J(I2CBus bus) throws IOException {
    bmp085 = bus.getDevice(address);
  }

  @Override
  public int read(int startAddress, int subAddressSize, ByteBuffer data) throws IOException {
    return bmp085.read(address, data.array(), i2cBus, i2cBus);
  }

  @Override
  public void write(int startAddress, int subAddressSize, ByteBuffer data) throws IOException {
    bmp085.write(startAddress, data.array(), 0, data.array().length);
  }

  @Override
  public void close() {
  }

}
