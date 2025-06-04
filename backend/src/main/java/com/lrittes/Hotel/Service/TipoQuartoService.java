package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Hotel;
import com.lrittes.Hotel.Model.TipoQuarto;
import com.lrittes.Hotel.Repository.HotelRepository;
import com.lrittes.Hotel.Repository.TipoQuartoRepository;
import com.lrittes.Hotel.dto.TipoQuartoDTO;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TipoQuartoService {

    @Autowired
    private TipoQuartoRepository tipoQuartoRepository;

    @Autowired
    private HotelRepository hotelRepository; // Necessário para buscar o Hotel

    public List<TipoQuartoDTO> findAll() {
        return tipoQuartoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TipoQuartoDTO> findById(Long id) {
        return tipoQuartoRepository.findById(id)
                .map(this::convertToDTO);
    }

    public TipoQuartoDTO save(TipoQuartoDTO tipoQuartoDTO) {
        TipoQuarto tipoQuarto = convertToEntity(tipoQuartoDTO);
        tipoQuarto = tipoQuartoRepository.save(tipoQuarto);
        return convertToDTO(tipoQuarto);
    }

    public TipoQuartoDTO update(Long id, TipoQuartoDTO tipoQuartoDTO) {
        return tipoQuartoRepository.findById(id).map(existingTipoQuarto -> {
            existingTipoQuarto.setPlano(tipoQuartoDTO.getPlano());
            existingTipoQuarto.setTipoQuarto(tipoQuartoDTO.getTipoQuarto());
            existingTipoQuarto.setPrecoNoite(tipoQuartoDTO.getPrecoNoite());

            hotelRepository.findById(tipoQuartoDTO.getHotelId()).ifPresentOrElse(
                existingTipoQuarto::setHotel,
                () -> { throw new RuntimeException("Hotel não encontrado com ID: " + tipoQuartoDTO.getHotelId()); }
            );

            return convertToDTO(tipoQuartoRepository.save(existingTipoQuarto));
        }).orElseThrow(() -> new RuntimeException("TipoQuarto não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        tipoQuartoRepository.deleteById(id);
    }

    private TipoQuartoDTO convertToDTO(TipoQuarto tipoQuarto) {
        return new TipoQuartoDTO(
                tipoQuarto.getId(),
                tipoQuarto.getHotel().getId(), // Pega apenas o ID do Hotel
                tipoQuarto.getPlano(),
                tipoQuarto.getTipoQuarto(),
                tipoQuarto.getPrecoNoite()
        );
    }

    private TipoQuarto convertToEntity(TipoQuartoDTO tipoQuartoDTO) {
        TipoQuarto tipoQuarto = new TipoQuarto();
        tipoQuarto.setId(tipoQuartoDTO.getId());

        // Busca a entidade Hotel a partir do ID
        Hotel hotel = hotelRepository.findById(tipoQuartoDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado com ID: " + tipoQuartoDTO.getHotelId()));
        tipoQuarto.setHotel(hotel);

        tipoQuarto.setPlano(tipoQuartoDTO.getPlano());
        tipoQuarto.setTipoQuarto(tipoQuartoDTO.getTipoQuarto());
        tipoQuarto.setPrecoNoite(tipoQuartoDTO.getPrecoNoite());
        return tipoQuarto;
    }
}
