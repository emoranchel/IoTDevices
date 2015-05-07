package org.asmatron.iot.pi;

import java.io.IOException;
import org.asmatron.iot.pi.devices.BMP085;

public interface RaspberryPi extends AutoCloseable {

  public BMP085 getBPM085() throws IOException;

  @Override
  public void close();

}
