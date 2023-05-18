package com.blocky.blockyend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocky.blockyend.security.entity.Usuario;
import com.blocky.blockyend.security.repository.UsuarioRepositorySecurity;

import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceSecurity {

    @Autowired
    UsuarioRepositorySecurity usuarioRepository;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }
}
