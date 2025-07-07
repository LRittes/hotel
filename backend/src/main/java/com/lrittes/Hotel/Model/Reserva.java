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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Document(collection = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    private String id;
    
    @NotBlank
    @Indexed(unique = true)
    private Long rid;

    @NotNull
    private LocalDate dataReserva;

    @NotNull
    private LocalDate dataCheckinPrevista;

    @NotNull
    private LocalDate dataCheckoutPrevisto;

    private Long tipoQuartoId;

    private Long quartoId;

    private Long hotelId;

    private Boolean camaExtra = false;

    private Long clienteId; // Assumindo que você também converterá a entidade Cliente

    @NotNull
    private BigDecimal valor;

    private BigDecimal valor_servicos_extra = BigDecimal.ZERO;

    @NotNull
    private StatusReserva status = StatusReserva.pendente;

    @DBRef
    private Estadia estadia;

    public enum StatusReserva {
        confirmada, pendente, cancelada
    }

    public long getQuantidadeDeDias() {
        if (dataCheckinPrevista == null || dataCheckoutPrevisto == null || dataCheckoutPrevisto.isBefore(dataCheckinPrevista)) {
            return 0;
        }
        
        return ChronoUnit.DAYS.between(dataCheckinPrevista, dataCheckoutPrevisto);
    }

    public static final String SEQUENCE_NAME = "res_sequence";
}