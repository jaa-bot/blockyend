package com.blocky.blockyend.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.blocky.blockyend.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Token implements Serializable{

   
    private String endpoint;
    private String auth;
    private String p256dh;
    private Usuario usuarioid;

    public Token(String endpoint, String auth, String p256dh, Usuario usuarioid) {
        this.endpoint = endpoint;
        this.auth = auth;
        this.p256dh = p256dh;
        this.usuarioid = usuarioid;
    }

    public Token() {
    }

    @Id
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getP256dh() {
        return p256dh;
    }

    public void setP256dh(String p256dh) {
        this.p256dh = p256dh;
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
}
