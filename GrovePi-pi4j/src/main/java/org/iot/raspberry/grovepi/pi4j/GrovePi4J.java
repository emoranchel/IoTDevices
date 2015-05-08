package org.iot.raspberry.grovepi.pi4j;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GroveDigitalIn;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GroveIO;
import org.iot.raspberry.grovepi.GrovePi;

public class GrovePi4J implements GrovePi, GroveIO {

  private static final int GROVEPI_ADDRESS = 4;
  private final I2CBus bus;
  private final I2CDevice device;

  public GrovePi4J() throws IOException {
    this.bus = I2CFactory.getInstance(I2CBus.BUS_1);
    this.device = bus.getDevice(GROVEPI_ADDRESS);
  }

  @Override
  public GroveDigitalOut getDigitalOut(int digitalPort) throws IOException {
    return new GroveDigitalOut(this, digitalPort);
  }

  @Override
  public GroveDigitalIn getDigitalIn(int digitalPort) throws IOException {
    return new GroveDigitalIn(this, digitalPort);
  }

  @Override
  public void close() {
    try {
      bus.close();
    } catch (IOException ex) {
      Logger.getLogger("GrovePi").log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void send(int... command) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocateDirect(command.length);
    Arrays.stream(command).forEach((c) -> buffer.put((byte) c));
    device.write(buffer.array(), 0, command.length);
  }

  @Override
  public int read() throws IOException {
    return device.read();
  }

}
