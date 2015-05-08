package org.iot.raspberry.grovepi;

import java.io.IOException;

public interface GroveIO {

  public void send(int... command) throws IOException;

  public int read() throws IOException;
}
