package org.asmatron.iot.grovepi;

import java.io.IOException;

public interface GrovePi extends AutoCloseable {

  public GroveDigitalOut getDigitalOut(int digitalPort) throws IOException;

  public GroveDigitalIn getDigitalIn(int digitalPort) throws IOException;

  @Override
  public void close();

  public void send(int... command) throws IOException;

}
