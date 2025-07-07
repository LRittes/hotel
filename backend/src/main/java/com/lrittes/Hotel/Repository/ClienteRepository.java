package com.lrittes.Hotel.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.lrittes.Hotel.Model.Cliente;



@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {

    Optional<Cliente> findByClienteId(Long cId);

    void deleteByClienteId(Long cId);

    @Query("{ 'email': ?0, 'password': ?1 }")
    Optional<Cliente> login(String email, String password);
}
