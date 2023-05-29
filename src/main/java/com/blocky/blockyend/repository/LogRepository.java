package com.blocky.blockyend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocky.blockyend.entity.Log;
import com.blocky.blockyend.security.entity.Usuario;

public interface LogRepository extends JpaRepository<Log, Integer> {
    List<Log> findByUsuarioid(Usuario usuarioid);
}
