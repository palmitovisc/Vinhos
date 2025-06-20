package com.vinheria.vinhos.views;

public interface PedidoResumoView {
    Long getPedidoId();
    java.time.LocalDateTime getData();
    String getStatus();
    Double getTotal();
    String getClienteNome();
    String getRepresentanteNome();
}
