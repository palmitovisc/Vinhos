package com.vinheria.vinhos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vinheria.vinhos.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContainingIgnoreCase(String nome);}
