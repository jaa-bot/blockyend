package com.blocky.blockyend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocky.blockyend.dto.ContactoDto;
import com.blocky.blockyend.dto.LogDto;
import com.blocky.blockyend.dto.Mensaje;
import com.blocky.blockyend.entity.Contacto;
import com.blocky.blockyend.security.entity.Usuario;
import com.blocky.blockyend.service.ContactoService;
import com.blocky.blockyend.service.UsuarioService;

import io.micrometer.core.instrument.util.StringUtils;

@RestController
@RequestMapping("/contacto")
@CrossOrigin(origins = "*")
public class ContactoController {

    @Autowired
    ContactoService contactoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LogController logController;

    @GetMapping("/listaContacto")
    public ResponseEntity<List<Contacto>> list() {
        List<Contacto> list = contactoService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/contactoPorRemitente/{remitente}")
    public ResponseEntity<List<Contacto>> getRemitentes(@PathVariable("remitente") int remitente) {
        Optional<Usuario> optionalUsuario = usuarioService.getOne(remitente);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            List<Contacto> notas = contactoService.listByRemitente(usuario);
            return ResponseEntity.ok(notas);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/contactoPorDestinatario/{destinatario}")
    public ResponseEntity<List<Contacto>> getDestinarario(@PathVariable("destinatario") int destinatario) {
        Optional<Usuario> optionalUsuario = usuarioService.getOne(destinatario);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            List<Contacto> notas = contactoService.listByDestinatario(usuario);
            return ResponseEntity.ok(notas);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/nuevoContacto")
    public ResponseEntity<?> create(@RequestBody ContactoDto notasDto) {
        if (StringUtils.isBlank(notasDto.getTitulo())) {
            return new ResponseEntity<>(new Mensaje("El título es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> optionalRemitente = usuarioService.getOne(notasDto.getRemitente());
        Optional<Usuario> optionalDestinatario = usuarioService.getOne(notasDto.getDestinatario());

        if (optionalRemitente.isPresent() && optionalDestinatario.isPresent()) {
            Usuario usuarioRemitente = optionalRemitente.get();
            Usuario usuarioDestinatario = optionalDestinatario.get();
            Contacto contacto = new Contacto(notasDto.getTitulo(), notasDto.getDescripcion(), usuarioRemitente,
                    usuarioDestinatario, notasDto.isResponder());
            contactoService.save(contacto);

            logController.create(
                new LogDto(contacto.getId(), "El usuario: " + usuarioRemitente.getNombreUsuario()
                        + " ha enviado una pregunta. Titulo de la pregunta: " + contacto.getTitulo()));

            return new ResponseEntity<>(new Mensaje("Contacto creado"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Mensaje("Remitente o destinatario no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detailContacto/{id}")
    public ResponseEntity<Contacto> getById(@PathVariable("id") int id) {
        if (!contactoService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Contacto contacto = contactoService.getOne(id).get();

        return new ResponseEntity<>(contacto, HttpStatus.OK);
    }

    @PutMapping("/updateContacto/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ContactoDto notasDto) {

        Contacto nota = contactoService.getOne(id).get();
        
        nota.setResponder(true);
        contactoService.save(nota);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
