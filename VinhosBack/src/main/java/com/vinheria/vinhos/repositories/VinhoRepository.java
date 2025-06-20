package com.vinheria.vinhos.repositories;

import com.vinheria.vinhos.entities.Vinho;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VinhoRepository extends JpaRepository<Vinho, Long> {

    List<Vinho> findByNomeContainingIgnoreCase(String nome);
}