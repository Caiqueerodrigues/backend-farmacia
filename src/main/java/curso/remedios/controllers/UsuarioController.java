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
import curso.remedios.usuario.DTO.DadosCompletosUsuario;
import curso.remedios.usuario.DTO.DadosUser;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseDto> buscar(@PathVariable Long id) {
        Usuario usuario = (Usuario) repository.getById(id);
        
        var usuarioResponse = new DadosCompletosUsuario(usuario.getId(), usuario.getLogin(), "", usuario.getAtivo());
        var resposta = new ResponseDto(usuarioResponse, "", "", "");
        return ResponseEntity.ok().body(resposta);
    }

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

    @PutMapping
    @Transactional
    public ResponseEntity<ResponseDto> atualizar(@RequestBody @Valid DadosCompletosUsuario dados) {
        
        Usuario usuario = (Usuario) repository.getReferenceById(dados.id());

        if(usuario.getLogin() == null) {
            var resposta = new ResponseDto("", "Usuário inexistente!", "", "");
            return ResponseEntity.status(404).body(resposta);
        };

        Usuario usuarioExiste = (Usuario) repository.findByLogin(dados.login());

        if(usuarioExiste == null || usuarioExiste.getId() == usuario.getId()) {
        
            String encryptedPassword = passwordEncoder.encode(dados.senha());
            DadosCompletosUsuario dadosUserCriptografado = new DadosCompletosUsuario(dados.id(), dados.login(), encryptedPassword, dados.ativo());
            usuario.atualizarInformacoes(dadosUserCriptografado);
            repository.save(usuario);
            var resposta = new ResponseDto("", "", "Usuário atualizado com sucesso!", "");
            return ResponseEntity.ok().body(resposta);
        } else {
            var resposta = new ResponseDto("", "Usuário já cadastrado!", "", "");
            return ResponseEntity.ok().body(resposta);
        }
    }
}
