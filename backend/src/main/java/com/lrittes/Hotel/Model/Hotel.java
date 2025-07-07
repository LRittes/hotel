package com.lrittes.Hotel.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range; 

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "hoteis") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    private String id; 

    @NotBlank
    @Indexed(unique = true)
    private Long hid;

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "O endereço não pode estar em branco")
    private String endereco;

    private String telefone;

    @Range(min = 0, max = 10, message = "Nota deve estar entre 0 e 10")
    private Double nota;

    public static final String SEQUENCE_NAME = "hot_sequence";
}