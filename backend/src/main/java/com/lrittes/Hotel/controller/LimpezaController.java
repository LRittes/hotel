package com.lrittes.Hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lrittes.Hotel.Service.LimpezaService;
import com.lrittes.Hotel.dto.LimpezaDTO;

import java.util.List;

@RestController
@RequestMapping("/api/limpezas")
public class LimpezaController {

    @Autowired
    private LimpezaService limpezaService;

    @GetMapping
    public ResponseEntity<List<LimpezaDTO>> getAllLimpezas() {
        List<LimpezaDTO> limpezas = limpezaService.findAll();
        return ResponseEntity.ok(limpezas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LimpezaDTO> getLimpezaById(@PathVariable Long id) {
        return limpezaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LimpezaDTO> createLimpeza(@RequestBody LimpezaDTO limpezaDTO) {
        try {
            LimpezaDTO savedLimpeza = limpezaService.save(limpezaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLimpeza);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LimpezaDTO> updateLimpeza(@PathVariable Long id, @RequestBody LimpezaDTO limpezaDTO) {
        try {
            LimpezaDTO updatedLimpeza = limpezaService.update(id, limpezaDTO);
            return ResponseEntity.ok(updatedLimpeza);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLimpeza(@PathVariable Long id) {
        limpezaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
