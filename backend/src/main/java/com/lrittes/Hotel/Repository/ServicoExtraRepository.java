package com.lrittes.Hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.ServicoExtra;

@Repository
public interface ServicoExtraRepository extends JpaRepository<ServicoExtra, Long> {
}
