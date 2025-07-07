package com.lrittes.Hotel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private String id;
    private Long clienteId;
    private String cpf;
    private String nome;
    private String email;
    private String password;
    private String endereco;
    private String telefone;
}
