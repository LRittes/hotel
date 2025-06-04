package com.lrittes.Hotel.exception;


import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lrittes.Hotel.exception.cliente.ClienteNotFoundException;
import com.lrittes.Hotel.exception.estadia.InsertEstadiaException;
import com.lrittes.Hotel.exception.hotel.RangeOutRateException;
import com.lrittes.Hotel.exception.reserva.DataCheckinBeforeCheckoutException;
import com.lrittes.Hotel.exception.reserva.SameDataReservaException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFound(ClienteNotFoundException exc){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Cliente não encontrado!");
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsertEstadiaException.class)
    public ResponseEntity<Object> handleInsertEstadia(InsertEstadiaException exc){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Erro ao inserir Estadia!");
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameDataReservaException.class)
    public ResponseEntity<Object> handleSameDataReservaException(SameDataReservaException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        String errorMSG = exc.getMessage(); 
        int idx = errorMSG.indexOf('\n');
        if (idx != -1) {
            errorMSG = errorMSG.substring(0, idx);
        }
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflito de Reserva");
        body.put("message", errorMSG);
        
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataCheckinBeforeCheckoutException.class)
    public ResponseEntity<Object> DataCheckinBeforeCheckoutException(DataCheckinBeforeCheckoutException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        String errorMSG = exc.getMessage(); 
        int idx = errorMSG.indexOf('\n');
        if (idx != -1) {
            errorMSG = errorMSG.substring(0, idx);
        }
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Conflito de Reserva");
        body.put("message", errorMSG);
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RangeOutRateException.class)
    public ResponseEntity<Object> RangeOutException(RangeOutRateException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_ACCEPTABLE.value());
        body.put("error", "Fora do Range");
        body.put("message", "Nota deve estar entre 0 e 10");
        
        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }
    


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenerictException(Exception exc){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Erro interno do servidor!");
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

