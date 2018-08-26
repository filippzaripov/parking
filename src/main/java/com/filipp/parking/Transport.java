package com.filipp.parking;

public class Transport {
    TransportType type;
    int places;

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public Transport(TransportType type) {
        this.type = type;
        if (type.equals(TransportType.Car)) {
            this.places = 1;
        } else {
            this.places = 2;
        }
    }

    @Override
    public String toString() {
        return "Transport type : " + this.getType();
    }
}
