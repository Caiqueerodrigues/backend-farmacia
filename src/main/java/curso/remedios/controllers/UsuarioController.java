package curso.remedios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import curso.remedios.DTO.ResponseDto;
import curso.remedios.usuario.Usuario;
import curso.remedios.usuario.UsuarioRepository;
import curso.remedios.usuario.DTO.DadosUser;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional // faz o rollback caso não de certo
    public ResponseEntity<ResponseDto> cadastrar(@RequestBody @Valid DadosUser dados, UriComponentsBuilder uriBuilder) {
        // Buscar o usuário pelo login
        Usuario usuario = (Usuario) repository.findByLogin(dados.login());
        
        if (usuario != null) {
            var response = new ResponseDto("", "Usuário já cadastrado", "", "");
            return ResponseEntity.status(400).body(response);
        }
        
        String encryptedPassword = passwordEncoder.encode(dados.senha()); // Criptografa a senha

        DadosUser dadosUserCriptografado = new DadosUser(dados.login(), encryptedPassword); 
        // para criptografar a senha no novo obj de usuario
        var user = new Usuario(dadosUserCriptografado); //para poder pegar o id usuario cadastrado
        repository.save(user);
        
        var resposta = new ResponseDto("", "", "Usuário cadastrado com sucesso!", "");
        var uri =  uriBuilder.path("/usuarios/{id}").buildAndExpand(user.getId()).toUri();
        //pegar a url para enviar na resposta, PADRÃO DESING REST
        return ResponseEntity.created(uri).body(resposta);
    }
}
