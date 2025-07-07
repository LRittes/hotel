package com.lrittes.Hotel.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.ServicoExtra;

@Repository
public interface ServicoExtraRepository extends MongoRepository<ServicoExtra, String> {

    Optional<ServicoExtra> findBySeid(Long seId);

    void deleteBySeid(Long seId);
}
