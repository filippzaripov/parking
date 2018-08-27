package com.filipp.parking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Филипп on 26.08.2018.
 */
public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);
    private static volatile Parking parking = new Parking(20, 10);

    private static volatile List<Transport> transports = new ArrayList();

    private static List<Transport> fillTransportList() {
        List<Transport> transports = new ArrayList(100);
        for (int i = 0; i < 100; i++) {
            if ((int) (Math.random() * 100) % 2 == 0) {
                transports.add(new Transport(TransportType.Car));
            } else {
                transports.add(new Transport(TransportType.Truck));
            }
        }
        return transports;
    }

    public static void main(String[] args) {
        transports = fillTransportList();

        new Thread(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 30; i++) {
                        Thread.sleep(2000);
                        int random = (int) (Math.random() * 99);
                        parking.park(transports.get(random));
                    }
                } catch (InterruptedException e) {
                    log.error(e.toString());
                    log.error(e.getStackTrace().toString());
                } catch (ParkingException e) {
                    log.error(e.toString());
                    log.error(e.getStackTrace().toString());
                }
            }

        }).start();

        new Thread(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(4000);
                    for (int i = 0; i < 30; i++) {
                        Thread.sleep(1000);
                        int random = (int) (Math.random() * 99);
                        parking.unPark(transports.get(random));
                    }
                } catch (InterruptedException e) {
                    log.error(e.toString());
                    log.error(e.getStackTrace().toString());
                } catch (ParkingException e) {
                    log.error(e.toString());
                    log.error(e.getStackTrace().toString());
                }
            }

        }).start();
    }
}
