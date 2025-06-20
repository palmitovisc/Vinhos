package com.vinheria.vinhos.repositories;

import com.vinheria.vinhos.entities.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}