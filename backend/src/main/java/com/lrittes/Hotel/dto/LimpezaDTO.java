package com.lrittes.Hotel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimpezaDTO {
    private String id;
    private Long lid;
    private Long empregadoId;
    private Long quartoId;
    private LocalDate data;
}
