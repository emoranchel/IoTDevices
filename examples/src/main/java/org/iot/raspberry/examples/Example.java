package org.iot.raspberry.examples;

import java.util.concurrent.atomic.AtomicBoolean;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.pi.RaspberryPi;

public interface Example {

  public void run(RaspberryPi pi, GrovePi grovePi, AtomicBoolean running) throws Exception;

}
