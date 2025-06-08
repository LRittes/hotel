package com.lrittes.Hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lrittes.Hotel.Service.TipoQuartoService;
import com.lrittes.Hotel.dto.TipoQuartoDTO;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/api/tipos-quarto")
public class TipoQuartoController {

    @Autowired
    private TipoQuartoService tipoQuartoService;

    @GetMapping
    public ResponseEntity<List<TipoQuartoDTO>> getAllTiposQuarto() {
        List<TipoQuartoDTO> tiposQuarto = tipoQuartoService.findAll();
        return ResponseEntity.ok(tiposQuarto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoQuartoDTO> getTipoQuartoById(@PathVariable Long id) {
        return tipoQuartoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoQuartoDTO> createTipoQuarto(@RequestBody TipoQuartoDTO tipoQuartoDTO) {
        try {
            TipoQuartoDTO savedTipoQuarto = tipoQuartoService.save(tipoQuartoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTipoQuarto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Exemplo de tratamento de erro para ID de Hotel inválido
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoQuartoDTO> updateTipoQuarto(@PathVariable Long id, @RequestBody TipoQuartoDTO tipoQuartoDTO) {
        try {
            TipoQuartoDTO updatedTipoQuarto = tipoQuartoService.update(id, tipoQuartoDTO);
            return ResponseEntity.ok(updatedTipoQuarto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Ou badRequest se for ID de Hotel/TipoQuarto inválido
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoQuarto(@PathVariable Long id) {
        tipoQuartoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
