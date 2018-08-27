package com.filipp.parking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parking {
    private volatile int maxParkingTruckPlaces;
    private volatile int maxParkingCarPlaces;
    private volatile int freeCarParkingPlacesAmount;
    private volatile int freeTruckParkingPlacesAmount;
    private volatile int allFreeParkingPlacesAmount;
    private volatile int carAmount;
    private volatile int truckAmount;
    private static Logger log = LoggerFactory.getLogger(Parking.class);

    public int getMaxParkingTruckPlaces() {
        return maxParkingTruckPlaces;
    }

    public void setMaxParkingTruckPlaces(int maxParkingTruckPlaces) {
        this.maxParkingTruckPlaces = maxParkingTruckPlaces;
    }

    public int getMaxParkingCarPlaces() {
        return maxParkingCarPlaces;
    }

    public void setMaxParkingCarPlaces(int maxParkingCarPlaces) {
        this.maxParkingCarPlaces = maxParkingCarPlaces;
    }

    public int getFreeCarParkingPlacesAmount() {
        return freeCarParkingPlacesAmount;
    }

    public void setFreeCarParkingPlacesAmount(int freeCarParkingPlacesAmount) {
        this.freeCarParkingPlacesAmount = freeCarParkingPlacesAmount;
    }

    public int getFreeTruckParkingPlacesAmount() {
        return freeTruckParkingPlacesAmount;
    }

    public void setFreeTruckParkingPlacesAmount(int freeTruckParkingPlacesAmount) {
        this.freeTruckParkingPlacesAmount = freeTruckParkingPlacesAmount;
    }

    public int getAllFreeParkingPlacesAmount() {
        return allFreeParkingPlacesAmount;
    }

    public void setAllFreeParkingPlacesAmount(int allFreeParkingPlacesAmount) {
        this.allFreeParkingPlacesAmount = allFreeParkingPlacesAmount;
    }

    public int getCarAmount() {
        return carAmount;
    }

    public void setCarAmount(int carAmount) {
        this.carAmount = carAmount;
    }

    public int getTruckAmount() {
        return truckAmount;
    }

    public void setTruckAmount(int truckAmount) {
        this.truckAmount = truckAmount;
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
     * @param transport transport object that should be parked
     * @return Transport if transport was parked successfully, null if parking is full, throws Exception in other cases
     * @throws ParkingException custom exception
     */
        public Transport park(Transport transport) throws ParkingException{
        synchronized (transport){
            if (allFreeParkingPlacesAmount == 0){
                log.debug("Parking is full. Can not park " + transport.getType());
                return null;
            }else if(transport.getType().equals(TransportType.Car)){
                if(freeCarParkingPlacesAmount > 0){
                    freeCarParkingPlacesAmount--;
                    carAmount++;
                    log.info("Some car was parked");
                    updatePlaces();
                    return transport;
                }else{
                    log.debug("Car parking is full. Can't park some car");
                    return null;
                }
            }else if (transport.getType().equals(TransportType.Truck)){
                if(freeTruckParkingPlacesAmount > 0){
                    freeTruckParkingPlacesAmount--;
                    truckAmount++;
                    log.info("Some truck was parked on trucks parking place");
                    updatePlaces();
                    return transport;
                }else if(freeCarParkingPlacesAmount > 1){
                    freeCarParkingPlacesAmount = freeCarParkingPlacesAmount - 2;
                    truckAmount++;
                    log.info("Some truck was parked on cars parking place");
                    updatePlaces();
                    return transport;
                }else{
                    log.debug("Parking is full. Can not park truck");
                    return null;
                }
            }else{
              log.error("Exception while parking " + transport.getType());
              throw new ParkingException("Exception while parking " + transport.getType());
            }
        }
    }

    /**
     * @param transport transport object that should be 'unparked'
     * @return Transport if was successfully "unparked", null if transport wasn't found, throws exception in other cases
     * @throws ParkingException custom exception class
     */
    public Transport unPark(Transport transport) throws ParkingException {
        synchronized (transport) {
            if (transport.getType().equals(TransportType.Car)) {
                if (carAmount > 0) {
                    freeCarParkingPlacesAmount++;
                    carAmount--;
                    log.info("Some car was unparked");
                    updatePlaces();
                    return transport;
                } else {
                    log.debug("No cars on parking found while trying to unpark some");
                    return null;
                }
            } else {
                if (truckAmount > 0) {
                    if (freeTruckParkingPlacesAmount < maxParkingTruckPlaces) {
                        freeTruckParkingPlacesAmount++;
                        truckAmount--;
                        log.info("Some truck was unparked from truck parking");
                        updatePlaces();
                        return transport;
                    } else if (maxParkingCarPlaces - freeCarParkingPlacesAmount > 1) {
                        freeCarParkingPlacesAmount = freeCarParkingPlacesAmount + 2;
                        truckAmount--;
                        log.info("Some truck was unparked from car parking");
                        updatePlaces();
                        return transport;
                    } else {
                        log.error("Unexpected exception while trying to unpark truck");
                        throw new ParkingException("Unexpected exception while trying to unpark truck");
                    }
                } else {
                    log.debug("No trucks on parking found while trying to unpark some");
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
        log.info("freeCarParkingPlacesAmount : " + freeCarParkingPlacesAmount);
        log.info("freeTruckParkingPlacesAmount : " + freeTruckParkingPlacesAmount);
        log.info("allFreeParkingPlacesAmount : " + allFreeParkingPlacesAmount);
        log.info("Cars on parking : " + carAmount);
        log.info("Trucks on parking : " + truckAmount);
        log.info("---------------------------------------");


    }
}
