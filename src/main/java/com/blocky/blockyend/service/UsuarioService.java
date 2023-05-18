package com.blocky.blockyend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blocky.blockyend.repository.UsuarioRepository;
import com.blocky.blockyend.security.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository productoRepository;

    public List<Usuario> list(){
        return productoRepository.findAll();
    }

    public Optional<Usuario> getOne(int id){
        return productoRepository.findById(id);
    }

    public Optional<Usuario> getByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public void  save(Usuario producto){
        productoRepository.save(producto);
    }

    public void delete(int id){
        productoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return productoRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return productoRepository.existsByNombre(nombre);
    }

    public boolean existsByNombreUsuario(String nombre){
        return productoRepository.existsByNombreUsuario(nombre);
    }

    public Optional<Usuario> getByNombreUsuario(String nombre){
        return productoRepository.findByNombreUsuario(nombre);
    }
}
