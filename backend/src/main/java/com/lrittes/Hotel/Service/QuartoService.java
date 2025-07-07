package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Quarto;
import com.lrittes.Hotel.Repository.QuartoRepository;
import com.lrittes.Hotel.dto.QuartoDTO;
import com.lrittes.Hotel.exception.cliente.ResourceConflictException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    public List<QuartoDTO> findAll() {
        return quartoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<QuartoDTO> findById(Long id) {
        return quartoRepository.findByQid(id)
                .map(this::convertToDTO);
    }

    public List<Map<String, Object>> findQuartoByHotelId(Long id) {
        return quartoRepository.roomByHotelId(id).stream().collect(Collectors.toList());
    }

    public QuartoDTO save(QuartoDTO quartoDTO) {
        try{
            Quarto quarto = convertToEntity(quartoDTO);
            quarto = quartoRepository.save(quarto);
            return convertToDTO(quarto);
        } catch (Exception e) {
            throw new ResourceConflictException(e.getMessage());
        }
    }

    public QuartoDTO update(Long id, QuartoDTO quartoDTO) {
        return quartoRepository.findByQid(id).map(existingQuarto -> {
            existingQuarto.setNumero(quartoDTO.getNumero());
            existingQuarto.setAndar(quartoDTO.getAndar());
            existingQuarto.setHotelId(quartoDTO.getHotelId());
            existingQuarto.setTipoQuartoId(quartoDTO.getTipoQuartoId());



            return convertToDTO(quartoRepository.save(existingQuarto));
        }).orElseThrow(() -> new RuntimeException("Quarto não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        quartoRepository.deleteByQid(id);
    }

    private QuartoDTO convertToDTO(Quarto quarto) {
        return new QuartoDTO(
                quarto.getId(),
                quarto.getQid(),
                quarto.getNumero(),
                quarto.getHotelId(),
                quarto.getAndar(),
                quarto.getTipoQuartoId()
        );
    }

    private Quarto convertToEntity(QuartoDTO quartoDTO) {
        Quarto quarto = new Quarto();
        quarto.setId(quartoDTO.getId());
        quarto.setNumero(quartoDTO.getNumero());
        quarto.setAndar(quartoDTO.getAndar());
        quarto.setHotelId(quartoDTO.getHotelId());
        quarto.setTipoQuartoId(quartoDTO.getTipoQuartoId());



        return quarto;
    }
}
