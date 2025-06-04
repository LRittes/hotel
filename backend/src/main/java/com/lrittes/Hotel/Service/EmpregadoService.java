package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Empregado;
import com.lrittes.Hotel.Repository.EmpregadoRepository;
import com.lrittes.Hotel.dto.EmpregadoDTO;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmpregadoService {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    public List<EmpregadoDTO> findAll() {
        return empregadoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EmpregadoDTO> findById(Long id) {
        return empregadoRepository.findById(id)
                .map(this::convertToDTO);
    }

    public EmpregadoDTO save(EmpregadoDTO empregadoDTO) {
        Empregado empregado = convertToEntity(empregadoDTO);
        empregado = empregadoRepository.save(empregado);
        return convertToDTO(empregado);
    }

    public EmpregadoDTO update(Long id, EmpregadoDTO empregadoDTO) {
        return empregadoRepository.findById(id).map(existingEmpregado -> {
            existingEmpregado.setNome(empregadoDTO.getNome());
            return convertToDTO(empregadoRepository.save(existingEmpregado));
        }).orElseThrow(() -> new RuntimeException("Empregado não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        empregadoRepository.deleteById(id);
    }

    private EmpregadoDTO convertToDTO(Empregado empregado) {
        return new EmpregadoDTO(empregado.getCpf(), empregado.getNome(), empregado.getEndereco(), empregado.getTelefone());
    }

    private Empregado convertToEntity(EmpregadoDTO empregadoDTO) {
        Empregado empregado = new Empregado();
        empregado.setNome(empregadoDTO.getNome());
        empregado.setCpf(empregadoDTO.getCpf());
        empregado.setNome(empregadoDTO.getNome());
        empregado.setEndereco(empregadoDTO.getEndereco());
        empregado.setTelefone(empregadoDTO.getTelefone());
        return empregado;
    }
}
