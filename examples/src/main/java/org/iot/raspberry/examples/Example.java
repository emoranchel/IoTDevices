package org.iot.raspberry.examples;

import org.asmatron.iot.grovepi.GrovePi;
import org.asmatron.iot.pi.RaspberryPi;

public interface Example {

  public void run(RaspberryPi pi, GrovePi grovePi, Monitor monitor) throws Exception;

  interface Monitor {

    boolean isRunning();
  }

}
