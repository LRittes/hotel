package com.lrittes.Hotel.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
   @Id
    private String id; 

    @NotBlank
    @Indexed(unique = true)
    private Long clienteId;

    @NotBlank
    @Size(min = 11, max = 11)
    @Indexed(unique = true) 
    private String cpf;

    @NotBlank
    private String nome;

    @NotBlank
    @Email 
    @Indexed(unique = true) 
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String endereco;

    private String telefone;

    public static final String SEQUENCE_NAME = "clientes_sequence";
}

