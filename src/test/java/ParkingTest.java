/**
 * Created by Филипп on 27.08.2018.
 */

import com.filipp.parking.Parking;
import com.filipp.parking.ParkingException;
import com.filipp.parking.Transport;
import com.filipp.parking.TransportType;
import org.junit.Assert;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class ParkingTest extends Assert {

    public static volatile Parking parking = new Parking(20, 10);
    public static volatile List<Transport> transports = new ArrayList();
    private int carAmount = parking.getCarAmount();
    private int truckAmount = parking.getTruckAmount();
    private final int maxParkingTruckPlaces = parking.getMaxParkingTruckPlaces();
    private final int maxParkingCarPlaces= parking.getMaxParkingCarPlaces();
    private int freeCarParkingPlacesAmount = parking.getFreeCarParkingPlacesAmount();
    private int freeTruckParkingPlacesAmount = parking.getFreeTruckParkingPlacesAmount();
    private int allFreeParkingPlacesAmount = parking.getAllFreeParkingPlacesAmount();

    @Before
    public static List<Transport> fillTransportList() {
        List<Transport> transports = new ArrayList<Transport>(100);
        for (int i = 0; i < 100; i++) {
            if ((int) (Math.random() * 100) % 2 == 0) {
                transports.add(new Transport(TransportType. Car));
            } else {
                transports.add(new Transport(TransportType.Truck));
            }
        }
        return transports;
    }

    @Test
    public void testParking() {
        transports = fillTransportList();

        new Thread(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 30; i++) {
                        Thread.sleep(2000);
                        int random = (int) (Math.random() * 99);
                        Transport tr = transports.get(random);
                        assertNotNull(tr);
                        assertEquals(tr,parking.park(transports.get(random)));

                        if (tr.getType().equals(TransportType.Car)){
                            assertEquals(carAmount + 1, parking.getCarAmount());
                            carAmount++;
                            assertEquals(freeCarParkingPlacesAmount-1, parking.getFreeCarParkingPlacesAmount());
                            freeCarParkingPlacesAmount--;
                            assertEquals(allFreeParkingPlacesAmount-1, parking.getAllFreeParkingPlacesAmount());
                            allFreeParkingPlacesAmount--;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ParkingException e) {
                    System.out.println();
                    System.err.println(e);
                }
            }

        }).start();

        new Thread(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(15000);
                    for (int i = 0; i < 30; i++) {
                        Thread.sleep(2000);
                        int random = (int) (Math.random() * 99);
                        parking.unPark(transports.get(random));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ParkingException e) {
                    System.out.println();
                    System.err.println(e);
                }
            }

        }).start();

    }
}
