package curso.remedios.controllers;

import curso.remedios.remedio.DadosCadastroRemedio;
import curso.remedios.remedio.DadosListagemRemedio;
import curso.remedios.remedio.Remedio;
import curso.remedios.remedio.RemedioRepository;
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
}
