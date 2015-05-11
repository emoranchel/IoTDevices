package org.iot.raspberry.examples;

import org.iot.raspberry.grovepi.GroveDigitalIn;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.pi.RaspberryPi;
/*
 Connect: Led to D4 and Button to D6
 */

public class ButtonLed implements Example {

  @Override
  public void run(RaspberryPi pi, GrovePi grovePi, Monitor monitor) throws Exception {
    GroveDigitalIn button = grovePi.getDigitalIn(6);
    GroveDigitalOut led = grovePi.getDigitalOut(4);
    while (monitor.isRunning()) {
      try {
        led.set(button.get());
      } catch (Exception e) {
      }
    }
    led.set(false);
  }

}
