package com.lrittes.Hotel.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Quarto;

@Repository
public interface QuartoRepository extends MongoRepository<Quarto, String> {

    @Aggregation({
        "{ $match: { 'hotelId': ?0 } }",

        "{ $lookup: { " +
        "    from: 'tipos_quarto', " +
        "    localField: 'tipoQuartoId', " +
        "    foreignField: 'tqid', " +
        "    as: 'tipoQuartoDetails' " +
        "} }",

        "{ $unwind: '$tipoQuartoDetails' }",

        "{ $project: { " +
        "    _id: 0, " + 
        "    qid: '$qid', " + 
        "    numero: 1, " + 
        "    andar: 1, " +
        "    hotel_id: '$hotelId', " +
        "    tipo_quarto_id: '$tipoQuartoId', " +
        "    tp_quarto: '$tipoQuartoDetails.tipoQuarto', " +
        "    plano: '$tipoQuartoDetails.plano', " +
        "    preco_noite: '$tipoQuartoDetails.precoNoite' " +
        "} }"
    })
    List<Map<String, Object>> roomByHotelId(Long id);

    Optional<Quarto> findByQid(Long qId);

    void deleteByQid(Long qId);
}
