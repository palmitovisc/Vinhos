package com.vinheria.vinhos.security;

import com.vinheria.vinhos.repositories.PedidoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Helper de autorização – verifica se o pedido pertence ao representante logado.
 */
@Component
public class AuthzService {

    private final PedidoRepository pedidoRepository;

    public AuthzService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Retorna true se o pedido pertence ao e-mail do usuário autenticado.
     */
    public boolean isOwnerPedido(Long pedidoId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return pedidoRepository.existsByIdAndRepresentanteUsuarioEmail(pedidoId, email);
    }
}
