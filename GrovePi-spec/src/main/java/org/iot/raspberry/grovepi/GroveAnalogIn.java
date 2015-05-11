package org.iot.raspberry.grovepi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.iot.raspberry.grovepi.GrovePiCommands.*;

public class GroveAnalogIn implements Runnable {

  private final GrovePi grovePi;
  private final int pin;
  private double value = 0.0;
  private GroveAnalogInListener listener;

  public GroveAnalogIn(GrovePi grovePi, int pin) throws IOException {
    this.grovePi = grovePi;
    this.pin = pin;
    grovePi.execVoid((io) -> io.write(pMode_cmd, pMode_in_arg, pin, unused));
  }

  @Override
  public void run() {
    try {
      get();
    } catch (IOException ex) {
      Logger.getLogger("GrovePi").log(Level.SEVERE, null, ex);
    }
  }

  public double get() throws IOException {
    double val = grovePi.exec((io) -> {
      io.write(aRead_cmd, pin, unused, unused);
      io.sleep(100);
      io.read();
      byte[] response = io.read(new byte[2]);
      return (response[0] * 256) + response[1];
    });
    if (listener != null && value != val) {
      listener.onChange(value, val);
    }
    this.value = val;
    return val;
  }

  public void setListener(GroveAnalogInListener listener) {
    this.listener = listener;
  }

}
