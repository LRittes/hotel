package com.lrittes.Hotel.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.lrittes.Hotel.Model.Reserva;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long id;
    private LocalDate dataReserva;
    private LocalDate dataCheckinPrevista;
    private LocalDate dataCheckoutPrevisto;
    private Long tipoQuartoId;
    private Long quartoId;
    private Long hotelId;
    private Boolean camaExtra;
    private Long clienteId;
    private BigDecimal valor;
    private Reserva.StatusReserva status;
}
