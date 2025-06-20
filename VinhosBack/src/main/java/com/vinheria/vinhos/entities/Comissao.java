package com.vinheria.vinhos.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "comissoes") 
public class Comissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;
    private Double percentual;
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "representante_id")
    private Representante representante;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Comissao() {}

    // Getters e Setters
public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Double getValor() {
    return valor;
}

public void setValor(Double valor) {
    this.valor = valor;
}

public Double getPercentual() {
    return percentual;
}

public void setPercentual(Double percentual) {
    this.percentual = percentual;
}

public LocalDateTime getData() {
    return data;
}

public void setData(LocalDateTime data) {
    this.data = data;
}

public Representante getRepresentante() {
    return representante;
}

public void setRepresentante(Representante representante) {
    this.representante = representante;
}

public Pedido getPedido() {
    return pedido;
}

public void setPedido(Pedido pedido) {
    this.pedido = pedido;
}
    
    public Double calcularComissao() {
        if (pedido != null && pedido.getTotal() != null && percentual != null) {
            return pedido.getTotal() * (percentual / 100);
        }
        return 0.0;
    }
}