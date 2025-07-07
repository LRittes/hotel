package com.lrittes.Hotel.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Quarto;

@Repository
public interface QuartoRepository extends MongoRepository<Quarto, String> {

    @Query("{'hotelId': ?0}")
    List<Quarto> roomByHotelId(Long id);

    Optional<Quarto> findByQid(Long qId);

    void deleteByQid(Long qId);
}
