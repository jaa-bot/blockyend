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
    
    @NotBlank
    private boolean responder;

    public ContactoDto(@NotBlank String titulo, @NotBlank String descripcion, @NotBlank int remitente,
            @NotBlank int destinatario, @NotBlank boolean responder) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.responder = responder;
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

    public boolean isResponder() {
        return responder;
    }

    public void setResponder(boolean responder) {
        this.responder = responder;
    }

    
}
