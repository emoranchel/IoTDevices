package org.iot.raspberry.grovepi.devices;

import java.io.IOException;
import org.iot.raspberry.grovepi.GroveI2CPin;
import org.iot.raspberry.grovepi.GrovePiSequenceVoid;

@GroveI2CPin
public abstract class GroveRgbLcd implements AutoCloseable {

  public static final int DISPLAY_RGB_ADDR = 0x62;
  public static final int DISPLAY_TEXT_ADDR = 0x3e;

  public void setRGB(int r, int g, int b) throws IOException {
    execRGB((io) -> {
      io.write(0, 0);
      io.write(1, 0);
      io.write(0x80, 0xaa);
      io.write(4, r);
      io.write(3, g);
      io.write(2, b);
    });
  }

  public void setText(String text) throws IOException {
    execTEXT((io) -> {
      io.write(0x80, 0x01);
      io.sleep(500);
      io.write(0x80, 0x04 | 0x08);
      io.write(0x80, 0x28);
      io.sleep(500);
      int count = 0;
      int row = 0;
      for (char c : text.toCharArray()) {
        if (c == '\n' || count == 16) {
          count = 0;
          row += 1;
          if (row == 2) {
            break;
          }
          io.write(0x80, 0xc0);
          if (c == '\n') {
            continue;
          }
        }
        count++;
        io.write(0x40, c);
      }
    });
  }

  public abstract void execRGB(GrovePiSequenceVoid sequence) throws IOException;

  public abstract void execTEXT(GrovePiSequenceVoid sequence) throws IOException;

  @Override
  public abstract void close();

}
