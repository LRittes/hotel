package com.lrittes.Hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.TipoQuarto;

@Repository
public interface TipoQuartoRepository extends JpaRepository<TipoQuarto, Long> {
}
