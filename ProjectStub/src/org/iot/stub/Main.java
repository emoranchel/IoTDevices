package org.iot.stub;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.dio.GrovePiDio;
import org.iot.raspberry.pi.RaspberryPi;
import org.iot.raspberry.pi.dio.RaspberryPiDio;

public class Main {

  public static void run(RaspberryPi pi, GrovePi grovePi, AtomicBoolean running) {
    //Your App goes here
  }

  public static void main(String[] args) throws Exception {
    Logger.getLogger("DIO").setLevel(Level.WARNING);
    Logger.getLogger("GrovePi").setLevel(Level.WARNING);
    Logger.getLogger("RaspberryPi").setLevel(Level.WARNING);

    File control = new File("LOCKFILE");
    control.deleteOnExit();
    if (control.exists()) {
      control.delete();
      System.exit(0);
    }

    control.createNewFile();

    //select your implementation
    // Device IO Implementation
    GrovePi grovePi = new GrovePiDio();
    RaspberryPi pi = new RaspberryPiDio();
    // PI4J Implementation
//    GrovePi grovePi = new GrovePi4J();
//    RaspberryPi pi = new RaspberryPi4J();

    //Your project runner
    final ExecutorService runner = Executors.newSingleThreadExecutor();
    //The console monitor
    final ExecutorService consoleMonitor = Executors.newSingleThreadExecutor();
    //The file lock monitor
    final ExecutorService fileMonitor = Executors.newSingleThreadExecutor();

    final Semaphore lock = new Semaphore(0);
    final AtomicBoolean running = new AtomicBoolean(true);

    runner.execute(() -> {
      try {
        run(pi, grovePi, running);
      } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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

}
