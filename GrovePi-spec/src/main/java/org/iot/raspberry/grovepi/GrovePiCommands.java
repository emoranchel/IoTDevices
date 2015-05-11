package org.iot.raspberry.grovepi;

public class GrovePiCommands {

  public static final int unused = 0x00;
// Command Format
// digitalRead()command format header 
  public static final int dRead_cmd = 0x01;
// digitalWrite() command format header
  public static final int dWrite_cmd = 0x02;
// analogRead() command format header
  public static final int aRead_cmd = 0x03;
// analogWrite() command format header
  public static final int aWrite_cmd = 0x04;
// pinMode() command format header
  public static final int pMode_cmd = 0x05;
// Ultrasonic read
  public static final int uRead_cmd = 0x07;
// Get firmware version
  public static final int version_cmd = 0x08;
// Accelerometer (+/- 1.5g) read
  public static final int acc_xyz_cmd = 0x20;
// RTC get time
  public static final int rtc_getTime_cmd = 0x30;
// DHT Pro sensor temperature
  public static final int dht_temp_cmd = 0x40;

// Grove LED Bar commands
// Initialise
  public static final int ledBarInit_cmd = 0x50;
// Set orientation
  public static final int ledBarOrient_cmd = 0x51;
// Set level
  public static final int ledBarLevel_cmd = 0x52;
// Set single LED
  public static final int ledBarSetOne_cmd = 0x53;
// Toggle single LED
  public static final int ledBarToggleOne_cmd = 0x54;
// Set all LEDs
  public static final int ledBarSet_cmd = 0x55;
// Get current state
  public static final int ledBarGet_cmd = 0x56;

// Grove 4 Digit Display commands
// Initialise
  public static final int fourDigitInit_cmd = 0x70;
// Set brightness, not visible until next cmd
  public static final int fourDigitBrightness_cmd = 0x71;
// Set numeric value without leading zeros
  public static final int fourDigitValue_cmd = 0x72;
// Set numeric value with leading zeros
  public static final int fourDigitValueZeros_cmd = 0x73;
// Set individual digit
  public static final int fourDigitIndividualDigit_cmd = 0x74;
// Set individual leds of a segment
  public static final int fourDigitIndividualLeds_cmd = 0x75;
// Set left and right values with colon
  public static final int fourDigitScore_cmd = 0x76;
// Analog read for n seconds
  public static final int fourDigitAnalogRead_cmd = 0x77;
// Entire display on
  public static final int fourDigitAllOn_cmd = 0x78;
// Entire display off
  public static final int fourDigitAllOff_cmd = 0x79;

// Grove Chainable RGB LED commands
// Store color for later use
  public static final int storeColor_cmd = 0x90;
// Initialise
  public static final int chainableRgbLedInit_cmd = 0x91;
// Initialise and test with a simple color
  public static final int chainableRgbLedTest_cmd = 0x92;
// Set one or more leds to the stored color by pattern
  public static final int chainableRgbLedSetPattern_cmd = 0x93;
// set one or more leds to the stored color by modulo
  public static final int chainableRgbLedSetModulo_cmd = 0x94;
// sets leds similar to a bar graph, reversible
  public static final int chainableRgbLedSetLevel_cmd = 0x95;

  public static final int pMode_out_arg = 0x01;
  public static final int pMode_in_arg = 0x00;
}
