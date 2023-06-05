package com.blocky.blockyend.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocky.blockyend.dto.LogDto;
import com.blocky.blockyend.dto.Mensaje;
import com.blocky.blockyend.security.dto.UsuarioDto;
import com.blocky.blockyend.security.entity.Usuario;
import com.blocky.blockyend.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LogController logController;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> list() {
        List<Usuario> list = usuarioService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable("id") int id) {
        if (!usuarioService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Usuario producto = usuarioService.getOne(id).get();
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping("/detailnameUser/{nombreUsuario}")
    public ResponseEntity<Usuario> getByNombreUser(@PathVariable("nombreUsuario") String nombreUsuario) {
        if (!usuarioService.existsByNombreUsuario(nombreUsuario))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Usuario producto = usuarioService.getByNombreUsuario(nombreUsuario).get();

        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Usuario> getByNombre(@PathVariable("nombre") String nombre) {
        if (!usuarioService.existsByNombre(nombre))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Usuario producto = usuarioService.getByNombre(nombre).get();

        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody UsuarioDto usuarioDto) {

        Usuario usuario = usuarioService.getOne(id).orElse(null);

        if (!usuarioService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!usuarioDto.getNombreUsuario().equals(usuario.getNombreUsuario())
                && usuarioService.existsByNombreUsuario(usuarioDto.getNombreUsuario()))
            return new ResponseEntity<>(new Mensaje("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(usuarioDto.getNombre()))
            return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());

        if (!StringUtils.isBlank(usuarioDto.getPassword()))
            usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));

        usuarioService.save(usuario);

        logController.create(
                new LogDto(usuario.getId(), "El usuario: " + usuario.getNombreUsuario() + " ha actualizado su perfil"));

        return new ResponseEntity<>(new Mensaje("Se ha actualizado el perfil"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateAdmin/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable("id") int id, @RequestBody UsuarioDto usuarioDto) {

        Usuario usuario = usuarioService.getOne(id).orElse(null);

        if (!usuarioService.existsById(id))
            return new ResponseEntity<>(new Mensaje("no existe"), HttpStatus.NOT_FOUND);

        if (!usuarioDto.getNombreUsuario().equals(usuario.getNombreUsuario())
                && usuarioService.existsByNombreUsuario(usuarioDto.getNombreUsuario()))
            return new ResponseEntity<>(new Mensaje("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(usuarioDto.getNombre()))
            return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());

        usuarioService.save(usuario);

        logController.create(
                new LogDto(usuario.getId(), "El Admin ha actualizado al usuario: " + usuario.getNombreUsuario()));

        return new ResponseEntity<>(new Mensaje("Se ha actualizado el perfil"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!usuarioService.existsById(id)) {
            return new ResponseEntity<>(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }

        Usuario usuario = usuarioService.getOne(id).orElse(null);

        logController
                .create(new LogDto(usuario.getId(), "El Admin ha borrado al usuario: " + usuario.getNombreUsuario()));

        usuarioService.delete(id);

        return new ResponseEntity<>(new Mensaje("has eliminado el usuario con id: " + id), HttpStatus.OK);
    }
}
