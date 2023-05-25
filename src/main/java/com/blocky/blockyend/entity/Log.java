package com.blocky.blockyend.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.blocky.blockyend.security.entity.Usuario;

@Entity
@Table(name = "log")
public class Log implements Serializable {
    private int id;
    private String accion;
    private Usuario usuarioid;

    public Log(String accion, Usuario usuarioid) {
        this.accion = accion;
        this.usuarioid = usuarioid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

}
