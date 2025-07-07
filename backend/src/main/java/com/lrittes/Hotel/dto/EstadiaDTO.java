package com.lrittes.Hotel.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadiaDTO {
    private String id;
    private Long eid;
    private LocalDate dataCheckin;
    private LocalDate dataCheckout;
    private Long clienteId;
    private Long quartoId;
    private Long reservaId;
}
