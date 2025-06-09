package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Cliente;
import com.lrittes.Hotel.Model.Hotel;
import com.lrittes.Hotel.Model.Quarto;
import com.lrittes.Hotel.Model.Reserva;
import com.lrittes.Hotel.Model.TipoQuarto;
import com.lrittes.Hotel.Repository.ClienteRepository;
import com.lrittes.Hotel.Repository.HotelRepository;
import com.lrittes.Hotel.Repository.QuartoRepository;
import com.lrittes.Hotel.Repository.ReservaRepository;
import com.lrittes.Hotel.Repository.TipoQuartoRepository;
import com.lrittes.Hotel.dto.ReservaDTO;
import com.lrittes.Hotel.exception.reserva.DataCheckinBeforeCheckoutException;
import com.lrittes.Hotel.exception.reserva.SameDataReservaException;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private TipoQuartoRepository tipoQuartoRepository;

    public List<ReservaDTO> findAll() {
        return reservaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReservaDTO> findById(Long id) {
        return reservaRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<ReservaDTO> getReservasByClienteId(Long id) {
        return reservaRepository.getReservasByClienteId(id).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    public ReservaDTO save(ReservaDTO reservaDTO) {
        Reserva reserva = convertToEntity(reservaDTO);
        try {
            reserva = reservaRepository.save(reserva);
            return convertToDTO(reserva);
        } catch (DataAccessException ex) { // Captura exceções de acesso a dados (mais abrangente)
            // A PSQLException original do PostgreSQL estará aninhada como a causa raiz
            Throwable rootCause = ex.getRootCause(); // Pega a causa raiz da cadeia de exceções

            if (rootCause != null && rootCause.getMessage().contains("O quarto") && rootCause.getMessage().contains("não está disponível")) {
                // É a nossa exceção customizada do PostgreSQL
                throw new SameDataReservaException(rootCause.getMessage());
            } else if (rootCause != null && rootCause.getMessage().contains("A data de check-out") && rootCause.getMessage().contains("deve ser posterior à data de check-in")){
                throw new DataCheckinBeforeCheckoutException(rootCause.getMessage());
            } 
            else if (ex instanceof DataIntegrityViolationException) {
                // Captura outras DataIntegrityViolationException se elas não tiverem a mensagem específica
                // Você pode personalizar esta parte se tiver outras validações de integridade
                throw new IllegalArgumentException("Erro de integridade de dados ao salvar a reserva.", ex);
            } else {
                // Se for outra DataAccessException que não identificamos, relançar como erro genérico
                throw new RuntimeException("Ocorreu um erro de acesso a dados inesperado ao salvar a reserva.", ex);
            }
        } catch (Exception ex) {
            // Captura outras exceções inesperadas durante o salvamento
            throw new RuntimeException("Ocorreu um erro inesperado ao salvar a reserva.", ex);
        }
    }

    public ReservaDTO update(Long id, ReservaDTO reservaDTO) {
        return reservaRepository.findById(id).map(existingReserva -> {
            existingReserva.setDataReserva(reservaDTO.getDataReserva());
            existingReserva.setDataCheckinPrevista(reservaDTO.getDataCheckinPrevista());
            existingReserva.setDataCheckoutPrevisto(reservaDTO.getDataCheckoutPrevisto());
            existingReserva.setCamaExtra(reservaDTO.getCamaExtra());
            existingReserva.setValor(reservaDTO.getValor());
            existingReserva.setStatus(reservaDTO.getStatus());

            clienteRepository.findById(reservaDTO.getClienteId()).ifPresentOrElse(
                existingReserva::setCliente,
                () -> { throw new RuntimeException("Cliente não encontrado com ID: " + reservaDTO.getClienteId()); }
            );

            quartoRepository.findById(reservaDTO.getQuartoId()).ifPresentOrElse(
                existingReserva::setQuarto,
                () -> { throw new RuntimeException("Quarto não encontrado com ID: " + reservaDTO.getQuartoId()); }
            );

            hotelRepository.findById(reservaDTO.getHotelId()).ifPresentOrElse(
                existingReserva::setHotel,
                () -> { throw new RuntimeException("Hotel não encontrado com ID: " + reservaDTO.getHotelId()); }
            );

            tipoQuartoRepository.findById(reservaDTO.getTipoQuartoId()).ifPresentOrElse(
                existingReserva::setTipoQuarto,
                () -> { throw new RuntimeException("Tipo de Quarto não encontrado com ID: " + reservaDTO.getTipoQuartoId()); }
            );

            return convertToDTO(reservaRepository.save(existingReserva));
        }).orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + id));
    }

    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }

    private ReservaDTO convertToDTO(Reserva reserva) {
        return new ReservaDTO(
                reserva.getId(),
                reserva.getDataReserva(),
                reserva.getDataCheckinPrevista(),
                reserva.getDataCheckoutPrevisto(),
                reserva.getTipoQuarto().getId(),
                reserva.getQuarto().getId(),
                reserva.getHotel().getId(),
                reserva.getCamaExtra(),
                reserva.getCliente().getId(),
                reserva.getValor(),
                reserva.getValor_servicos_extra(),
                reserva.getStatus()
        );
    }

    private Reserva convertToEntity(ReservaDTO reservaDTO) {
        Reserva reserva = new Reserva();
        reserva.setId(reservaDTO.getId());
        reserva.setDataReserva(reservaDTO.getDataReserva());
        reserva.setDataCheckinPrevista(reservaDTO.getDataCheckinPrevista());
        reserva.setDataCheckoutPrevisto(reservaDTO.getDataCheckoutPrevisto());
        reserva.setCamaExtra(reservaDTO.getCamaExtra());
        reserva.setValor(reservaDTO.getValor());
        reserva.setValor_servicos_extra(reservaDTO.getValor_servicos_extra());
        reserva.setStatus(reservaDTO.getStatus());

        Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + reservaDTO.getClienteId()));
        reserva.setCliente(cliente);

        Quarto quarto = quartoRepository.findById(reservaDTO.getQuartoId())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado com ID: " + reservaDTO.getQuartoId()));
        reserva.setQuarto(quarto);

        Hotel hotel = hotelRepository.findById(reservaDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado com ID: " + reservaDTO.getHotelId()));
        reserva.setHotel(hotel);
        
        TipoQuarto tipoQuarto = tipoQuartoRepository.findById(reservaDTO.getTipoQuartoId())
                .orElseThrow(() -> new RuntimeException("Tipo de Quarto não encontrado com ID: " + reservaDTO.getTipoQuartoId()));
        reserva.setTipoQuarto(tipoQuarto);

        return reserva;
    }
}
