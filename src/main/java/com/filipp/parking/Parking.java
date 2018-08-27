package com.filipp.parking;

public class Parking {
    static volatile int maxParkingTruckPlaces;
    static volatile int maxParkingCarPlaces;
    static volatile int freeCarParkingPlacesAmount;
    static volatile int freeTruckParkingPlacesAmount;
    static volatile int allFreeParkingPlacesAmount;
    static volatile int carAmount;
    static volatile int truckAmount;

    public static int getMaxParkingTruckPlaces() {
        return maxParkingTruckPlaces;
    }

    public static void setMaxParkingTruckPlaces(int maxParkingTruckPlaces) {
        Parking.maxParkingTruckPlaces = maxParkingTruckPlaces;
    }

    public static int getMaxParkingCarPlaces() {
        return maxParkingCarPlaces;
    }

    public static void setMaxParkingCarPlaces(int maxParkingCarPlaces) {
        Parking.maxParkingCarPlaces = maxParkingCarPlaces;
    }

    public static int getFreeCarParkingPlacesAmount() {
        return freeCarParkingPlacesAmount;
    }

    public static void setFreeCarParkingPlacesAmount(int freeCarParkingPlacesAmount) {
        Parking.freeCarParkingPlacesAmount = freeCarParkingPlacesAmount;
    }

    public static int getFreeTruckParkingPlacesAmount() {
        return freeTruckParkingPlacesAmount;
    }

    public static void setFreeTruckParkingPlacesAmount(int freeTruckParkingPlacesAmount) {
        Parking.freeTruckParkingPlacesAmount = freeTruckParkingPlacesAmount;
    }

    public static int getAllFreeParkingPlacesAmount() {
        return allFreeParkingPlacesAmount;
    }

    public static void setAllFreeParkingPlacesAmount(int allFreeParkingPlacesAmount) {
        Parking.allFreeParkingPlacesAmount = allFreeParkingPlacesAmount;
    }

    public static int getCarAmount() {
        return carAmount;
    }

    public static void setCarAmount(int carAmount) {
        Parking.carAmount = carAmount;
    }

    public static int getTruckAmount() {
        return truckAmount;
    }

    public static void setTruckAmount(int truckAmount) {
        Parking.truckAmount = truckAmount;
    }

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
     * @return Transport if transport was parked successfully, null if not, throws Exception in other cases
     * @throws ParkingException
     */
    public Transport park(Transport transport) throws ParkingException {
        synchronized (transport) {
            if (transport.getType().equals(TransportType.Car) && freeCarParkingPlacesAmount > 0) {
                freeCarParkingPlacesAmount--;
                carAmount++;
                System.out.println("Some car was parked");
                updatePlaces();
                return transport;
            } else if (transport.getType().equals(TransportType.Truck) && freeTruckParkingPlacesAmount > 0) {
                freeTruckParkingPlacesAmount--;
                truckAmount++;
                System.out.println("Some truck was parked on trucks parking place");
                updatePlaces();
                return transport;
            } else if (transport.getType().equals(TransportType.Truck) && freeCarParkingPlacesAmount > 1) {
                freeCarParkingPlacesAmount = freeCarParkingPlacesAmount - 2;
                truckAmount++;
                System.out.println("Some truck was parked on cars parking place");
                updatePlaces();
                return transport;
            } else if (allFreeParkingPlacesAmount < transport.getPlaces()) {
                System.err.println("Parking is full. Can't park some " + transport.getType());
                return null;
            } else {
                throw new ParkingException("Exception while parking " + transport.getType());
            }
        }
    }

    /**
     * @param transport
     * @return Transport if was successfully "unparked", null if not, throws exception in other cases
     * @throws ParkingException
     */
    public Transport unPark(Transport transport) throws ParkingException {
        synchronized (transport) {
            if (transport.getType().equals(TransportType.Car)) {
                if (carAmount > 0) {
                    freeCarParkingPlacesAmount++;
                    carAmount--;
                    System.out.println("Some car was unparked");
                    updatePlaces();
                    return transport;
                } else {
                    System.err.println("No cars on parking found while trying to unpark some");
                    return null;
                }
            } else {
                if (truckAmount > 0) {
                    if (freeTruckParkingPlacesAmount < maxParkingTruckPlaces) {
                        freeTruckParkingPlacesAmount++;
                        truckAmount--;
                        System.out.println("Some truck was unparked from truck parking");
                        updatePlaces();
                        return transport;
                    } else if (maxParkingCarPlaces - freeCarParkingPlacesAmount > 1) {
                        freeCarParkingPlacesAmount = freeCarParkingPlacesAmount + 2;
                        truckAmount--;
                        System.out.println("Some truck was unparked from car parking");
                        updatePlaces();
                        return transport;
                    } else {
                        throw new ParkingException("Unexpected exception while trying to unpark truck");
                    }
                } else {
                    System.err.println("No trucks on parking found while trying to unpark some");
                    return null;
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
