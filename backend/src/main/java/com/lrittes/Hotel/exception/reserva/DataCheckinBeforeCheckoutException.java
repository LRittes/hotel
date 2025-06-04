package com.lrittes.Hotel.exception.reserva;

public class DataCheckinBeforeCheckoutException extends RuntimeException {
     public DataCheckinBeforeCheckoutException(String msg){
        super(msg);
    }
}
