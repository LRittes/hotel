package com.lrittes.Hotel.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "servicos_estadia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoEstadia {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private Long seid;

    @NotNull
    private Long estadiaId;

    @NotNull
    private Long servicoExtraId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
    private LocalDateTime dataHora;

    private Integer quantidade = 1;

    private String descricao;

    public static final String SEQUENCE_NAME = "serEs_sequence";

    // A lógica @PrePersist foi removida.
    // Defina a data/hora na camada de serviço antes de salvar. Ex:
    // if (servicoEstadia.getDataHora() == null) {
    //     servicoEstadia.setDataHora(LocalDateTime.now());
    // }
    // servicoEstadiaRepository.save(servicoEstadia);
}