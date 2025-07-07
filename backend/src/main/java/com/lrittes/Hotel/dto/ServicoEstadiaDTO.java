package com.lrittes.Hotel.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoEstadiaDTO {
    private String id;
    private Long seid;
    private Long estadiaId;
    private Long servicoExtraId;
    private LocalDateTime dataHora;
    private Integer quantidade;
    private String descricao;
}
