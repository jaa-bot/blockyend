package com.blocky.blockyend.dto;

import javax.validation.constraints.NotBlank;

public class ContactoDto {

    @NotBlank
    private String titulo;

    @NotBlank
    private String descripcion;

    @NotBlank
    private Integer remitente;

    @NotBlank
    private Integer destinatario;

    public ContactoDto(@NotBlank String titulo, @NotBlank String descripcion, @NotBlank Integer remitente,
            @NotBlank Integer destinatario) {
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

    public Integer getRemitente() {
        return remitente;
    }

    public void setRemitente(Integer remitente) {
        this.remitente = remitente;
    }

    public Integer getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Integer destinatario) {
        this.destinatario = destinatario;
    }
}
