package curso.remedios.controllers;

import curso.remedios.remedio.*;
import curso.remedios.remedio.DTO.DadosAlterarRemedio;
import curso.remedios.remedio.DTO.DadosCadastroRemedio;
import curso.remedios.remedio.DTO.DadosListagemRemedio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remedios")
public class remedioController {

    @Autowired // instanciando o repository, injeção de dependencias
    private RemedioRepository repository;

    @PostMapping
    @Transactional // faz o rollback caso não de certo
    public void cadastrar(@RequestBody @Valid DadosCadastroRemedio dados) {

        repository.save(new Remedio(dados));
    }

    @GetMapping
    public List<DadosListagemRemedio> listar() {
        return repository.findAll()//retornaria como a entidade, não é ideal
            .stream() //para converter
            .map(DadosListagemRemedio::new) // transformar em DTO de listagem
            .toList(); //transformar em lista como manda a func
    }

    @PutMapping
    @Transactional // faz o rollback caso não de certo
    public void atualizar(@RequestBody @Valid DadosAlterarRemedio dados) {
        var remedio = repository.getReferenceById(dados.id());
         //metódo do repository que retorna uma referencia do objeto pelo id
        remedio.atualizarInformacoes(dados); //atualizar os dados da entidade
    }

    @DeleteMapping("/{id}") // atributo dinamico, após a rota existente já
    @Transactional // faz o rollback caso não de certo
    public void deletar(@PathVariable Long id) {
        //@PathVariable é para pegar o id da url
        repository.deleteById(id);
    }
}
