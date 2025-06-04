package com.lrittes.Hotel.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "limpeza", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"empregado_id", "quarto_id", "data"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Limpeza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empregado_id", nullable = false)
    @JsonBackReference
    private Empregado empregado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarto_id", nullable = false)
    @JsonBackReference
    private Quarto quarto;

    @Column(nullable = false)
    private LocalDate data;
}
