package com.filipp.parking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Филипп on 26.08.2018.
 */
public class Main {
    public static volatile Parking parking = new Parking(20, 10);

    public static volatile List<Transport> transports = new ArrayList();

    public static List<Transport> fillTransportList() {
        List<Transport> transports = new ArrayList<Transport>(100);
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
                        if (random % 2 == 0) {
                            parking.park(transports.get(random));
                        } else {
                            parking.unPark(transports.get(random));
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ParkingException e) {
                    System.out.println(e);
                    System.err.println(e);
                }
            }

        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 30; i++) {
                        Thread.sleep(2000);
                        int random = (int) (Math.random() * 99);
                        if (random % 2 == 0) {
                            parking.park(transports.get(random));
                        } else {
                            parking.unPark(transports.get(random));
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ParkingException e) {
                    System.out.println(e);
                    System.err.println(e);
                }
            }

        }).start();
    }
}
