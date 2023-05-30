package com.blocky.blockyend.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocky.blockyend.dto.LogDto;
import com.blocky.blockyend.dto.Mensaje;
import com.blocky.blockyend.dto.NotasDto;
import com.blocky.blockyend.entity.Notas;
import com.blocky.blockyend.security.entity.Usuario;
import com.blocky.blockyend.service.NotasService;
import com.blocky.blockyend.service.UsuarioService;

@RestController
@RequestMapping("/notas")
@CrossOrigin(origins = "*")
public class NotasController {

    @Autowired
    NotasService notasService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LogController logController;

    @GetMapping("/listaNotas")
    public ResponseEntity<List<Notas>> list() {
        List<Notas> list = notasService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/notasPorUsuario/{usuarioid}")
    public ResponseEntity<List<Notas>> getAllNotasByUsuario(@PathVariable("usuarioid") int usuarioid) {
        Optional<Usuario> optionalUsuario = usuarioService.getOne(usuarioid);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            List<Notas> notas = notasService.listByUsuario(usuario);
            return ResponseEntity.ok(notas);
        } else {
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/detailNotas/{id}")
    public ResponseEntity<Notas> getById(@PathVariable("id") int id) {
        if (!notasService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Notas notas = notasService.getOne(id).get();
        return new ResponseEntity(notas, HttpStatus.OK);
    }

    @PostMapping("/nuevoNotas")
    public ResponseEntity<?> create(@RequestBody NotasDto notasDto) {
        if (StringUtils.isBlank(notasDto.getTitulo())) {
            return new ResponseEntity(new Mensaje("El título es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> optionalUsuario = usuarioService.getOne(notasDto.getUserId());

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            Notas notas = new Notas(usuario, notasDto.getTitulo(), notasDto.getTexto());
            notasService.save(notas);

            logController.create(
                    new LogDto(usuario.getId(), "El usuario: " + usuario.getNombreUsuario()
                            + " ha creado una nueva nota. Titulo de la nota: " + notas.getTitulo()));

            return new ResponseEntity(new Mensaje("Nota creada"), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Mensaje("Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateNotas/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody NotasDto notasDto) {

        Notas nota = notasService.getOne(id).get();
        nota.setTitulo(notasDto.getTitulo());
        nota.setTexto(notasDto.getTexto());
        notasService.save(nota);

        Optional<Usuario> optionalUsuario = usuarioService.getOne(notasDto.getUserId());

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            logController.create(
                    new LogDto(usuario.getId(), "El usuario: " + usuario.getNombreUsuario()
                            + " ha actualizado la nota con titulo: " + nota.getTitulo()));
        }

        return new ResponseEntity(new Mensaje("nota actualizada"), HttpStatus.OK);
    }

    @DeleteMapping("/deleteNotas/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!notasService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);

        Optional<Usuario> optionalUsuario = usuarioService.getOne(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            Notas nota = notasService.getOne(id).get();

            logController.create(
                    new LogDto(usuario.getId(), "El usuario: " + usuario.getNombreUsuario()
                            + " ha borrado la nota con titulo: " + nota.getTitulo()));
        }

        notasService.delete(id);
        return new ResponseEntity(new Mensaje("nota eliminada"), HttpStatus.OK);
    }
}
