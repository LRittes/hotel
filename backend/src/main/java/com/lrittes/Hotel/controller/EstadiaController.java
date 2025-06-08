package com.lrittes.Hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lrittes.Hotel.Service.EstadiaService;
import com.lrittes.Hotel.dto.EstadiaDTO;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/api/estadias")
public class EstadiaController {

    @Autowired
    private EstadiaService estadiaService;

    @GetMapping
    public ResponseEntity<List<EstadiaDTO>> getAllEstadias() {
        List<EstadiaDTO> estadias = estadiaService.findAll();
        return ResponseEntity.ok(estadias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadiaDTO> getEstadiaById(@PathVariable Long id) {
        return estadiaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstadiaDTO> createEstadia(@RequestBody EstadiaDTO estadiaDTO) {
        try {
            EstadiaDTO savedEstadia = estadiaService.save(estadiaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEstadia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadiaDTO> updateEstadia(@PathVariable Long id, @RequestBody EstadiaDTO estadiaDTO) {
        try {
            EstadiaDTO updatedEstadia = estadiaService.update(id, estadiaDTO);
            return ResponseEntity.ok(updatedEstadia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadia(@PathVariable Long id) {
        estadiaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
