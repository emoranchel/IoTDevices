package org.iot.raspberry.examples;

import java.io.IOException;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveRgbLcd;
import org.iot.raspberry.pi.RaspberryPi;
import org.iot.raspberry.pi.devices.BMP085;
import org.iot.raspberry.pi.devices.BMP085Mode;
import org.iot.raspberry.pi.devices.BMP085Result;

public class TemperatureMonitor implements Example {
  
  @Override
  public void run(RaspberryPi pi, GrovePi grovePi, Monitor monitor) throws Exception {
    final BMP085 bpM085 = pi.getBPM085();
    final GroveRgbLcd lcd = grovePi.getLCD();
    lcd.setRGB(255, 255, 255);
    while (monitor.isRunning()) {
      try {
        BMP085Result temPress = bpM085.getTemperaturePressure(BMP085Mode.STANDARD);
        String message = String.format("TEMP:%.2fC\nPRESS:%.2fpas", temPress.getTemperatureCelsius(), temPress.getPressurePascal());
        System.out.println(message);
        lcd.setText(message);
        Thread.sleep(3000);
      } catch (IOException e) {
        System.out.println("ERROR");
      }
    }
  }
}
