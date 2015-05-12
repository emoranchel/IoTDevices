package org.iot.raspberry.pi.devices;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class BMP085 implements AutoCloseable {

  // EEPROM registers - these represent calibration data
  private short AC1;
  private short AC2;
  private short AC3;
  private int AC4;
  private int AC5;
  private int AC6;
  private short B1;
  private short B2;
  private short MB;
  private short MC;
  private short MD;
  private static final int CALIB_BYTES = 22;

  // Uncompensated temperature
  private int UT;

  // Uncompensated pressure
  private int UP;

  // Variables common between temperature and pressure calculations
  private int B5;

  // EEPROM address data
  private static final int EEPROM_start = 0xAA;
  private static final int EEPROM_end = 0xBF;

  // Device Information
  public static final int i2cBus = 1;                        // Raspberry Pi's I2C bus
  public static final int address = 0x77;                    // Device address
  public static final int serialClock = 3400000;             // 3.4MHz Max clock
  public static final int addressSizeBits = 7;               // Device address size in bits

  // Conversion Timing data - pressure conversion delays
  // are defined in the BMP085Mode enum
  private static final int tempConvTime = 5;                  // Max delay time of 4.5 ms

  // Temperature and Pressure Control Register Data
  private static final int controlRegister = 0xF4;            // Control register address
  private static final int tempAddr = 0xF6;                   // Temperature read address
  private static final int pressAddr = 0xF6;                  // Pressure read address
  private static final byte getTempCmd = (byte) 0x2E;         // Read temperature command
  private static final byte getPressCmd = (byte) 0x34;        // Read pressure command

  // Address byte length
  private static final int subAddressSize = 1;                // Size of each address (in bytes)

  // ByteBuffer objects hold the data written/read from the sensor
  public abstract byte[] read(int startAddress, int subAddressSize, byte[] data) throws IOException;

  public abstract void write(int startAddress, int subAddressSize, byte[] data) throws IOException;

  @Override
  public abstract void close();

  protected final void initialize() throws IOException {
    // Read all of the calibration data into a byte array
    byte[] readdata = read(EEPROM_start, subAddressSize, new byte[CALIB_BYTES]);
    ByteBuffer calibData = ByteBuffer.wrap(readdata);
    // Read each of the pairs of data as a signed short
    calibData.rewind();
    AC1 = calibData.getShort();
    AC2 = calibData.getShort();
    AC3 = calibData.getShort();

    // Unsigned short values
    byte[] data = new byte[2];
    calibData.get(data);
    AC4 = (((data[0] << 8) & 0xFF00) + (data[1] & 0xFF));
    calibData.get(data);
    AC5 = (((data[0] << 8) & 0xFF00) + (data[1] & 0xFF));
    calibData.get(data);
    AC6 = (((data[0] << 8) & 0xFF00) + (data[1] & 0xFF));

    // Signed sort values
    B1 = calibData.getShort();
    B2 = calibData.getShort();
    MB = calibData.getShort();
    MC = calibData.getShort();
    MD = calibData.getShort();
  }

  private float getTemperature(BMP085Mode mode) throws IOException {
    // Write the read temperature command to the command register
    write(controlRegister, subAddressSize, new byte[]{getTempCmd});

    // Delay before reading the temperature
    try {
      Thread.sleep(mode.getDelay());
    } catch (InterruptedException ex) {
    }

    byte[] result = read(tempAddr, subAddressSize, new byte[2]);
    ByteBuffer uncompTemp = ByteBuffer.wrap(result);
    // Get the uncompensated temperature as a signed two byte word
    uncompTemp.rewind();
    byte[] data = new byte[2];
    uncompTemp.get(data);
    UT = ((data[0] << 8) & 0xFF00) + (data[1] & 0xFF);

    // Calculate the actual temperature
    int X1 = ((UT - AC6) * AC5) >> 15;
    int X2 = (MC << 11) / (X1 + MD);
    B5 = X1 + X2;
    float celsius = (float) ((B5 + 8) >> 4) / 10;

    return celsius;
  }

  private float getPressure(BMP085Mode mode) throws IOException {
    // The pressure command is calculated by the enum
    // Write the read pressure command to the command register
    write(controlRegister, subAddressSize, new byte[]{mode.getCommand()});

    // Delay before reading the pressure - use the value determined by the oversampling setting (mode)
    try {
      Thread.sleep(mode.getDelay());
    } catch (InterruptedException ex) {
    }

    // Read the uncompensated pressure value
    byte[] result = read(pressAddr, subAddressSize, new byte[3]);
    ByteBuffer uncompPress = ByteBuffer.wrap(result);
    // Get the uncompensated pressure as a three byte word
    uncompPress.rewind();
    byte[] data = new byte[3];
    uncompPress.get(data);
    UP = ((((data[0] << 16) & 0xFF0000) + ((data[1] << 8) & 0xFF00) + (data[2] & 0xFF)) >> (8 - mode.getOSS()));

    // Calculate the true pressure
    int B6 = B5 - 4000;
    int X1 = (B2 * (B6 * B6) >> 12) >> 11;
    int X2 = AC2 * B6 >> 11;
    int X3 = X1 + X2;
    int B3 = ((((AC1 * 4) + X3) << mode.getOSS()) + 2) / 4;
    X1 = AC3 * B6 >> 13;
    X2 = (B1 * ((B6 * B6) >> 12)) >> 16;
    X3 = ((X1 + X2) + 2) >> 2;
    int B4 = (AC4 * (X3 + 32768)) >> 15;
    int B7 = (UP - B3) * (50000 >> mode.getOSS());

    int Pa;
    if (B7 < 0x80000000) {
      Pa = (B7 * 2) / B4;
    } else {
      Pa = (B7 / B4) * 2;
    }

    X1 = (Pa >> 8) * (Pa >> 8);
    X1 = (X1 * 3038) >> 16;
    X2 = (-7357 * Pa) >> 16;

    Pa += ((X1 + X2 + 3791) >> 4);

    return (float) (Pa) / 100;
  }

  public BMP085Result getTemperaturePressure(BMP085Mode mode) throws IOException {
    float temperature, pressure;
    try {
      temperature = getTemperature(mode);
    } catch (IOException ex) {
      System.out.println("getTemperature");
      throw ex;
    }
    try {
      pressure = getPressure(mode);
    } catch (IOException ex) {
      System.out.println("getPressure");
      throw ex;
    }
    return new BMP085Result(temperature, pressure);
  }
}
