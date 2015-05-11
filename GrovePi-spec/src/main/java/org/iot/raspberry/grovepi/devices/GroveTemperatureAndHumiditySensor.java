package org.iot.raspberry.grovepi.devices;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.iot.raspberry.grovepi.GroveDigitalPin;
import org.iot.raspberry.grovepi.GrovePi;
import static org.iot.raspberry.grovepi.GrovePiCommands.*;

@GroveDigitalPin
public class GroveTemperatureAndHumiditySensor {

  private final GrovePi grovePi;
  private final int pin;
  private static final int MODULE_TYPE = 1;

  public GroveTemperatureAndHumiditySensor(GrovePi grovePi, int pin) {
    this.grovePi = grovePi;
    this.pin = pin;
  }

  public GroveTemperatureAndHumidityValue get() throws IOException {
    byte[] data = grovePi.exec((io) -> {
      io.write(dht_temp_cmd, pin, MODULE_TYPE, unused);
      io.sleep(600);
      return io.read(new byte[9]);
    });
    double temp = ByteBuffer.wrap(new byte[]{data[4], data[3], data[2], data[1]}).getFloat();
    double humid = ByteBuffer.wrap(new byte[]{data[8], data[7], data[6], data[5]}).getFloat();
    return new GroveTemperatureAndHumidityValue(temp, humid);
  }

}
