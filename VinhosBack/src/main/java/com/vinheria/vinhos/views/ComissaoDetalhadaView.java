package com.vinheria.vinhos.views;

public interface ComissaoDetalhadaView {
    Long getComissaoId();
    Long getRepresentanteId();
    String getTelefone();
    String getRepresentanteNome();
    Long getPedidoId();
    Double getPedidoTotal();
    Double getPercentual();
    Double getValorComissao();
    java.time.LocalDateTime getData();
}