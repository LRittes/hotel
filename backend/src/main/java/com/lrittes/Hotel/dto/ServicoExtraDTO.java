package com.lrittes.Hotel.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoExtraDTO {
    private String id;
    private Long seid;
    private String descricao;
    private BigDecimal preco;
}
