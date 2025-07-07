package com.lrittes.Hotel.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Document(collection = "estadias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estadia {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private Long eid;

    @NotNull
    private LocalDate dataCheckin;

    @NotNull
    private LocalDate dataCheckout;

    @NotNull
    private Long clienteId; 

    @NotNull
    private Long quartoId;

    @NotNull
    @Indexed(unique = true, background = true)
    private Long reservaId;

    public static final String SEQUENCE_NAME = "esta_sequence";
}