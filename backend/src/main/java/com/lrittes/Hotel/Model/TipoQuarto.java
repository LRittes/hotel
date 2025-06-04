package com.lrittes.Hotel.Model;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tipo_quarto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoQuarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING) // Garanta que isso está aqui
    private Plano plano;

    @Column(name = "tp_quarto", nullable = false, length = 20)
    @Enumerated(EnumType.STRING) // E isso também
    private TipoEnum tipoQuarto;

    @Column(name = "preco_noite", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoNoite;

    public enum Plano {
        standard, vip, luxo, presidencial
    }

    public enum TipoEnum {
        single, duplo, casal, suite_master
    }
}

