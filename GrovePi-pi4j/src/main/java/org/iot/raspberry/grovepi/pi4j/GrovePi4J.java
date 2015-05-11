package org.iot.raspberry.grovepi.pi4j;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GroveIO;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.GrovePiSequence;
import org.iot.raspberry.grovepi.GrovePiSequenceVoid;

public class GrovePi4J implements GrovePi, GroveIO {

  private static final int GROVEPI_ADDRESS = 4;
  private final I2CBus bus;
  private final I2CDevice device;

  public GrovePi4J() throws IOException {
    this.bus = I2CFactory.getInstance(I2CBus.BUS_1);
    this.device = bus.getDevice(GROVEPI_ADDRESS);
  }

  @Override
  public <T> T exec(GrovePiSequence<T> sequence) throws IOException {
    synchronized (this) {
      return sequence.execute(this);
    }
  }

  @Override
  public void execVoid(GrovePiSequenceVoid sequence) throws IOException {
    synchronized (this) {
      sequence.execute(this);
    }
  }

  @Override
  public void close() {
    try {
      bus.close();
    } catch (IOException ex) {
      Logger.getLogger("GrovePi").log(Level.SEVERE, null, ex);
    }
  }

  // IO
  @Override
  public void write(int... command) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocateDirect(command.length);
    Arrays.stream(command).forEach((c) -> buffer.put((byte) c));
    device.write(buffer.array(), 0, command.length);
  }

  @Override
  public int read() throws IOException {
    return device.read();
  }

  @Override
  public byte[] read(byte[] buffer) throws IOException {
    device.read(buffer, 0, buffer.length);
    return buffer;
  }
}
