package com.blocky.blockyend.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.blocky.blockyend.entity.Notas;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    private int id;
    @NotNull
    private String nombre;
    @NotNull
    private String nombreUsuario;
    @NotNull
    private String email;
    @NotNull
    private String password;
    
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    private Set<Notas> notas;

    public Usuario() {
    }

    public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email,
            @NotNull String password, Set<Notas> notas) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.notas = notas;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    public Set<Notas> getNotas() {
        return notas;
    }

    public void setNotas(Set<Notas> notas) {
        this.notas = notas;
    }
}
