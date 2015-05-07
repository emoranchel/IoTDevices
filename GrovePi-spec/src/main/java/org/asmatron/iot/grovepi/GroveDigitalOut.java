package org.asmatron.iot.grovepi;

import java.io.IOException;
import static org.asmatron.iot.grovepi.GrovePiCommands.*;

public class GroveDigitalOut {

  private final GrovePi grovePi;
  private final int pin;

  public GroveDigitalOut(GrovePi grovePi, int pin) throws IOException {
    this.grovePi = grovePi;
    this.pin = pin;
    grovePi.send(pMode_cmd, pin, pMode_out_arg, unused);
  }

  public void set(boolean value) throws IOException {
    int val = value ? 1 : 0;
    grovePi.send(dWrite_cmd, pin, val, unused);

  }
}
