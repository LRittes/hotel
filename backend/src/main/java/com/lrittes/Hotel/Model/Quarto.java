package com.lrittes.Hotel.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "quarto", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero", "hotel_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;

    @Column(nullable = false)
    private Integer andar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_quarto_id", nullable = false)
    @JsonBackReference
    private TipoQuarto tipoQuarto;
}
