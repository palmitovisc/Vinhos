package com.vinheria.vinhos.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "vinhos")
public class Vinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String safra;
    private String tipo;
    private String notas;
    private String harmonizacoes;
    private String imagemUrl;

    public Vinho() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSafra() { return safra; }
    public void setSafra(String safra) { this.safra = safra; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public String getHarmonizacoes() { return harmonizacoes; }
    public void setHarmonizacoes(String harmonizacoes) { this.harmonizacoes = harmonizacoes; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
}