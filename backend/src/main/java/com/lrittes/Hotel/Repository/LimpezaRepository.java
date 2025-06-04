package com.lrittes.Hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Limpeza;

@Repository
public interface LimpezaRepository extends JpaRepository<Limpeza, Long> {
}
