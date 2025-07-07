package com.lrittes.Hotel.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Reserva;

@Repository
public interface ReservaRepository extends MongoRepository<Reserva, String> {

    
    @Query("{'clienteId': ?0}")
    List<Reserva> getReservasByClienteId(Long id);

    Optional<Reserva> findByRid(Long rId);

    void deleteByRid(Long rId);

    @Query("{ " +
           "    'quartoId': ?0, " +
           "    'status': { '$in': ?1 }, " +
           "    'dataCheckinPrevista': { '$lt': ?3 }, " + 
           "    'dataCheckoutPrevisto': { '$gt': ?2 } " +
           "}")
    List<Reserva> findConflictingReservations(Long quartoId, List<String> status, LocalDate checkin, LocalDate checkout);

}
