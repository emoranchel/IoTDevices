/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package org.iot.raspberry.pi.devices;

/**
 *
 * @author Eduardo
 */
public class BMP085Result {

  private final float temperature;
  private final float pressure;

  public BMP085Result(float temperature, float pressure) {
    this.temperature = temperature;
    this.pressure = pressure;
  }

  public float getPressurePascal() {
    return pressure;
  }

  public float getPressureInchesMercury() {
    return (float) (pressure * 0.0296);
  }

  public float getTemperatureCelsius() {
    return temperature;
  }

  public float getTemperatureFahrenheit() {
    return (float) ((temperature * 1.8) + 32);
  }

  @Override
  public String toString() {
    return String.format("TEMPERATURE: %.2f C / %.2f F PRESSURE: %.2f Pas / %.2f HGi", getTemperatureCelsius(), getTemperatureFahrenheit(), getPressurePascal(), getPressureInchesMercury());
  }

}
