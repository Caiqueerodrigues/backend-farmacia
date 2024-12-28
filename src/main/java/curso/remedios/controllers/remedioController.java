package curso.remedios.controllers;

import curso.remedios.remedio.DadosCadastroRemedio;
import curso.remedios.remedio.Remedio;
import curso.remedios.remedio.RemedioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remedios")
public class remedioController {

    @Autowired // instanciando o repository, injeção de dependencias
    private RemedioRepository repository;
    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroRemedio dados) {

        repository.save(new Remedio(dados));
    }
}
