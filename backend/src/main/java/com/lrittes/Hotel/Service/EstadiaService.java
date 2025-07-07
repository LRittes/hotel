package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Estadia;
import com.lrittes.Hotel.Repository.EstadiaRepository;
import com.lrittes.Hotel.dto.EstadiaDTO;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadiaService {

    @Autowired
    private EstadiaRepository estadiaRepository;

    public List<EstadiaDTO> findAll() {
        return estadiaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EstadiaDTO> findById(Long id) {
        return estadiaRepository.findByEid(id)
                .map(this::convertToDTO);
    }

    public EstadiaDTO save(EstadiaDTO estadiaDTO) {
        Estadia estadia = convertToEntity(estadiaDTO);
        estadia = estadiaRepository.save(estadia);
        return convertToDTO(estadia);
    }

    public EstadiaDTO update(Long id, EstadiaDTO estadiaDTO) {
        return estadiaRepository.findByEid(id).map(existingEstadia -> {
            existingEstadia.setDataCheckin(estadiaDTO.getDataCheckin());
            existingEstadia.setDataCheckout(estadiaDTO.getDataCheckout());
            existingEstadia.setClienteId(estadiaDTO.getClienteId());
            existingEstadia.setQuartoId(estadiaDTO.getQuartoId());
            existingEstadia.setReservaId(estadiaDTO.getReservaId());



            return convertToDTO(estadiaRepository.save(existingEstadia));
        }).orElseThrow(() -> new RuntimeException("Estadia não encontrada com ID: " + id));
    }

    public void deleteById(Long id) {
        estadiaRepository.deleteByEid(id);
    }

    private EstadiaDTO convertToDTO(Estadia estadia) {
        return new EstadiaDTO(
                estadia.getId(),
                estadia.getEid(),
                estadia.getDataCheckin(),
                estadia.getDataCheckout(),
                estadia.getClienteId(),
                estadia.getQuartoId(),
                estadia.getReservaId()
        );
    }

    private Estadia convertToEntity(EstadiaDTO estadiaDTO) {
        Estadia estadia = new Estadia();
        estadia.setId(estadiaDTO.getId());
        estadia.setDataCheckin(estadiaDTO.getDataCheckin());
        estadia.setDataCheckout(estadiaDTO.getDataCheckout());
        estadia.setClienteId(estadiaDTO.getClienteId());
        estadia.setQuartoId(estadiaDTO.getQuartoId());
        estadia.setReservaId(estadiaDTO.getReservaId());


        return estadia;
    }
}
