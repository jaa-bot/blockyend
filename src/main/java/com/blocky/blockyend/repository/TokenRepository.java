package com.blocky.blockyend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blocky.blockyend.entity.Token;

public interface TokenRepository extends JpaRepository<Token, String> {
}
