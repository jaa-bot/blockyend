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
@Table(name = "contacto")
public class Contacto implements Serializable{

    private int id;
    private String titulo;
    private String descripcion;
    private Usuario remitente;
    private Usuario destinatario;

    public Contacto(String titulo, String descripcion, Usuario remitente, Usuario destinatario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.remitente = remitente;
        this.destinatario = destinatario;
    }

    public Contacto(){
        
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "remitente")
    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "destinatario")
    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

}