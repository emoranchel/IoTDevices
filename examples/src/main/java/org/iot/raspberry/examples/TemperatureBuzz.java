package org.iot.raspberry.examples;

import java.io.IOException;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.pi.RaspberryPi;
import org.iot.raspberry.pi.devices.BMP085;
import org.iot.raspberry.pi.devices.BMP085Mode;
import org.iot.raspberry.pi.devices.BMP085Result;
/*
 Connect: Led to D4 and Buzzer to D3
 Temperature BMP085 to Rpi I2C bus
 */

public class TemperatureBuzz implements Example {

  @Override
  public void run(RaspberryPi pi, GrovePi grovePi, Monitor monitor) throws Exception {
    GroveDigitalOut led = grovePi.getDigitalOut(4);
    GroveDigitalOut buzzer = grovePi.getDigitalOut(3);
    BMP085 bmp085 = pi.getBPM085();
    while (monitor.isRunning()) {
      BMP085Result temperaturePressure = bmp085.getTemperaturePressure(BMP085Mode.STANDARD);
      System.out.println(temperaturePressure);
      boolean overheat = temperaturePressure.getTemperatureCelsius() > 31;
      try {
        led.set(overheat);
        buzzer.set(overheat);
      } catch (IOException ex) {
      }
      Thread.sleep(500);
    }
    led.set(false);
    buzzer.set(false);
  }
}
