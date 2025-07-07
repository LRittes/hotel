package com.lrittes.Hotel.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Limpeza;

@Repository
public interface LimpezaRepository extends MongoRepository<Limpeza, String> {

    Optional<Limpeza> findByLid(Long lId);

    void deleteByLid(Long lId);
}
