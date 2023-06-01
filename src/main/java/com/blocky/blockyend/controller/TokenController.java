package com.blocky.blockyend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocky.blockyend.entity.Token;
import com.blocky.blockyend.service.TokenService;

@RestController
@RequestMapping("/token")
@CrossOrigin(origins = "*")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @GetMapping("/lista")
    public List<Token> list(){
        List<Token> list = tokenService.list();
        return list;
    }

    @PostMapping("/nuevoToken")
    public ResponseEntity<?> añadirToken(@RequestBody Token token) {
        tokenService.save(token);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarToken/{endpoint}")
    public ResponseEntity<?> eliminarToken(@PathVariable("endpoint") String endpoint) {
        tokenService.delete(endpoint);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/detailUser/{id_user}")
    public Optional<Token> cogerId_user(@PathVariable("id_user") String id) {
        return tokenService.getOne(id);
    }
}
