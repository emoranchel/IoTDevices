package org.iot.raspberry.examples;

import java.util.concurrent.atomic.AtomicBoolean;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.pi.RaspberryPi;

public class BlinkingLed implements Example {

  @Override
  public void run(RaspberryPi pi, GrovePi grovePi, AtomicBoolean running) throws Exception {
    GroveDigitalOut led = grovePi.getDigitalOut(4);
    boolean state = false;
    while (running.get()) {
      state = !state;
      led.set(state);
      Thread.sleep(500);
    }
    led.set(false);
  }

}
