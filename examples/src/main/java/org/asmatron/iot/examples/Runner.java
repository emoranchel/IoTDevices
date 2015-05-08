package org.asmatron.iot.examples;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asmatron.iot.grovepi.GroveDigitalIn;
import org.asmatron.iot.grovepi.GroveDigitalOut;
import org.asmatron.iot.grovepi.GrovePi;
import org.asmatron.iot.grovepi.dio.GrovePiDio;
import org.asmatron.iot.grovepi.pi4j.GrovePi4J;
import org.asmatron.iot.pi.RaspberryPi;
import org.asmatron.iot.pi.devices.BMP085;
import org.asmatron.iot.pi.dio.RaspberryPiDio;
import org.asmatron.iot.pi.pi4j.RaspberryPi4J;

public class Runner {

  public static void main(String[] args) throws Exception {
    File control = new File("RUNNINGSAMPLES");
    control.deleteOnExit();
    if (control.exists()) {
      control.delete();
      System.exit(0);
    }
    if (args.length != 2) {
      System.err.println("You need to provide 2 arguments DIO|PI4J EXAMPLECLASS");
      System.exit(-1);
    }

    control.createNewFile();

    String mode = args[0];
    GrovePi grovePi;
    RaspberryPi pi;
    switch (mode.toLowerCase()) {
      case "dio":
        grovePi = new GrovePiDio();
        pi = new RaspberryPiDio();
        break;
      case "pi4j":
        grovePi = new GrovePi4J();
        pi = new RaspberryPi4J();
        break;
      case "test":
        grovePi = new GrovePiTest();
        pi = new RaspberryPiTest();
        break;
      default:
        throw new IllegalArgumentException("You must provide either DIO or PI4J implementation");
    }
    Example example = (Example) Class.forName("org.asmatron.iot.examples." + args[1]).newInstance();
    final ExecutorService runner = Executors.newSingleThreadExecutor();
    final ExecutorService consoleMonitor = Executors.newSingleThreadExecutor();
    final ExecutorService fileMonitor = Executors.newSingleThreadExecutor();
    final Semaphore lock = new Semaphore(0);
    final AtomicBoolean running = new AtomicBoolean(true);
    final Example.Monitor monitor = running::get;

    runner.execute(() -> {
      try {
        example.run(pi, grovePi, monitor);
      } catch (Exception ex) {
        Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
      }
      lock.release();
    });

    consoleMonitor.execute(() -> {
      try (Scanner scanner = new Scanner(System.in)) {
        String command;
        while (!(command = scanner.next()).equalsIgnoreCase("quit")) {
          System.out.println("Command " + command + " not recognized, try quit");
        }
      }
      running.set(false);
      lock.release();
    });

    fileMonitor.execute(() -> {
      while (control.exists()) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
      }
      running.set(false);
      lock.release();
    });

    lock.acquire();
    running.set(false);
    runner.shutdown();
    consoleMonitor.shutdownNow();
    fileMonitor.shutdownNow();
    runner.awaitTermination(10, TimeUnit.SECONDS);
    control.delete();
    System.exit(0);
  }

  private static class GrovePiTest implements GrovePi {

    @Override
    public GroveDigitalOut getDigitalOut(int digitalPort) throws IOException {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroveDigitalIn getDigitalIn(int digitalPort) throws IOException {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void send(int... command) throws IOException {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  }

  private static class RaspberryPiTest implements RaspberryPi {

    @Override
    public BMP085 getBPM085() throws IOException {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  }
}
