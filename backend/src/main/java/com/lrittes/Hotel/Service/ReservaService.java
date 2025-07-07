package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Estadia;
import com.lrittes.Hotel.Model.Quarto;
import com.lrittes.Hotel.Model.Reserva;
import com.lrittes.Hotel.Model.TipoQuarto;
import com.lrittes.Hotel.Repository.EstadiaRepository;
import com.lrittes.Hotel.Repository.QuartoRepository;
import com.lrittes.Hotel.Repository.ReservaRepository;
import com.lrittes.Hotel.Repository.TipoQuartoRepository;
import com.lrittes.Hotel.dto.ReservaDTO;
import com.lrittes.Hotel.exception.reserva.DataCheckinBeforeCheckoutException;
import com.lrittes.Hotel.exception.reserva.SameDataReservaException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private QuartoRepository quartoRepository;
    
    @Autowired
    private TipoQuartoRepository tipoQuartoRepository;

    @Autowired
    private EstadiaRepository estadiaRepository;

    public List<ReservaDTO> findAll() {
        return reservaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReservaDTO> findById(Long id) {
        return reservaRepository.findByRid(id)
                .map(this::convertToDTO);
    }

    public List<ReservaDTO> getReservasByClienteId(Long id) {
        return reservaRepository.getReservasByClienteId(id).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    public void verificarDisponibilidade(Long rid, Long quartoId, LocalDate checkin, LocalDate checkout) {

         if(checkout.isBefore(checkin)){
                throw new DataCheckinBeforeCheckoutException("A data de Check-in deve ser anterior a data de Check-out");
        }

        List<String> statusesAtivos = Arrays.asList("confirmada", "pendente");

        List<Reserva> conflitos = reservaRepository.findConflictingReservations(
            quartoId,
            statusesAtivos,
            checkin,
            checkout
        );

        if (rid != null) {
            conflitos.removeIf(reserva -> reserva.getRid().equals(rid));
        }

        if (!conflitos.isEmpty()) {
            throw new SameDataReservaException("Conflito de datas: Já existe uma reserva para o período solicitado.");
        }
    }

    public BigDecimal calcularValorTotalEstadia(Reserva reserva) {
        TipoQuarto tipoQuarto = tipoQuartoRepository.findByTqid(reserva.getTipoQuartoId())
                .orElseThrow(() -> new RuntimeException("Tipo de quarto não encontrado"));

        Quarto quarto = quartoRepository.findByQid(reserva.getQuartoId())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));

        long dias = reserva.getQuantidadeDeDias();

        int andar = quarto.getAndar() - 1;

        BigDecimal valorTotal = tipoQuarto.getPrecoNoite().add(reserva.getValor_servicos_extra()).multiply(new BigDecimal(dias)).multiply(new BigDecimal(1 + andar));

        return valorTotal;
    }
        
    public ReservaDTO save(ReservaDTO reservaDTO) {
     
        Reserva reserva = convertToEntity(reservaDTO);

        verificarDisponibilidade(
            reserva.getRid(),
            reserva.getQuartoId(),
            reserva.getDataCheckinPrevista(),
            reserva.getDataCheckoutPrevisto()
        );

        reserva.setValor(calcularValorTotalEstadia(reserva));

        try {
            reserva = reservaRepository.save(reserva);

            if(reserva.getStatus().equals(Reserva.StatusReserva.confirmada)){
                estadiaRepository.save(new Estadia(null,null,reserva.getDataCheckinPrevista(),
                                reserva.getDataCheckoutPrevisto(),
                                reserva.getClienteId(),
                                reserva.getQuartoId(),
                                reserva.getRid()));
            }

            return convertToDTO(reserva);
        } catch (DataAccessException ex) {
            Throwable rootCause = ex.getRootCause();

            if (rootCause != null && rootCause.getMessage().contains("A data de check-out") && rootCause.getMessage().contains("deve ser posterior à data de check-in")){
                throw new DataCheckinBeforeCheckoutException(rootCause.getMessage());
            } 
            else if (ex instanceof DataIntegrityViolationException) {
                throw new IllegalArgumentException("Erro de integridade de dados ao salvar a reserva.", ex);
            } else {
                throw new RuntimeException("Ocorreu um erro de acesso a dados inesperado ao salvar a reserva.", ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Ocorreu um erro inesperado ao salvar a reserva.", ex);
        }
    }

    public ReservaDTO update(Long id, ReservaDTO reservaDTO) {
        return reservaRepository.findByRid(id).map(existingReserva -> {
            existingReserva.setDataReserva(reservaDTO.getDataReserva());
            existingReserva.setDataCheckinPrevista(reservaDTO.getDataCheckinPrevista());
            existingReserva.setDataCheckoutPrevisto(reservaDTO.getDataCheckoutPrevisto());
            existingReserva.setCamaExtra(reservaDTO.getCamaExtra());
            existingReserva.setValor_servicos_extra(reservaDTO.getValor_servicos_extra());
            existingReserva.setStatus(reservaDTO.getStatus());
            existingReserva.setClienteId(reservaDTO.getClienteId());
            existingReserva.setQuartoId(reservaDTO.getQuartoId());
            existingReserva.setHotelId(reservaDTO.getHotelId());
            existingReserva.setTipoQuartoId(reservaDTO.getTipoQuartoId());
            
            existingReserva.setValor(calcularValorTotalEstadia(existingReserva));

            verificarDisponibilidade(
                existingReserva.getRid(),
                existingReserva.getQuartoId(),
                existingReserva.getDataCheckinPrevista(),
                existingReserva.getDataCheckoutPrevisto()
            );

             if(existingReserva.getStatus().equals(Reserva.StatusReserva.confirmada)){
                estadiaRepository.save(new Estadia(null,null,existingReserva.getDataCheckinPrevista(),
                                existingReserva.getDataCheckoutPrevisto(),
                                existingReserva.getClienteId(),
                                existingReserva.getQuartoId(),
                                existingReserva.getRid()));
            }

            return convertToDTO(reservaRepository.save(existingReserva));
        }).orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + id));


        
    }

    public void deleteById(Long id) {
        reservaRepository.deleteByRid(id);

        if(estadiaRepository.findByReservaId(id).isPresent()){
            estadiaRepository.deleteByReservaId(id);
        }
    }

    private ReservaDTO convertToDTO(Reserva reserva) {
        return new ReservaDTO(
                reserva.getId(),
                reserva.getRid(),
                reserva.getDataReserva(),
                reserva.getDataCheckinPrevista(),
                reserva.getDataCheckoutPrevisto(),
                reserva.getTipoQuartoId(),
                reserva.getQuartoId(),
                reserva.getHotelId(),
                reserva.getCamaExtra(),
                reserva.getClienteId(),
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
        reserva.setClienteId(reservaDTO.getClienteId());
        reserva.setQuartoId(reservaDTO.getQuartoId());
        reserva.setHotelId(reservaDTO.getHotelId());
        reserva.setTipoQuartoId(reservaDTO.getTipoQuartoId());


        return reserva;
    }
}
