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

import com.blocky.blockyend.dto.Mensaje;
import com.blocky.blockyend.dto.NotasDto;
import com.blocky.blockyend.entity.Notas;
import com.blocky.blockyend.service.NotasService;

@RestController
@RequestMapping("/notas")
@CrossOrigin(origins = "*")
public class NotasController {

    @Autowired
    NotasService notasService;

    @GetMapping("/listaNotas")
    public ResponseEntity<List<Notas>> list() {
        List<Notas> list = notasService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detailNotas/{id}")
    public ResponseEntity<Notas> getById(@PathVariable("id") int id) {
        if (!notasService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Notas notas = notasService.getOne(id).get();
        return new ResponseEntity(notas, HttpStatus.OK);
    }

    @PostMapping("/nuevoNotas")
    public ResponseEntity<?> create(@RequestBody NotasDto productoDto) {
        if (StringUtils.isBlank(productoDto.getTitulo()))
            return new ResponseEntity(new Mensaje("el titulo es obligatorio"), HttpStatus.BAD_REQUEST);
        Notas producto = new Notas(productoDto.getUserId(), productoDto.getTitulo(), productoDto.getTexto());
        notasService.save(producto);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }

    @PutMapping("/updateNotas/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody NotasDto notasDto) {
        if (!notasService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);

        Notas nota = notasService.getOne(id).get();
        nota.setUsuario(notasDto.getUserId());
        nota.setTitulo(notasDto.getTitulo());
        nota.setTexto(notasDto.getTexto());
        notasService.save(nota);
        return new ResponseEntity(new Mensaje("nota actualizada"), HttpStatus.OK);
    }

    @DeleteMapping("/deleteNotas/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!notasService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        notasService.delete(id);
        return new ResponseEntity(new Mensaje("nota eliminada"), HttpStatus.OK);
    }

    @GetMapping("/detailnameNotas/{userId}")
    public ResponseEntity<List<Notas>> getByNombre(@PathVariable("userId") int id){
            Optional<Notas> nota = notasService.getAll(id);
        return new ResponseEntity(nota, HttpStatus.OK);
    }
}
