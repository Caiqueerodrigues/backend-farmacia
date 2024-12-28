package curso.remedios.remedio;

import org.springframework.data.jpa.repository.JpaRepository;

//REPOSITORY
public interface RemedioRepository extends JpaRepository<Remedio, Long> {
    //Remedio é o objeto que será mapeado e o TIPO da chave primária desse obj

}
