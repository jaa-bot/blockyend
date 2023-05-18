package com.blocky.blockyend.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocky.blockyend.security.entity.Rol;
import com.blocky.blockyend.security.enums.RolNombre;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
