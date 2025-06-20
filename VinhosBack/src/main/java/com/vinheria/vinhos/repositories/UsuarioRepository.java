package com.vinheria.vinhos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinheria.vinhos.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /** Busca para autenticação */
    Optional<Usuario> findByEmail(String email);

    /** Evitar e-mail duplicado no cadastro */
    boolean existsByEmail(String email);
}