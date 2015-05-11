package org.iot.raspberry.examples;

import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveSoundSensor;
import org.iot.raspberry.pi.RaspberryPi;

/*
 Connect:
 SoundSensor to A0
 Leds to D2 (red),D3 (green) and D4 (blue)
 */
public class SoundSensor implements Example {

  @Override
  public void run(RaspberryPi pi, GrovePi grovePi, Monitor monitor) throws Exception {
    GroveSoundSensor soundSensor = GroveSoundSensor.build(grovePi, 0);
    GroveDigitalOut redLed = grovePi.getDigitalOut(2);
    GroveDigitalOut greenLed = grovePi.getDigitalOut(3);
    GroveDigitalOut blueLed = grovePi.getDigitalOut(4);
    while (monitor.isRunning()) {
      double soundLevel = soundSensor.get();
      System.out.println(soundLevel);
      if (soundLevel > 800) {
        redLed.set(true);
        blueLed.set(false);
        greenLed.set(false);
      } else if (soundLevel < 200) {
        blueLed.set(true);
        greenLed.set(false);
        redLed.set(false);
      } else {
        greenLed.set(true);
        blueLed.set(false);
        redLed.set(false);
      }
    }
    blueLed.set(false);
    greenLed.set(false);
    redLed.set(false);
  }

}
