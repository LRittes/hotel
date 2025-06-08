package com.lrittes.Hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lrittes.Hotel.Service.ServicoEstadiaService;
import com.lrittes.Hotel.dto.ServicoEstadiaDTO;

import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@Transactional
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/api/servicos-estadia")
public class ServicoEstadiaController {

    @Autowired
    private ServicoEstadiaService servicoEstadiaService;

    @GetMapping
    public ResponseEntity<List<ServicoEstadiaDTO>> getAllServicosEstadia() {
        List<ServicoEstadiaDTO> servicosEstadia = servicoEstadiaService.findAll();
        return ResponseEntity.ok(servicosEstadia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoEstadiaDTO> getServicoEstadiaById(@PathVariable Long id) {
        return servicoEstadiaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServicoEstadiaDTO> createServicoEstadia(@RequestBody ServicoEstadiaDTO servicoEstadiaDTO) {
        ServicoEstadiaDTO savedServicoEstadia = servicoEstadiaService.save(servicoEstadiaDTO);
        return ResponseEntity.ok(savedServicoEstadia);
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoEstadiaDTO> updateServicoEstadia(@PathVariable Long id, @RequestBody ServicoEstadiaDTO servicoEstadiaDTO) {
        try {
            ServicoEstadiaDTO updatedServicoEstadia = servicoEstadiaService.update(id, servicoEstadiaDTO);
            return ResponseEntity.ok(updatedServicoEstadia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoEstadia(@PathVariable Long id) {
        servicoEstadiaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
