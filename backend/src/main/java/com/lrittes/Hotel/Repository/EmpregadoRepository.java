package com.lrittes.Hotel.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Empregado;
import java.util.Optional;


@Repository
public interface EmpregadoRepository extends MongoRepository<Empregado, String> {

    Optional<Empregado> findByEid(Long eId);

    void deleteByEid(Long eId);
}
