package com.vinheria.vinhos.repositories;

import com.vinheria.vinhos.entities.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByRepresentanteId(Long representanteId);

    /* true se o pedido existe e pertence ao e-mail informado  */
        boolean existsByIdAndRepresentanteUsuarioEmail(Long id, String email);
    
        /* lista só do representante  */
        List<Pedido> findByRepresentanteUsuarioEmail(String email);
        /* usado pelo controller para filtros */
    List<Pedido> findByClienteId(Long clienteId);
    }