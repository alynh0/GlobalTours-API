package com.globaltours.agencia.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "viagem")
public class Viagem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origem;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDate dataIda;

    @Column(nullable = false)
    private LocalDate dataVolta;

    @Column(nullable = false)
    private float preco;
    
    private String descricao;

    @OneToMany(mappedBy = "viagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;
    
    public Viagem() {
    }


    public Viagem(Long id, String origem, String destino, LocalDate dataIda, LocalDate dataVolta, float preco,
            String descricao, List<Comentario> comentarios) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.dataIda = dataIda;
        this.dataVolta = dataVolta;
        this.preco = preco;
        this.descricao = descricao;
        this.comentarios = comentarios;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getOrigem() {
        return origem;
    }


    public void setOrigem(String origem) {
        this.origem = origem;
    }


    public String getDestino() {
        return destino;
    }


    public void setDestino(String destino) {
        this.destino = destino;
    }


    public LocalDate getDataIda() {
        return dataIda;
    }


    public void setDataIda(LocalDate dataIda) {
        this.dataIda = dataIda;
    }


    public LocalDate getDataVolta() {
        return dataVolta;
    }


    public void setDataVolta(LocalDate dataVolta) {
        this.dataVolta = dataVolta;
    }


    public float getPreco() {
        return preco;
    }


    public void setPreco(float preco) {
        this.preco = preco;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public List<Comentario> getComentarios() {
        return comentarios;
    }


    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    
}
