package org.asmatron.iot.examples;

import org.asmatron.iot.grovepi.GroveDigitalOut;
import org.asmatron.iot.grovepi.GrovePi;
import org.asmatron.iot.pi.RaspberryPi;

public class Led implements Example {

  @Override
  public void run(RaspberryPi pi, GrovePi grovePi, Monitor monitor) throws Exception {
    GroveDigitalOut led = grovePi.getDigitalOut(4);
    boolean state = false;
    led.set(state);
    while (monitor.isRunning()) {
      Thread.sleep(500);
      state = !state;
      led.set(state);
    }
    led.set(false);
  }

}
