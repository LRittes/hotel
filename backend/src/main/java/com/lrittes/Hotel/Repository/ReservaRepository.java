package com.lrittes.Hotel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    
    @Query(value = "select * from reserva r where r.cliente_id = :id", nativeQuery = true)
    List<Reserva> getReservasByClienteId(Long id);

}
