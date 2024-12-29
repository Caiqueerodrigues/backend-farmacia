package curso.remedios.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "usuario")
@Entity(name = "usuarios")
@Getter //geração pelo LOMBOOK das coisas do JAVA
@Setter //geração pelo LOMBOOK das coisas do JAVA
@AllArgsConstructor //geração pelo LOMBOOK das coisas do JAVA
@NoArgsConstructor //geração pelo LOMBOOK das coisas do JAVA
@EqualsAndHashCode(of = "id") //geração pelo LOMBOOK das coisas do JAVA
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
}
