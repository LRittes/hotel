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
    private Long hotelId; 
    private Integer andar;
    private Long tipoQuartoId;
}
