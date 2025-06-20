package com.vinheria.vinhos.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "representantes")
public class Representante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String telefone;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Representante() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
