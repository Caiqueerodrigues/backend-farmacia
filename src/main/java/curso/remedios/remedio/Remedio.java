package curso.remedios.remedio;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import curso.remedios.remedio.DTO.DadosAlterarRemedio;
import curso.remedios.remedio.DTO.DadosCadastroRemedio;
import curso.remedios.remedio.Enums.Laboratorio;
import curso.remedios.remedio.Enums.Via;

@Table(name = "remedio")
@Entity(name = "remedios")
@Getter //geração pelo LOMBOOK das coisas do JAVA
@Setter //geração pelo LOMBOOK das coisas do JAVA
@AllArgsConstructor //geração pelo LOMBOOK das coisas do JAVA
@NoArgsConstructor //geração pelo LOMBOOK das coisas do JAVA
@EqualsAndHashCode(of = "id") //geração pelo LOMBOOK das coisas do JAVA
public class Remedio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //GERAR O ID NO BANCO
    private Long id;
    private String nome;

    @Enumerated(EnumType.STRING) //sempre tem que ter quando ENUM
    private Via via;
    private String lote;
    private int quantidade;
    private LocalDate validade;
    private Boolean ativo;

    @Enumerated(EnumType.STRING) //sempre tem que ter quando ENUM
    private Laboratorio laboratorio;

    public Remedio(DadosCadastroRemedio dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.via = dados.via();
        this.lote = dados.lote();
        this.quantidade = dados.quantidade();
        this.validade = dados.validade();
        this.laboratorio = dados.laboratorio();
    }

    public void atualizarInformacoes(DadosAlterarRemedio dados) {
        if(dados.nome() != null) this.nome = dados.nome(); //validacao se o campo não é null
        if(dados.via() != null) this.via = dados.via();
        if(dados.laboratorio() != null) this.laboratorio = dados.laboratorio();
    }
}
