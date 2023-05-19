package com.blocky.blockyend.dto;

import javax.validation.constraints.NotBlank;

public class NotasDto {
    @NotBlank
    private int userId;
    @NotBlank
    private String titulo;
    @NotBlank
    private String texto;

    public NotasDto(int userId, String titulo, String texto) {
        this.userId = userId;
        this.titulo = titulo;
        this.texto = texto;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
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
