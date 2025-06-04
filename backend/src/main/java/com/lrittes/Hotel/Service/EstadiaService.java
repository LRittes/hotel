package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Cliente;
import com.lrittes.Hotel.Model.Estadia;
import com.lrittes.Hotel.Model.Quarto;
import com.lrittes.Hotel.Model.Reserva;
import com.lrittes.Hotel.Repository.ClienteRepository;
import com.lrittes.Hotel.Repository.EstadiaRepository;
import com.lrittes.Hotel.Repository.QuartoRepository;
import com.lrittes.Hotel.Repository.ReservaRepository;
import com.lrittes.Hotel.dto.EstadiaDTO;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstadiaService {

    @Autowired
    private EstadiaRepository estadiaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public List<EstadiaDTO> findAll() {
        return estadiaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EstadiaDTO> findById(Long id) {
        return estadiaRepository.findById(id)
                .map(this::convertToDTO);
    }

    public EstadiaDTO save(EstadiaDTO estadiaDTO) {
        Estadia estadia = convertToEntity(estadiaDTO);
        estadia = estadiaRepository.save(estadia);
        return convertToDTO(estadia);
    }

    public EstadiaDTO update(Long id, EstadiaDTO estadiaDTO) {
        return estadiaRepository.findById(id).map(existingEstadia -> {
            existingEstadia.setDataCheckin(estadiaDTO.getDataCheckin());
            existingEstadia.setDataCheckout(estadiaDTO.getDataCheckout());

            clienteRepository.findById(estadiaDTO.getClienteId()).ifPresentOrElse(
                existingEstadia::setCliente,
                () -> { throw new RuntimeException("Cliente não encontrado com ID: " + estadiaDTO.getClienteId()); }
            );

            quartoRepository.findById(estadiaDTO.getQuartoId()).ifPresentOrElse(
                existingEstadia::setQuarto,
                () -> { throw new RuntimeException("Quarto não encontrado com ID: " + estadiaDTO.getQuartoId()); }
            );

            if (estadiaDTO.getReservaId() != null) {
                reservaRepository.findById(estadiaDTO.getReservaId()).ifPresentOrElse(
                    existingEstadia::setReserva,
                    () -> { throw new RuntimeException("Reserva não encontrada com ID: " + estadiaDTO.getReservaId()); }
                );
            } else {
                existingEstadia.setReserva(null);
            }

            return convertToDTO(estadiaRepository.save(existingEstadia));
        }).orElseThrow(() -> new RuntimeException("Estadia não encontrada com ID: " + id));
    }

    public void deleteById(Long id) {
        estadiaRepository.deleteById(id);
    }

    private EstadiaDTO convertToDTO(Estadia estadia) {
        return new EstadiaDTO(
                estadia.getId(),
                estadia.getDataCheckin(),
                estadia.getDataCheckout(),
                estadia.getCliente().getId(),
                estadia.getQuarto().getId(),
                estadia.getReserva() != null ? estadia.getReserva().getId() : null
        );
    }

    private Estadia convertToEntity(EstadiaDTO estadiaDTO) {
        Estadia estadia = new Estadia();
        estadia.setId(estadiaDTO.getId());
        estadia.setDataCheckin(estadiaDTO.getDataCheckin());
        estadia.setDataCheckout(estadiaDTO.getDataCheckout());

        Cliente cliente = clienteRepository.findById(estadiaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + estadiaDTO.getClienteId()));
        estadia.setCliente(cliente);

        Quarto quarto = quartoRepository.findById(estadiaDTO.getQuartoId())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado com ID: " + estadiaDTO.getQuartoId()));
        estadia.setQuarto(quarto);

        if (estadiaDTO.getReservaId() != null) {
            Reserva reserva = reservaRepository.findById(estadiaDTO.getReservaId())
                    .orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + estadiaDTO.getReservaId()));
            estadia.setReserva(reserva);
        }

        return estadia;
    }
}
