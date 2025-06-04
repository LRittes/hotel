package com.lrittes.Hotel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpregadoDTO {
    //private Long id;
    private String cpf;
    private String nome;
    private String endereco;
    private String telefone;
}
