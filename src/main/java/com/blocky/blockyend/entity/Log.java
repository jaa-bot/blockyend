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
@Table(name = "log")
public class Log implements Serializable {

    private int id;
    private Usuario usuarioid;
    private String accion;
    private String fecha;

    public Log() {
    }

    public Log(Usuario usuarioid, String accion, String fecha) {
        this.usuarioid = usuarioid;
        this.accion = accion;
        this.fecha = fecha;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuarioid")
    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}