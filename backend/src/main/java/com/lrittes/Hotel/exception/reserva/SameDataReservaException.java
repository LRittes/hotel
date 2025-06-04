package com.lrittes.Hotel.exception.reserva;

public class SameDataReservaException extends RuntimeException {
    
    public SameDataReservaException(String msg){
        super(msg);
    }
}
