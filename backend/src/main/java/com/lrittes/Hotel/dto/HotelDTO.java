package com.lrittes.Hotel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private String id;
    private Long hid;
    private String nome;
    private String endereco;
    private String telefone;
    private Double nota;
}
