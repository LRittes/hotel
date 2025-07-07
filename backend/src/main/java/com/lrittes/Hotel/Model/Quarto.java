package com.lrittes.Hotel.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "quartos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quarto {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private Long qid;

    @NotNull(message = "O número do quarto é obrigatório")
    private Integer numero;

    @NotNull(message = "O número do hotel é obrigatório")
    private Long hotelId;

    @NotNull(message = "O andar é obrigatório")
    private Integer andar;

    @NotNull
    private Long tipoQuartoId;

    public static final String SEQUENCE_NAME = "qua_sequence";
}