package org.iot.raspberry.grovepi.dio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.dio.DeviceManager;
import jdk.dio.i2cbus.I2CDevice;
import jdk.dio.i2cbus.I2CDeviceConfig;
import org.iot.raspberry.grovepi.GroveDigitalIn;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;

public class GrovePiDio implements GrovePi {

  private final I2CDevice grovePi;

  public GrovePiDio() throws IOException {
    final int i2cBus = 1;                        // Raspberry Pi's I2C bus
    final int address = 0x04;                    // Device address
    final int serialClock = 3400000;             // 3.4MHz Max clock
    final int addressSizeBits = 7;               // Device address size in bits

    I2CDeviceConfig config = new I2CDeviceConfig(i2cBus, address, addressSizeBits, serialClock);
    grovePi = DeviceManager.open(config);

  }

  @Override
  public GroveDigitalOut getDigitalOut(int digitalPort) throws IOException {
    return new GroveDigitalOut(this, digitalPort);
  }

  @Override
  public GroveDigitalIn getDigitalIn(int digitalPort) throws IOException {
    return new GroveDigitalIn();
  }

  @Override
  public void close() {
  }

  @Override
  public void send(int... cmd) throws IOException {
    ByteBuffer command = ByteBuffer.allocateDirect(cmd.length);
    Arrays.stream(cmd).forEach((c) -> command.put((byte) c));
    command.rewind();
    Logger.getLogger("GrovePi").log(Level.INFO, "[DIO]Sending command {0}", Arrays.toString(cmd));
    grovePi.write(command);
  }

}
