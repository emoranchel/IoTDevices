package org.asmatron.iot.pi.dio;

import java.io.IOException;
import org.asmatron.iot.pi.RaspberryPi;
import org.asmatron.iot.pi.devices.BMP085;

public class RaspberryPiDio implements RaspberryPi {

  @Override
  public BMP085 getBPM085() throws IOException{
    return new BMP085Dio();
  }

  @Override
  public void close() {
  }

}
