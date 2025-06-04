package com.lrittes.Hotel.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Cliente;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "select * from cliente c where c.email = :email and c.password = :password", nativeQuery = true)
    Optional<Cliente> login(String email, String password);
}
