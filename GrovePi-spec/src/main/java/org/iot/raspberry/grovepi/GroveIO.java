package org.iot.raspberry.grovepi;

import java.io.IOException;
import java.util.concurrent.Callable;

public interface GroveIO {

  public void send(int... command) throws IOException;

  public int read() throws IOException;

  default public <T> T exec(Callable<T> command) throws Exception {
    synchronized (this) {
      return command.call();
    }
  }
}
