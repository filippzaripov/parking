package com.filipp.parking;

public class Parking {
    static volatile int maxParkingTruckPlaces;
    static volatile int freeCarParkingPlacesAmount;
    static volatile int freeTruckParkingPlacesAmount;
    static volatile int allFreeParkingPlacesAmount;
    static volatile int carAmount;
    static volatile int truckAmount;

    public Parking(int freeCarParkingPlacesAmount, int freeTruckParkingPlacesAmount) {
        this.allFreeParkingPlacesAmount = freeCarParkingPlacesAmount + freeTruckParkingPlacesAmount;
        this.maxParkingTruckPlaces = freeTruckParkingPlacesAmount;
        this.carAmount = 0;
        this.truckAmount = 0;
    }

    public boolean park(Transport transport){
        if(transport.getType().equals(TransportType.Car)){
             
        }
        return true;
    }

}
