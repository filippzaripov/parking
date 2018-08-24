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

    public Transport(TransportType type){
        this.type = type;
    }

}
