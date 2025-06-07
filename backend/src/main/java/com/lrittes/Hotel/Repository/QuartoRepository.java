package com.lrittes.Hotel.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Quarto;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    @Query(value = "select * from roomByHotelId(:id)", nativeQuery = true)
    List<Map<String, Object>> roomByHotelId(Long id);
}
