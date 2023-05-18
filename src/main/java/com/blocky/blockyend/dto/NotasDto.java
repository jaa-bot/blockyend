package com.blocky.blockyend.dto;

import javax.validation.constraints.NotBlank;

import com.blocky.blockyend.security.entity.Usuario;

public class NotasDto {
    @NotBlank
    private Usuario userId;
    @NotBlank
    private String titulo;
    @NotBlank
    private String texto;

    public NotasDto(Usuario userId, String titulo, String texto) {
        this.userId = userId;
        this.titulo = titulo;
        this.texto = texto;
    }

    public Usuario getUserId() {
        return userId;
    }
    public void setUserId(Usuario userId) {
        this.userId = userId;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
