package com.lrittes.Hotel.exception.cliente;


public class ClienteNotFoundException extends RuntimeException {
    
    public ClienteNotFoundException(String msg){
        super(msg);
    }
}
