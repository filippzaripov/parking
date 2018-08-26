package com.filipp.parking;

public class Parking {
    static volatile int maxParkingTruckPlaces;
    static volatile int maxParkingCarPlaces;
    static volatile int freeCarParkingPlacesAmount;
    static volatile int freeTruckParkingPlacesAmount;
    static volatile int allFreeParkingPlacesAmount;
    static volatile int carAmount;
    static volatile int truckAmount;

    public Parking(int freeCarParkingPlacesAmount, int freeTruckParkingPlacesAmount) {
        this.freeCarParkingPlacesAmount = freeCarParkingPlacesAmount;
        this.freeTruckParkingPlacesAmount = freeTruckParkingPlacesAmount;
        this.maxParkingTruckPlaces = freeTruckParkingPlacesAmount;
        this.maxParkingCarPlaces = freeCarParkingPlacesAmount;
        this.allFreeParkingPlacesAmount = freeCarParkingPlacesAmount + freeTruckParkingPlacesAmount;
        this.carAmount = 0;
        this.truckAmount = 0;
    }

    /**
     * @param transport
     * @return true if transport was parked successfully, throws Exception in other case
     * @throws ParkingException
     */
    public boolean park(Transport transport) throws ParkingException {
        synchronized (transport) {
            if (transport.getType().equals(TransportType.Car) && freeCarParkingPlacesAmount > 0) {
                freeCarParkingPlacesAmount--;
                carAmount++;
                System.out.println("Some car was parked");
                updatePlaces();
                return true;
            } else if (transport.getType().equals(TransportType.Truck) && freeTruckParkingPlacesAmount > 0) {
                freeTruckParkingPlacesAmount--;
                truckAmount++;
                System.out.println("Some truck was parked on trucks parking place");
                updatePlaces();
                return true;
            } else if (transport.getType().equals(TransportType.Truck) && freeCarParkingPlacesAmount > 1) {
                freeCarParkingPlacesAmount = freeCarParkingPlacesAmount - 2;
                truckAmount++;
                System.out.println("Some truck was parked on cars parking place");
                updatePlaces();
                return true;
            } else if (allFreeParkingPlacesAmount < transport.getPlaces()) {
                System.err.println("Parking is full. Can't park some " + transport.getType());
                return false;
            } else {
                throw new ParkingException("Exception while parking " + transport.getType());
            }
        }
    }

    /**
     * @param transport
     * @return true if was successfully "unparked", throw exception in other case
     * @throws ParkingException
     */
    public boolean unPark(Transport transport) throws ParkingException {
        synchronized (transport) {
            if (transport.getType().equals(TransportType.Car)) {
                if (carAmount > 0) {
                    freeCarParkingPlacesAmount++;
                    carAmount--;
                    System.out.println("Some car was unparked");
                    updatePlaces();
                    return true;
                } else {
                    System.err.println("No cars on parking found while trying to unpark some");
                    return false;
                }
            } else {
                if (truckAmount > 0) {
                    if (freeTruckParkingPlacesAmount < maxParkingTruckPlaces) {
                        freeTruckParkingPlacesAmount++;
                        truckAmount--;
                        System.out.println("Some truck was unparked from truck parking");
                        updatePlaces();
                        return true;
                    } else if (maxParkingCarPlaces - freeCarParkingPlacesAmount > 1) {
                        freeCarParkingPlacesAmount = freeCarParkingPlacesAmount + 2;
                        truckAmount--;
                        System.out.println("Some truck was unparked from car parking");
                        updatePlaces();
                        return true;
                    } else {
                        throw new ParkingException("Unexpected exception while trying to unpark truck");
                    }
                } else {
                    System.err.println("No trucks on parking found while trying to unpark some");
                    return false;
                }
            }
        }
    }

    /**
     * updates all free parking places
     */
    private synchronized void updatePlaces() {

        allFreeParkingPlacesAmount = freeCarParkingPlacesAmount + freeTruckParkingPlacesAmount;
        System.out.println("freeCarParkingPlacesAmount : " + freeCarParkingPlacesAmount);
        System.out.println("freeTruckParkingPlacesAmount : " + freeTruckParkingPlacesAmount);
        System.out.println("allFreeParkingPlacesAmount : " + allFreeParkingPlacesAmount);
        System.out.println("Cars on parking : " + carAmount);
        System.out.println("Trucks on parking : " + truckAmount);
        System.out.println("---------------------------------------");

    }
}
