package com.lrittes.Hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Estadia;

@Repository
public interface EstadiaRepository extends JpaRepository<Estadia, Long> {
}
