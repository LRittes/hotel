package com.lrittes.Hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lrittes.Hotel.Service.EmpregadoService;
import com.lrittes.Hotel.dto.EmpregadoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/empregados")
public class EmpregadoController {

    @Autowired
    private EmpregadoService empregadoService;

    @GetMapping
    public ResponseEntity<List<EmpregadoDTO>> getAllEmpregados() {
        List<EmpregadoDTO> empregados = empregadoService.findAll();
        return ResponseEntity.ok(empregados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpregadoDTO> getEmpregadoById(@PathVariable Long id) {
        return empregadoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmpregadoDTO> createEmpregado(@RequestBody EmpregadoDTO empregadoDTO) {
        EmpregadoDTO savedEmpregado = empregadoService.save(empregadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpregado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpregadoDTO> updateEmpregado(@PathVariable Long id, @RequestBody EmpregadoDTO empregadoDTO) {
        try {
            EmpregadoDTO updatedEmpregado = empregadoService.update(id, empregadoDTO);
            return ResponseEntity.ok(updatedEmpregado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpregado(@PathVariable Long id) {
        empregadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
