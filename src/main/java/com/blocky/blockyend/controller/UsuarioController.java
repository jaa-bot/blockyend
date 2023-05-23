package com.blocky.blockyend.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.blocky.blockyend.dto.UsuarioDto;
import com.blocky.blockyend.security.entity.Usuario;
import com.blocky.blockyend.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> list(){
        List<Usuario> list = usuarioService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable("id") int id){
        if(!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
            Usuario producto = usuarioService.getOne(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @GetMapping("/detailnameUser/{nombreUsuario}")
    public ResponseEntity<Usuario> getByNombreUser(@PathVariable("nombreUsuario") String nombreUsuario){
        if(!usuarioService.existsByNombreUsuario(nombreUsuario))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
            Usuario producto = usuarioService.getByNombreUsuario(nombreUsuario).get();
            
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Usuario> getByNombre(@PathVariable("nombre") String nombre){
        if(!usuarioService.existsByNombre(nombre))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
            Usuario producto = usuarioService.getByNombre(nombre).get();

        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UsuarioDto productoDto){
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
            Usuario producto = new Usuario(productoDto.getNombre(), productoDto.getNombreUsuario(), productoDto.getEmail(), productoDto.getContra());
            usuarioService.save(producto);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody UsuarioDto usuarioDto) {
        if (!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
    
        if (usuarioService.existsByNombre(usuarioDto.getNombre())
                && usuarioService.getByNombre(usuarioDto.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
    
        if (StringUtils.isBlank(usuarioDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
    
        Usuario usuario = usuarioService.getOne(id).orElse(null);
        if (usuario == null)
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
    
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
    
        if (!StringUtils.isBlank(usuarioDto.getContra()))
            usuario.setPassword(usuarioDto.getContra());
    
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);
    }
    

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
            usuarioService.delete(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }
}
