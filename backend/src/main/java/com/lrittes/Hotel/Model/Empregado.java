package com.lrittes.Hotel.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "empregado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empregado {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private Long eid;


    @NotBlank
    @Size(max = 100)
    private String nome;
    
    @NotBlank
    @Size(min = 11, max = 11)
    @Indexed(unique = true)
    private String cpf;


    @NotBlank
    private String endereco;

    @Size(max = 20)
    private String telefone;

    public static final String SEQUENCE_NAME = "emp_sequence";
}
