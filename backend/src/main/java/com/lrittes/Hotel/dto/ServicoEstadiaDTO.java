package com.lrittes.Hotel.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoEstadiaDTO {
    private Long id;
    private Long estadiaId;
    private Long servicoExtraId;
    private Timestamp dataHora;
    private Integer quantidade;
    private String descricao;
}
