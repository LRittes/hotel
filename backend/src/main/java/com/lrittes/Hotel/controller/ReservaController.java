package com.lrittes.Hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lrittes.Hotel.Service.ReservaService;
import com.lrittes.Hotel.dto.ReservaDTO;
import com.lrittes.Hotel.exception.reserva.DataCheckinBeforeCheckoutException;
import com.lrittes.Hotel.exception.reserva.SameDataReservaException;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getAllReservas() {
        List<ReservaDTO> reservas = reservaService.findAll();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReservaById(@PathVariable Long id) {
        return reservaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            ReservaDTO savedReserva = reservaService.save(reservaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReserva);
        } catch (SameDataReservaException e) {
            throw new SameDataReservaException(e.getMessage());
            
        } catch (DataCheckinBeforeCheckoutException e) {
            throw new DataCheckinBeforeCheckoutException(e.getMessage());
        } 
        catch (RuntimeException e) {
            // Captura outras exceções inesperadas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserva(@PathVariable Long id, @RequestBody ReservaDTO reservaDTO) {
        try {
            ReservaDTO updatedReserva = reservaService.update(id, reservaDTO);
            return ResponseEntity.ok(updatedReserva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
