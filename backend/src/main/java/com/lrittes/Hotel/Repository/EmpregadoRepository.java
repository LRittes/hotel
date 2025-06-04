package com.lrittes.Hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Empregado;

@Repository
public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {
}
