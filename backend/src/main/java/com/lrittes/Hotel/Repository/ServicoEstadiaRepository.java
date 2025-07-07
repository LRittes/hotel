package com.lrittes.Hotel.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.ServicoEstadia;

@Repository
public interface ServicoEstadiaRepository extends MongoRepository<ServicoEstadia, String> {

    Optional<ServicoEstadia> findBySeid(Long seId);

    void deleteBySeid(Long seId);
}
