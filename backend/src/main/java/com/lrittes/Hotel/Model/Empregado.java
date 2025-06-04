package com.lrittes.Hotel.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "empregado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empregado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String endereco;

    @Column(length = 20)
    private String telefone;
}
