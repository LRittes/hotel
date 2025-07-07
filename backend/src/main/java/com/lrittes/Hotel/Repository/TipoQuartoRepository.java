package com.lrittes.Hotel.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.TipoQuarto;

@Repository
public interface TipoQuartoRepository extends MongoRepository<TipoQuarto, String> {

    Optional<TipoQuarto> findByTqid(Long tqId);

    void deleteByTqid(Long tqId);
}
