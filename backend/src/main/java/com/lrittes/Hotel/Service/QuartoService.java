package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Hotel;
import com.lrittes.Hotel.Model.Quarto;
import com.lrittes.Hotel.Model.TipoQuarto;
import com.lrittes.Hotel.Repository.HotelRepository;
import com.lrittes.Hotel.Repository.QuartoRepository;
import com.lrittes.Hotel.Repository.TipoQuartoRepository;
import com.lrittes.Hotel.dto.QuartoDTO;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private TipoQuartoRepository tipoQuartoRepository;

    public List<QuartoDTO> findAll() {
        return quartoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<QuartoDTO> findById(Long id) {
        return quartoRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<Map<String, Object>> findQuartoByHotelId(Long id) {
        return quartoRepository.roomByHotelId(id).stream().collect(Collectors.toList());
    }

    public QuartoDTO save(QuartoDTO quartoDTO) {
        Quarto quarto = convertToEntity(quartoDTO);
        quarto = quartoRepository.save(quarto);
        return convertToDTO(quarto);
    }

    public QuartoDTO update(Long id, QuartoDTO quartoDTO) {
        return quartoRepository.findById(id).map(existingQuarto -> {
            existingQuarto.setNumero(quartoDTO.getNumero());
            existingQuarto.setAndar(quartoDTO.getAndar());

            hotelRepository.findById(quartoDTO.getHotelId()).ifPresentOrElse(
                existingQuarto::setHotel,
                () -> { throw new RuntimeException("Hotel não encontrado com ID: " + quartoDTO.getHotelId()); }
            );

            tipoQuartoRepository.findById(quartoDTO.getTipoQuartoId()).ifPresentOrElse(
                existingQuarto::setTipoQuarto,
                () -> { throw new RuntimeException("Tipo de Quarto não encontrado com ID: " + quartoDTO.getTipoQuartoId()); }
            );

            return convertToDTO(quartoRepository.save(existingQuarto));
        }).orElseThrow(() -> new RuntimeException("Quarto não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        quartoRepository.deleteById(id);
    }

    private QuartoDTO convertToDTO(Quarto quarto) {
        return new QuartoDTO(
                quarto.getId(),
                quarto.getNumero(),
                quarto.getHotel().getId(),
                quarto.getAndar(),
                quarto.getTipoQuarto().getId()
        );
    }

    private Quarto convertToEntity(QuartoDTO quartoDTO) {
        Quarto quarto = new Quarto();
        quarto.setId(quartoDTO.getId());
        quarto.setNumero(quartoDTO.getNumero());
        quarto.setAndar(quartoDTO.getAndar());

        Hotel hotel = hotelRepository.findById(quartoDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado com ID: " + quartoDTO.getHotelId()));
        quarto.setHotel(hotel);

        TipoQuarto tipoQuarto = tipoQuartoRepository.findById(quartoDTO.getTipoQuartoId())
                .orElseThrow(() -> new RuntimeException("Tipo de Quarto não encontrado com ID: " + quartoDTO.getTipoQuartoId()));
        quarto.setTipoQuarto(tipoQuarto);

        return quarto;
    }
}
