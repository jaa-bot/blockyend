package com.blocky.blockyend.security.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blocky.blockyend.controller.LogController;
import com.blocky.blockyend.dto.LogDto;
import com.blocky.blockyend.dto.Mensaje;
import com.blocky.blockyend.security.dto.JwtDto;
import com.blocky.blockyend.security.dto.LoginUsuario;
import com.blocky.blockyend.security.dto.UsuarioDto;
import com.blocky.blockyend.security.entity.Rol;
import com.blocky.blockyend.security.entity.Usuario;
import com.blocky.blockyend.security.enums.RolNombre;
import com.blocky.blockyend.security.jwt.JwtProvider;
import com.blocky.blockyend.security.service.RolService;
import com.blocky.blockyend.security.service.UsuarioServiceSecurity;

@RestController @RequestMapping("/auth") @CrossOrigin
public class AuthController
{

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioServiceSecurity usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    LogController logController;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody UsuarioDto nuevoUsuario)
    {

        String expresionRegular = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

        Pattern pattern = Pattern.compile(expresionRegular);
        Matcher matcher = pattern.matcher(nuevoUsuario.getPassword());

        if (!matcher.matches())
        {
            return new ResponseEntity<>(new Mensaje(
                    "Formato contraseña mal introducido, minimo 8 caracteres, debe contener mayuscula y minuscula, minimo un digito"),
                    HttpStatus.BAD_REQUEST);

        }

        String expresionRegularEmail = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

        Pattern pattern2 = Pattern.compile(expresionRegularEmail);
        Matcher matcher2 = pattern2.matcher(nuevoUsuario.getPassword());

        if (!matcher2.matches())
        {
            return new ResponseEntity<>(new Mensaje("Formato email mal introducido"), HttpStatus.BAD_REQUEST);
        }

        if (nuevoUsuario.getNombreUsuario().length() < 6)
            return new ResponseEntity<>(new Mensaje("El nombre de usuario debe tener minimo 6 caracteres"), HttpStatus.BAD_REQUEST);

        if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity<>(new Mensaje("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        if (usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity<>(new Mensaje("Ya hay una cuenta registrada con ese email"),
                    HttpStatus.BAD_REQUEST);
        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(),
                nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if (nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);

        logController.create(
                new LogDto(usuario.getId(), "Nuevo usuario registrado. Nombre Usuario: " + usuario.getNombreUsuario()));

        return new ResponseEntity<>(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }
}
