package curso.remedios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.remedios.infra.TokenService;
import curso.remedios.usuario.Usuario;
import curso.remedios.usuario.DTO.DadosAuth;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid DadosAuth dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        //tem que ser nesse DTO do próprio spring
        var auth = manager.authenticate(token);

        return ResponseEntity.ok().body(tokenService.generateToken((Usuario) auth.getPrincipal()));
    }
}