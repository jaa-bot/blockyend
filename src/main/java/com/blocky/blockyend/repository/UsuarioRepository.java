package com.blocky.blockyend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.blocky.blockyend.security.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByNombre(String nombre);
    boolean existsByNombreUsuario(String nombreUsuario);
}
