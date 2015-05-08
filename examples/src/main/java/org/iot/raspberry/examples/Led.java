package org.iot.raspberry.examples;

import org.asmatron.iot.grovepi.GroveDigitalOut;
import org.asmatron.iot.grovepi.GrovePi;
import org.asmatron.iot.pi.RaspberryPi;

public class Led implements Example {

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
