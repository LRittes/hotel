package com.lrittes.Hotel.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "servico_estadia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoEstadia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estadia_id", nullable = false)
    @JsonBackReference
    private Estadia estadia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_extra_id")
    @JsonBackReference
    private ServicoExtra servicoExtra;

    @Column(name = "data_hora", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a")  
    private Timestamp dataHora;

    @Column(nullable = false)
    private Integer quantidade = 1;

    @Column(nullable = false)
    private String descricao;

    @PrePersist
    protected void onCreate() {
        if (dataHora == null) {
            dataHora = new Timestamp(System.currentTimeMillis());
        }
    }

}
