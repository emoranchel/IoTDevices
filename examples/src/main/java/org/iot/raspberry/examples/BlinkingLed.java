package org.iot.raspberry.examples;

import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.pi.RaspberryPi;

/*
 Connect: Led to D4
 */
public class BlinkingLed implements Example {

  @Override
  public void run(RaspberryPi pi, GrovePi grovePi, Monitor monitor) throws Exception {
    GroveDigitalOut led = grovePi.getDigitalOut(4);
    boolean state = false;
    while (monitor.isRunning()) {
      state = !state;
      led.set(state);
      Thread.sleep(500);
    }
    led.set(false);
  }

}
