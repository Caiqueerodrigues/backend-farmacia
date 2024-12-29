package curso.remedios.remedio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//REPOSITORY
public interface RemedioRepository extends JpaRepository<Remedio, Long> {
    //Remedio é o objeto que será mapeado e o TIPO da chave primária desse obj

    List<Remedio> findAllByAtivoTrue();
    //quando colocado findAllBy, o spring já entende que é para buscar todos
    //seguido de atributo e da condição
}
