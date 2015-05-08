package org.asmatron.iot.examples;

import org.asmatron.iot.grovepi.GrovePi;
import org.asmatron.iot.pi.RaspberryPi;

public class RunnerTest implements Example {

  private boolean running = true;

  @Override
  public void run(RaspberryPi pi, GrovePi grovePi, Monitor monitor) throws Exception {
    while (monitor.isRunning()) {
      Thread.sleep(1000);
      System.out.println("Running...");
    }
  }
}
