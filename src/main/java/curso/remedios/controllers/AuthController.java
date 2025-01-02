package curso.remedios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.remedios.DTO.DadosTokenJWT;
import curso.remedios.DTO.ResponseDto;
import curso.remedios.infra.TokenService;
import curso.remedios.usuario.Usuario;
import curso.remedios.usuario.DTO.DadosUser;
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
    public ResponseEntity<?> login(@RequestBody @Valid DadosUser dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        //tem que ser nesse DTO do próprio spring
        var auth = manager.authenticate(token);

        Usuario usuario = (Usuario) auth.getPrincipal();
        if (!usuario.getAtivo()) {
            var response = new ResponseDto("", "Usuário inativo, procure a administração!", "", "");
            return ResponseEntity.status(403).body(response);
        }

        // Gerando o token JWT para o usuário autenticado
        var tokenJWT = tokenService.generateToken(usuario);
        return ResponseEntity.ok().body(new DadosTokenJWT(tokenJWT));
    }
}
