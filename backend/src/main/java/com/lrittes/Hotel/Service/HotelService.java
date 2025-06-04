package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Hotel;
import com.lrittes.Hotel.Repository.HotelRepository;
import com.lrittes.Hotel.dto.HotelDTO;
import com.lrittes.Hotel.exception.hotel.RangeOutRateException;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<HotelDTO> findAll() {
        return hotelRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<HotelDTO> findById(Long id) {
        return hotelRepository.findById(id)
                .map(this::convertToDTO);
    }

    public HotelDTO save(HotelDTO hotelDTO) {
        try {
            Hotel hotel = convertToEntity(hotelDTO);
            hotel = hotelRepository.save(hotel);
            return convertToDTO(hotel);    
        } catch (ConstraintViolationException e) {
            throw new RangeOutRateException(e.getMessage());
        } catch (Exception ex) {
            // Captura outras exceções inesperadas durante o salvamento
            throw new RuntimeException("Ocorreu um erro inesperado ao salvar o hotel.", ex);
        }
        
    }

    public HotelDTO update(Long id, HotelDTO hotelDTO) {
        return hotelRepository.findById(id).map(existingHotel -> {
            existingHotel.setNome(hotelDTO.getNome());
            existingHotel.setEndereco(hotelDTO.getEndereco());
            existingHotel.setTelefone(hotelDTO.getTelefone());
            return convertToDTO(hotelRepository.save(existingHotel));
        }).orElseThrow(() -> new RuntimeException("Hotel não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }

    private HotelDTO convertToDTO(Hotel hotel) {
        return new HotelDTO(hotel.getId(), hotel.getNome(), hotel.getEndereco(), hotel.getTelefone(), hotel.getNota());
    }

    private Hotel convertToEntity(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelDTO.getId()); // ID pode ser nulo para novas entidades
        hotel.setNome(hotelDTO.getNome());
        hotel.setEndereco(hotelDTO.getEndereco());
        hotel.setTelefone(hotelDTO.getTelefone());
        hotel.setNota(hotelDTO.getNota());
        return hotel;
    }
}
