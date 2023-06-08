package com.blocky.blockyend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.blocky.blockyend.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "notas")
public class Notas implements Serializable{
    
    private int id;
    private Usuario usuarioid;
    private String titulo;
    private String texto;

    public Notas(Usuario usuarioid, String titulo, String texto) {
        this.usuarioid = usuarioid;
        this.titulo = titulo;
        this.texto = texto;
    }

    public Notas(){}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuarioid")
    public Usuario getUsuarioid() {
        return usuarioid;
    }
    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Column(name = "texto", length = 1000)
    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    } 
}
