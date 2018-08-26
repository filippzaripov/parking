package com.filipp.parking;

/**
 * Created by Филипп on 26.08.2018.
 */
public class ParkingException extends Exception {

    private String detail;

    public ParkingException(String detail){
        this.detail = detail;
    }
    @Override
    public String toString() {
        return "UnexpectedParkingException : [ " + detail + " ]" ;
    }
}
