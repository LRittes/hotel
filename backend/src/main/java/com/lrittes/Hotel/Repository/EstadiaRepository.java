package com.lrittes.Hotel.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Estadia;
import java.util.Optional;


@Repository
public interface EstadiaRepository extends MongoRepository<Estadia, String> {

    Optional<Estadia> findByEid(Long eId);

    Optional<Estadia> findByReservaId(Long rId);

    void deleteByEid(Long eId);

    void deleteByReservaId(Long rId);

}
