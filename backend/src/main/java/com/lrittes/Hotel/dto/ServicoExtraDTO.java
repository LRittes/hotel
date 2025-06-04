package com.lrittes.Hotel.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoExtraDTO {
    private Long id;
    private String descricao;
    private BigDecimal preco;
}
