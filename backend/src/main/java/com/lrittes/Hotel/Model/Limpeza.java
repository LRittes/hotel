package com.lrittes.Hotel.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Document(collection = "limpezas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Limpeza {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private Long lid;

    @NotNull
    private Long empregadoId; // Assumindo que você também converterá a entidade Empregado

    @NotNull
    private Long quartoId;

    @NotNull
    private LocalDate data;

    public static final String SEQUENCE_NAME = "lim_sequence";
}