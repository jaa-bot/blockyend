package com.blocky.blockyend.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.blocky.blockyend.entity.Notas;

public class UsuarioDto {
    @NotBlank
    private String nombre;
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String email;
    @NotBlank
    private String contra;

    private Set<Notas> notas;
    
    public UsuarioDto(@NotBlank String nombre, @NotBlank String nombreUsuario, @NotBlank String email,
            @NotBlank String contra, Set<Notas> notas) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contra = contra;
        this.notas = notas;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContra() {
        return contra;
    }
    public void setContra(String contra) {
        this.contra = contra;
    }
    public Set<Notas> getNotas() {
        return notas;
    }
    public void setNotas(Set<Notas> notas) {
        this.notas = notas;
    }

    
}
