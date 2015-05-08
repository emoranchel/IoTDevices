package org.iot.raspberry.pi.devices;

public enum BMP085Mode {
  // Relationship between Oversampling Setting and conversion delay (in ms) for each Oversampling Setting constant
  // Ultra low power:        4.5 ms minimum conversion delay
  // Standard:               7.5 ms 
  // High Resolution:       13.5 ms
  // Ultra high Resolution: 25.5 ms

  ULTRA_LOW_POWER(0, 5), STANDARD(1, 8), HIGH_RESOLUTION(2, 14), ULTRA_HIGH_RESOLUTION(3, 26);
  private final int oss;                                      // Over sample setting value
  private final int delay;                                    // Minimum conversion time in ms
  private static final byte getPressCmd = (byte) 0x34;        // Read pressure command
  private final byte cmd;                                     // Command byte to read pressure

  BMP085Mode(int oss, int delay) {
    this.oss = oss;
    this.delay = delay;
    this.cmd = (byte) (getPressCmd + ((oss << 6) & 0xC0));
  }
// Return the conversion delay (in ms) associated with this oversampling setting

  public int getDelay() {
    return delay;
  }

  // Return the command to the control register for this oversampling setting
  public byte getCommand() {
    return cmd;
  }

  // Return this oversampling setting
  public int getOSS() {
    return oss;
  }
}
