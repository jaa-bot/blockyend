package com.blocky.blockyend.controller;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.security.Security;

import com.blocky.blockyend.dto.TokenDto;
import com.blocky.blockyend.entity.Token;
import com.blocky.blockyend.security.entity.Usuario;
import com.blocky.blockyend.service.TokenService;
import com.blocky.blockyend.service.UsuarioService;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.Subscription.Keys;

@RestController
@RequestMapping("/token")
@CrossOrigin(origins = "*")
public class TokenController {

    @Value("${vapid.public.key}")
    private String publicKey;
    
    @Value("${vapid.private.key}")
    private String privateKey;
    
    @Value("${vapid.subject}")
    private String subject;

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioService usuarioService;

    private PushService pushService;

    private final List<Token> endpointToSubscription = new ArrayList<>();

    
    @GetMapping("/lista")
    public List<Token> list() {
        List<Token> list = tokenService.list();
        return list;
    }

    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        // publicKey =
        // Base64.getEncoder().encodeToString(publicKey.getBytes(StandardCharsets.UTF_8));
        // privateKey =
        // Base64.getEncoder().encodeToString(privateKey.getBytes(StandardCharsets.UTF_8));
        pushService = new PushService(publicKey, privateKey, subject);
        this.endpointToSubscription.addAll(list());
    }

    public String getPublicKey() {
        return publicKey;
    }

    @PostMapping("/nuevoToken")
    public ResponseEntity<?> añadirToken(@RequestBody TokenDto tokenDto) {

        System.out.println("AAAAAAAAAAAAAAAA   " + tokenDto.getUserId());

        Optional<Usuario> optionalUsuario = usuarioService.getOne(tokenDto.getUserId());

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            Token newToken = new Token(tokenDto.getEndpoint(), tokenDto.getAuth(), tokenDto.getP256dh(), usuario);

            tokenService.save(newToken);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarToken/{endpoint}")
    public ResponseEntity<?> eliminarToken(@PathVariable("endpoint") String endpoint) {
        tokenService.delete(endpoint);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/enviarNotificacion/{userId}")
    public ResponseEntity<?> enviarNotificacion(@PathVariable("userId") int userId) {
        List<Token> tokens = tokenService.list();

        for (Token token : tokens) {
            try {

                Keys keys = new Keys(token.getP256dh(), token.getAuth());
                Subscription subs = new Subscription(token.getEndpoint(), keys);

                Notification notification = new Notification(subs, "holaa");

                HttpResponse response = pushService.send(notification);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != 201) {
                    System.out.println("Error del servidor, codigo del error:" + statusCode);
                    InputStream content = response.getEntity().getContent();
                    List<String> strings = IOUtils.readLines(content, "UTF-8");
                    System.out.println(strings);
                }

            } catch (Exception e) {
                // Maneja las excepciones adecuadamente
            }
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public void unsubscribe(Token subscription) {

        System.out.println("-------------------------------------------------");
        System.out.println("ID DEL UNSUBCRIBE: " + subscription.getEndpoint());
        ;

        eliminarToken(subscription.getEndpoint());

        this.endpointToSubscription.clear();
        this.endpointToSubscription.addAll(list());
        System.out.println("-------------------------------------------------");
        for (int i = 0; i < this.endpointToSubscription.size(); i++) {
            System.out.println(this.endpointToSubscription.get(i).getEndpoint());
        }
    }

    public record Message(String title, String body) {
    }

    @GetMapping("/detailUser/{id_user}")
    public Optional<Token> cogerId_user(@PathVariable("id_user") String id) {
        return tokenService.getOne(id);
    }
}
