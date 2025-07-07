package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Limpeza;
import com.lrittes.Hotel.Repository.LimpezaRepository;
import com.lrittes.Hotel.dto.LimpezaDTO;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LimpezaService {

    @Autowired
    private LimpezaRepository limpezaRepository;


    public List<LimpezaDTO> findAll() {
        return limpezaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<LimpezaDTO> findById(Long id) {
        return limpezaRepository.findByLid(id)
                .map(this::convertToDTO);
    }

    public LimpezaDTO save(LimpezaDTO limpezaDTO) {
        Limpeza limpeza = convertToEntity(limpezaDTO);
        limpeza = limpezaRepository.save(limpeza);
        return convertToDTO(limpeza);
    }

    public LimpezaDTO update(Long id, LimpezaDTO limpezaDTO) {
        return limpezaRepository.findByLid(id).map(existingLimpeza -> {
            existingLimpeza.setData(limpezaDTO.getData());
            existingLimpeza.setQuartoId(limpezaDTO.getQuartoId());
            existingLimpeza.setEmpregadoId(limpezaDTO.getEmpregadoId());


            // empregadoRepository.findByEid(limpezaDTO.getEmpregadoId()).ifPresentOrElse(
            //     existingLimpeza::setEmpregado,
            //     () -> { throw new RuntimeException("Empregado não encontrado com ID: " + limpezaDTO.getEmpregadoId()); }
            // );

            // quartoRepository.findByQid(limpezaDTO.getQuartoId()).ifPresentOrElse(
            //     existingLimpeza::setQuarto,
            //     () -> { throw new RuntimeException("Quarto não encontrado com ID: " + limpezaDTO.getQuartoId()); }
            // );

            return convertToDTO(limpezaRepository.save(existingLimpeza));
        }).orElseThrow(() -> new RuntimeException("Limpeza não encontrada com ID: " + id));
    }

    public void deleteById(Long id) {
        limpezaRepository.deleteByLid(id);
    }

    private LimpezaDTO convertToDTO(Limpeza limpeza) {
        return new LimpezaDTO(
                limpeza.getId(),
                limpeza.getLid(),
                limpeza.getEmpregadoId(),
                limpeza.getQuartoId(),
                limpeza.getData()
        );
    }

    private Limpeza convertToEntity(LimpezaDTO limpezaDTO) {
        Limpeza limpeza = new Limpeza();
        limpeza.setId(limpezaDTO.getId());
        limpeza.setData(limpezaDTO.getData());
        limpeza.setQuartoId(limpezaDTO.getQuartoId());
        limpeza.setEmpregadoId(limpezaDTO.getEmpregadoId());


        // Empregado empregado = empregadoRepository.findByEid(limpezaDTO.getEmpregadoId())
        //         .orElseThrow(() -> new RuntimeException("Empregado não encontrado com ID: " + limpezaDTO.getEmpregadoId()));
        // limpeza.setEmpregado(empregado);

        // Quarto quarto = quartoRepository.findByQid(limpezaDTO.getQuartoId())
        //         .orElseThrow(() -> new RuntimeException("Quarto não encontrado com ID: " + limpezaDTO.getQuartoId()));
        // limpeza.setQuarto(quarto);

        return limpeza;
    }
}
