package com.blocky.blockyend.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocky.blockyend.security.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepositorySecurity extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);

}
