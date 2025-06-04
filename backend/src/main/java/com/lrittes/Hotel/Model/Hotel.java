package com.lrittes.Hotel.Model;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String endereco;

    @Column(length = 20)
    private String telefone;

    @Column
    @Range(min = 0, max = 10, message = "Nota deve estar entre 0 e 10")
    private Double nota;
}