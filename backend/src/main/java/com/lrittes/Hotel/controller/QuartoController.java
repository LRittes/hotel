package com.lrittes.Hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lrittes.Hotel.Service.QuartoService;
import com.lrittes.Hotel.dto.QuartoDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quartos")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @GetMapping
    public ResponseEntity<List<QuartoDTO>> getAllQuartos() {
        List<QuartoDTO> quartos = quartoService.findAll();
        return ResponseEntity.ok(quartos);
    }

    @GetMapping("/hid/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAllQuartosByHotelId(@PathVariable Long id) {
        List<Map<String, Object>> quartos = quartoService.findQuartoByHotelId(id);
        return ResponseEntity.ok(quartos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuartoDTO> getQuartoById(@PathVariable Long id) {
        return quartoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<QuartoDTO> createQuarto(@RequestBody QuartoDTO quartoDTO) {
        try {
            QuartoDTO savedQuarto = quartoService.save(quartoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuarto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Exemplo de tratamento de erro para IDs inválidos
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoDTO> updateQuarto(@PathVariable Long id, @RequestBody QuartoDTO quartoDTO) {
        try {
            QuartoDTO updatedQuarto = quartoService.update(id, quartoDTO);
            return ResponseEntity.ok(updatedQuarto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuarto(@PathVariable Long id) {
        quartoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
