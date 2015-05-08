package org.iot.raspberry.examples;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.pi.RaspberryPi;

public interface Example {

  public void run(RaspberryPi pi, GrovePi grovePi, Monitor monitor) throws Exception;

}
