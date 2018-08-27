/**
 * Created by Филипп on 27.08.2018.
 */

import com.filipp.parking.Parking;
import com.filipp.parking.ParkingException;
import com.filipp.parking.Transport;
import com.filipp.parking.TransportType;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ParkingTest extends Assert {

    private final int maxParkingTruckPlaces = 20;
    private final int maxParkingCarPlaces = 10;
    private Parking parking;
    private List<Transport> transports = new ArrayList();
    private Logger log = LoggerFactory.getLogger(ParkingTest.class);
    private int carAmount;
    private int truckAmount;
    private int freeCarParkingPlacesAmount;
    private int freeTruckParkingPlacesAmount;
    private int allFreeParkingPlacesAmount;


    @Test
    public void testParkTransportToEmptyParking() throws ParkingException {
        parking = new Parking(maxParkingCarPlaces, maxParkingTruckPlaces);
        freeCarParkingPlacesAmount = parking.getFreeCarParkingPlacesAmount();
        freeTruckParkingPlacesAmount = parking.getFreeTruckParkingPlacesAmount();
        Transport transport = new Transport(TransportType.Car);
        assertEquals(transport, parking.park(transport));
        assertEquals(freeCarParkingPlacesAmount - 1, parking.getFreeCarParkingPlacesAmount());
        transport = new Transport(TransportType.Truck);
        assertEquals(transport, parking.park(transport));
        assertEquals(freeTruckParkingPlacesAmount - 1, parking.getFreeTruckParkingPlacesAmount());
    }

    @Test
    public void testParkTransportToFullParking() throws ParkingException {
        parking = new Parking(maxParkingCarPlaces, maxParkingTruckPlaces);
        for (int i = 0; i < maxParkingCarPlaces; i++) {
            parking.park(new Transport(TransportType.Car));
        }
        for (int i = 0; i < maxParkingTruckPlaces; i++) {
            parking.park(new Transport(TransportType.Truck));
        }
        assertNull(parking.park(new Transport(TransportType.Car)));
        assertNull(parking.park(new Transport(TransportType.Truck)));
    }

    @Test
    public void testParkTruckOnCarPlace() throws ParkingException {
        parking = new Parking(maxParkingCarPlaces, maxParkingTruckPlaces);
        allFreeParkingPlacesAmount = parking.getAllFreeParkingPlacesAmount();

        Transport truck = new Transport(TransportType.Truck);
        for (int i = 0; i < maxParkingTruckPlaces; i++) {
            parking.park(new Transport(TransportType.Truck));
        }
        assertEquals(0, parking.getFreeTruckParkingPlacesAmount());
        assertEquals(truck, parking.park(truck));
    }

    @Test
    public void testUnparkTransport() throws ParkingException {
        parking = new Parking(maxParkingCarPlaces, maxParkingTruckPlaces);
        Transport transport = new Transport(TransportType.Car);
        for (int i = 0; i < maxParkingCarPlaces; i++) {
            parking.park(new Transport(TransportType.Car));
        }
        for (int i = 0; i < maxParkingTruckPlaces; i++) {
            parking.park(new Transport(TransportType.Truck));
        }
        assertEquals(transport, parking.unPark(transport));
        transport = new Transport(TransportType.Truck);
        assertEquals(transport, parking.unPark(transport));
    }

    @Test
    public void testUnparkTransportFromEmptyParking() throws ParkingException {
        parking = new Parking(maxParkingCarPlaces, maxParkingTruckPlaces);
        assertNull(parking.unPark(new Transport(TransportType.Car)));
        assertNull(parking.unPark(new Transport(TransportType.Truck)));

    }
}
