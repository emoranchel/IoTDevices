package org.iot.raspberry.pi.pi4j;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.pi.RaspberryPi;
import org.iot.raspberry.pi.devices.BMP085;

public class RaspberryPi4J implements RaspberryPi {

  private final I2CBus bus;

  public RaspberryPi4J() throws IOException {
    bus = I2CFactory.getInstance(I2CBus.BUS_1);
  }

  @Override
  public BMP085 getBPM085() throws IOException {
    return new BMP085Pi4J(bus);
  }

  @Override
  public void close() {
    try {
      bus.close();
    } catch (IOException ex) {
      Logger.getLogger("RaspberryPi").log(Level.SEVERE, null, ex);
    }
  }

}
