package org.iot.raspberry.pi.dio;

import java.io.IOException;
import org.iot.raspberry.pi.RaspberryPi;
import org.iot.raspberry.pi.devices.BMP085;

public class RaspberryPiDio implements RaspberryPi {

  @Override
  public BMP085 getBPM085() throws IOException {
    return new BMP085Dio();
  }

  @Override
  public void close() {
  }

}
