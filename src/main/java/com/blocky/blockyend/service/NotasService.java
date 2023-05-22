package com.blocky.blockyend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocky.blockyend.entity.Notas;
import com.blocky.blockyend.repository.NotasRepository;
import com.blocky.blockyend.security.entity.Usuario;

@Service @Transactional
public class NotasService
{

    @Autowired
    NotasRepository notasRepository;

    public List<Notas> list()
    {
        return notasRepository.findAll();
    }

    public Optional<Notas> getOne(int id)
    {
        return notasRepository.findById(id);
    }

    public void save(Notas producto)
    {
        notasRepository.save(producto);
    }

    public void delete(int id)
    {
        notasRepository.deleteById(id);
    }

    public boolean existsById(int id)
    {
        return notasRepository.existsById(id);
    }

 public List<Notas> listByUsuario(Usuario usuario) {
    return notasRepository.findByUsuarioid(usuario);
}
}
