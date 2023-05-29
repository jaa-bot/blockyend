package com.blocky.blockyend.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocky.blockyend.dto.LogDto;
import com.blocky.blockyend.dto.Mensaje;
import com.blocky.blockyend.entity.Log;
import com.blocky.blockyend.security.entity.Usuario;
import com.blocky.blockyend.service.LogService;
import com.blocky.blockyend.service.UsuarioService;

@RestController
@RequestMapping("/Log")
@CrossOrigin(origins = "*")
public class LogController {

    @Autowired
    LogService logService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/listaLog")
    public ResponseEntity<List<Log>> list() {
        List<Log> list = logService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/nuevoLog")
    public void create(@RequestBody LogDto logDto) {

        Optional<Usuario> optionalUsuario = usuarioService.getOne(logDto.getUserId());

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            Date fecha = new Date();
            
            Log log = new Log(usuario, logDto.getAccion(), String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(fecha)));
            logService.save(log);
        } else {
            System.out.println("no se pudo crear el log correctamente");
        }
    }

}
