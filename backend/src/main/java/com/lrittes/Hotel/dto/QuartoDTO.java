package com.lrittes.Hotel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuartoDTO {
    private Long id;
    private Integer numero;
    private Long hotelId; // Apenas o ID do hotel
    private Integer andar;
    private Long tipoQuartoId; // Apenas o ID do tipo de quarto
}
