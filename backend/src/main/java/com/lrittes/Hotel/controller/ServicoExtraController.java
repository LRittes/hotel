package com.lrittes.Hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lrittes.Hotel.Service.ServicoExtraService;
import com.lrittes.Hotel.dto.ServicoExtraDTO;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/api/servicos-extra")
public class ServicoExtraController {

    @Autowired
    private ServicoExtraService servicoExtraService;

    @GetMapping
    public ResponseEntity<List<ServicoExtraDTO>> getAllServicosExtra() {
        List<ServicoExtraDTO> servicosExtra = servicoExtraService.findAll();
        return ResponseEntity.ok(servicosExtra);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoExtraDTO> getServicoExtraById(@PathVariable Long id) {
        return servicoExtraService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServicoExtraDTO> createServicoExtra(@RequestBody ServicoExtraDTO servicoExtraDTO) {
        ServicoExtraDTO savedServicoExtra = servicoExtraService.save(servicoExtraDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedServicoExtra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoExtraDTO> updateServicoExtra(@PathVariable Long id, @RequestBody ServicoExtraDTO servicoExtraDTO) {
        try {
            ServicoExtraDTO updatedServicoExtra = servicoExtraService.update(id, servicoExtraDTO);
            return ResponseEntity.ok(updatedServicoExtra);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoExtra(@PathVariable Long id) {
        servicoExtraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
