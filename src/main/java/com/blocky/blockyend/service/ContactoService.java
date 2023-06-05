package com.blocky.blockyend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocky.blockyend.entity.Contacto;
import com.blocky.blockyend.repository.ContactoRepository;
import com.blocky.blockyend.security.entity.Usuario;

@Service
@Transactional
public class ContactoService {

    @Autowired
    ContactoRepository contactoRepository;

    public List<Contacto> list() {
        return contactoRepository.findAll();
    }

    public Optional<Contacto> getOne(int id) {
        return contactoRepository.findById(id);
    }

    public void save(Contacto producto) {
        contactoRepository.save(producto);
    }

    public void delete(int id) {
        contactoRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return contactoRepository.existsById(id);
    }

    public List<Contacto> listByRemitente(Usuario usuario) {
        return contactoRepository.findByRemitente(usuario);
    }

    public List<Contacto> listByDestinatario(Usuario usuario) {
        return contactoRepository.findByDestinatario(usuario);
    }
}
