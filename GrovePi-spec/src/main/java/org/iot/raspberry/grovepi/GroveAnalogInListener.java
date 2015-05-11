package org.iot.raspberry.grovepi;

public interface GroveAnalogInListener {

  void onChange(double oldValue, double newValue);
}
