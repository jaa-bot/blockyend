package com.blocky.blockyend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocky.blockyend.entity.Token;
import com.blocky.blockyend.repository.TokenRepository;

@Service
@Transactional
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public List<Token> list() {
        return tokenRepository.findAll();
    }
    public void save(Token token) {
        tokenRepository.save(token);
    }

    public void delete(String id) {
        tokenRepository.deleteById(id);
    }

    public Optional<Token> getOne(String id){
        return tokenRepository.findById(id);
    }

}
