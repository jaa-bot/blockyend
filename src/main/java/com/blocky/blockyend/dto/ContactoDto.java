package com.blocky.blockyend.dto;

import javax.validation.constraints.NotBlank;

public class ContactoDto {

    @NotBlank
    private String titulo;

    @NotBlank
    private String descripcion;

    @NotBlank
    private int remitente;

    @NotBlank
    private int destinatario;

    public ContactoDto(@NotBlank String titulo, @NotBlank String descripcion, @NotBlank int remitente,
            @NotBlank int destinatario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.remitente = remitente;
        this.destinatario = destinatario;
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

    public int getRemitente() {
        return remitente;
    }

    public void setRemitente(int remitente) {
        this.remitente = remitente;
    }

    public int getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(int destinatario) {
        this.destinatario = destinatario;
    }
}
