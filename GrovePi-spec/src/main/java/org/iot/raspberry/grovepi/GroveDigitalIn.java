package org.iot.raspberry.grovepi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.iot.raspberry.grovepi.GrovePiCommands.*;

public class GroveDigitalIn implements Runnable {

  private final GroveIO grovePi;
  private final int pin;
  private boolean status = false;
  private GroveDigitalInListener listener;

  public GroveDigitalIn(GroveIO grovePi, int pin) {
    this.grovePi = grovePi;
    this.pin = pin;
  }

  public boolean get() throws IOException, InterruptedException {
    try {
      boolean st = grovePi.exec(() -> {
        grovePi.send(dRead_cmd, pin, unused, unused);
        Thread.sleep(100);
        return grovePi.read() == 1;
      });
      if (listener != null && status != st) {
        listener.onChange(status, st);
      }
      this.status = st;
      return st;
    } catch (Exception ex) {
      if (ex instanceof IOException) {
        throw (IOException) ex;
      }
      if (ex instanceof InterruptedException) {
        throw (IOException) ex;
      }
      throw new RuntimeException(ex);
    }
  }

  public void setListener(GroveDigitalInListener listener) {
    this.listener = listener;
  }

  @Override
  public void run() {
    try {
      get();
    } catch (IOException ex) {
      Logger.getLogger("GrovePi").log(Level.SEVERE, null, ex);
    } catch (InterruptedException ex) {
      Logger.getLogger("GrovePi").log(Level.SEVERE, null, ex);
    }
  }

}
