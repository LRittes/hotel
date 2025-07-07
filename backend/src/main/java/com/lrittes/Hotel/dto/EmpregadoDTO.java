package com.lrittes.Hotel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpregadoDTO {
    private String cpf;
    private Long eid;
    private String nome;
    private String endereco;
    private String telefone;
}
