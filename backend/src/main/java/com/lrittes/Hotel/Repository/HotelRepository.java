package com.lrittes.Hotel.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Hotel;
import java.util.Optional;


@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {

    Optional<Hotel> findByHid(Long hId);

    void deleteByHid(Long hId);
}
