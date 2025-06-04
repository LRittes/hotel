package com.lrittes.Hotel.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_reserva", nullable = false)
    private LocalDate dataReserva;

    @Column(name = "data_checkin_prevista", nullable = false)
    private LocalDate dataCheckinPrevista;

    @Column(name = "data_checkout_previsto", nullable = false)
    private LocalDate dataCheckoutPrevisto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_quarto_id", nullable = false)
    @JsonBackReference
    private TipoQuarto tipoQuarto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarto_id", nullable = false)
    @JsonBackReference
    private Quarto quarto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;

    @Column(name = "cama_extra", nullable = false)
    private Boolean camaExtra = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private StatusReserva status = StatusReserva.pendente;

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Estadia estadia;

    public enum StatusReserva {
        confirmada, pendente, cancelada
    }
}
