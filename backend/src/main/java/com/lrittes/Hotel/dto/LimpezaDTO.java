package com.lrittes.Hotel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimpezaDTO {
    private Long id;
    private Long empregadoId;
    private Long quartoId;
    private LocalDate data;
}
