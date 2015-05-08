package org.iot.raspberry.examples;

import java.util.concurrent.atomic.AtomicBoolean;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.pi.RaspberryPi;

public class ButtonLed implements Example {

  @Override
  public void run(RaspberryPi pi, GrovePi grovePi, AtomicBoolean running) throws Exception {
    while (running.get()) {

    }
  }

}
