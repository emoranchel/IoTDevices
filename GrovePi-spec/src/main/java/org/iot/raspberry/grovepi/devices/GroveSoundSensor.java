package org.iot.raspberry.grovepi.devices;

import java.io.IOException;
import org.iot.raspberry.grovepi.GroveAnalogIn;
import org.iot.raspberry.grovepi.GroveAnalogInListener;
import org.iot.raspberry.grovepi.GrovePi;

public class GroveSoundSensor implements Runnable {

  public static GroveSoundSensor build(GrovePi grovePi, int pin) throws IOException {
    return new GroveSoundSensor(grovePi.getAnalogIn(pin));
  }
  private final GroveAnalogIn in;

  private GroveSoundSensor(GroveAnalogIn in) {
    this.in = in;
  }

  public void setListener(GroveAnalogInListener listener) {
    in.setListener(listener);
  }

  public double get() throws IOException {
    return in.get();
  }

  @Override
  public void run() {
    in.run();
  }

}
