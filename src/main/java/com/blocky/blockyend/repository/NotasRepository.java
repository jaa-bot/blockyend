package com.blocky.blockyend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocky.blockyend.entity.Notas;
import com.blocky.blockyend.security.entity.Usuario;


public interface NotasRepository extends JpaRepository<Notas, Integer> {
    List<Notas> findByUsuarioid(Usuario usuarioid);
}
