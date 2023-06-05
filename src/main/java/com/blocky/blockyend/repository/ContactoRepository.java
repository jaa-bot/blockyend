package com.blocky.blockyend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocky.blockyend.entity.Contacto;
import com.blocky.blockyend.security.entity.Usuario;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    List<Contacto> findByRemitente(Usuario remitente);
    List<Contacto> findByDestinatario(Usuario destinarario);
}
