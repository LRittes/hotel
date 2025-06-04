package com.lrittes.Hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Quarto;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
}
