package com.lrittes.Hotel.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import com.lrittes.Hotel.Model.TipoQuarto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoQuartoDTO {
    private Long id;
    private Long hotelId; 
    private TipoQuarto.Plano plano;
    private TipoQuarto.TipoEnum tipoQuarto;
    private BigDecimal precoNoite;
}
