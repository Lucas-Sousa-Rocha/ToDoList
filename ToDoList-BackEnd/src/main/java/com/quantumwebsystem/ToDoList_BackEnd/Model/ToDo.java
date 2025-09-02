package com.quantumwebsystem.ToDoList_BackEnd.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_todo")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private Boolean concluido;

    private LocalDate dataCriacao;

    public ToDo() {
    }

    public ToDo(String descricao, Boolean concluido, LocalDate dataCriacao) {
        this.descricao = descricao;
        this.concluido = concluido;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
