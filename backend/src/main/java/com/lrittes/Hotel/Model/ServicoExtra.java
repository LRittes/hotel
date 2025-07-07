package com.lrittes.Hotel.Model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "servicos_extra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoExtra {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private Long seid;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;

    public static final String SEQUENCE_NAME = "servEx_sequence";
}