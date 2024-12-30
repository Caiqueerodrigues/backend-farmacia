package curso.remedios.controllers;

import curso.remedios.DTO.ResponseDto;
import curso.remedios.remedio.*;
import curso.remedios.remedio.DTO.DadosAlterarRemedio;
import curso.remedios.remedio.DTO.DadosCadastroRemedio;
import curso.remedios.remedio.DTO.DadosDetalhamentoRemedio;
import curso.remedios.remedio.DTO.DadosListagemRemedio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/remedios")
public class RemedioController {

    @Autowired // instanciando o repository, injeção de dependencias
    private RemedioRepository repository;
    private ResponseDto response;

    @PostMapping
    @Transactional // faz o rollback caso não de certo
    public ResponseEntity<ResponseDto> cadastrar(@RequestBody @Valid DadosCadastroRemedio dados, UriComponentsBuilder uriBuilder) {
        var remedio = new Remedio(dados); //para poder pegar o id do remedio cadastrado
        repository.save(remedio);
        
        var resposta = new ResponseDto(remedio, "", "Remédio cadastrado com sucesso!", "");
        var uri =  uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();
        //pegar a url para enviar na resposta, PADRÃO DESING REST
        return ResponseEntity.created(uri).body(resposta);
    }

    @GetMapping
    public ResponseEntity<Object> listar() {
        var list = repository.findAllByAtivoTrue()//retornaria como a entidade, não é ideal
            .stream() //para converter
            .map(DadosListagemRemedio::new) // transformar em DTO de listagem
            .toList(); //transformar em lista como manda a func
        var resposta = new ResponseDto(list, "", "", "");
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable Long id) {
        var remedio = repository.findById(id);
        if(remedio.isEmpty()) throw new jakarta.persistence.EntityNotFoundException();
        
        var resposta = new ResponseDto(remedio, "", "", "");
        return ResponseEntity.ok().body(resposta);
    }
    
    @PutMapping
    @Transactional // faz o rollback caso não de certo
    public ResponseEntity<ResponseDto> atualizar(@RequestBody @Valid DadosAlterarRemedio dados) {
        var remedio = repository.getReferenceById(dados.id());
         //metódo do repository que retorna uma referencia do objeto pelo id
        remedio.atualizarInformacoes(dados); //atualizar os dados da entidade
        
        var remedioAlterado = new DadosDetalhamentoRemedio(remedio);
        var resposta = new ResponseDto(remedioAlterado, "", "Remédio alterado com sucesso!", "");
        return ResponseEntity.ok(resposta);
    }

    //Deletar Físico
    @DeleteMapping("/{id}") // atributo dinamico, após a rota existente já
    @Transactional // faz o rollback caso não de certo
    public void deletar(@PathVariable Long id) {
        //@PathVariable é para pegar o id da url
        repository.deleteById(id);
    }

    //Deletar Lógico
    @PutMapping("ativo/{status}/{id}")
    @Transactional // faz o rollback caso não de certo
    public ResponseEntity<ResponseDto> inativar(@PathVariable Boolean status, @PathVariable Long id) {
        //@PathVariable é para pegar o id da url
        var remedio = repository.getReferenceById(id);
         //metódo do repository que retorna uma referencia do objeto pelo id
        remedio.setAtivo(status); //Atualizando status
        
        var remedioAlterado = new DadosDetalhamentoRemedio(remedio);
        var resposta = new ResponseDto(remedioAlterado, "", "Situação do remédio alterada com sucesso!", "");
        return ResponseEntity.ok(resposta);
    }
}
