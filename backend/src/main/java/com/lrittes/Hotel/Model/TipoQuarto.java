package com.lrittes.Hotel.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Document(collection = "tipos_quarto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoQuarto {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private Long tqid;

    @DBRef
    @JsonBackReference
    private Hotel hotel;

    @NotNull
    private Plano plano;

    @NotNull
    private TipoEnum tipoQuarto;

    @NotNull
    private BigDecimal precoNoite;

    public enum Plano {
        standard, vip, luxo, presidencial
    }

    public enum TipoEnum {
        single, duplo, casal, suite_master
    }

    public static final String SEQUENCE_NAME = "tq_sequence";
}