package org.iot.raspberry.grovepi.dio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.DeviceManager;
import jdk.dio.i2cbus.I2CDevice;
import jdk.dio.i2cbus.I2CDeviceConfig;
import org.iot.raspberry.grovepi.GroveIO;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.GrovePiSequence;
import org.iot.raspberry.grovepi.GrovePiSequenceVoid;

public class GrovePiDio implements GrovePi, GroveIO {

  private final I2CDevice device;

  public GrovePiDio() throws IOException {
    final int i2cBus = 1;                        // Raspberry Pi's I2C bus
    final int address = 0x04;                    // Device address
    final int serialClock = 3400000;             // 3.4MHz Max clock
    final int addressSizeBits = 7;               // Device address size in bits

    I2CDeviceConfig config = new I2CDeviceConfig(i2cBus, address, addressSizeBits, serialClock);
    device = DeviceManager.open(config);

  }

  public <T> T exec(GrovePiSequence<T> sequence) throws IOException {
    synchronized (this) {
      return sequence.execute(this);
    }
  }

  public void execVoid(GrovePiSequenceVoid sequence) throws IOException {
    synchronized (this) {
      sequence.execute(this);
    }
  }

  @Override
  public void close() {
  }

  // IO
  @Override
  public void write(int... cmd) throws IOException {
    ByteBuffer command = ByteBuffer.allocateDirect(cmd.length);
    Arrays.stream(cmd).forEach((c) -> command.put((byte) c));
    command.rewind();
    Logger.getLogger("GrovePi").log(Level.INFO, "[DIO IO write]{0}", Arrays.toString(cmd));
    device.write(command);
  }

  @Override
  public int read() throws IOException {
    final int read = device.read();
    Logger.getLogger("GrovePi").log(Level.INFO, "[DIO IO read]{0}", read);
    return read;
  }

  @Override
  public byte[] read(byte[] buffer) throws IOException {
    ByteBuffer bf = ByteBuffer.wrap(buffer);
    bf.rewind();
    device.read(bf);
    Logger.getLogger("GrovePi").log(Level.INFO, "[DIO IO read]{0}", buffer);
    return buffer;
  }

}
