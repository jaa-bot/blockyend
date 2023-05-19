package com.blocky.blockyend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blocky.blockyend.entity.Notas;


public interface NotasRepository extends JpaRepository<Notas, Integer> {
    Optional<Notas> findByUsuarioid(Integer usuarioid);
}
