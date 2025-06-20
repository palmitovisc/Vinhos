package com.vinheria.vinhos.repositories;

import com.vinheria.vinhos.entities.Comissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComissaoRepository extends JpaRepository<Comissao, Long> {
}