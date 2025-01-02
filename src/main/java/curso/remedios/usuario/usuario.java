package curso.remedios.usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import curso.remedios.remedio.DTO.DadosAlterarRemedio;
import curso.remedios.remedio.DTO.DadosCadastroRemedio;
import curso.remedios.usuario.DTO.DadosCompletosUsuario;
import curso.remedios.usuario.DTO.DadosUser;
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
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    private Boolean ativo;

    public Usuario(DadosUser dados) {
        this.login = dados.login();
        this.senha = dados.senha();
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosCompletosUsuario dados) {
        if(dados.login() != null) this.login = dados.login();
        if(dados.senha() != null) this.senha = dados.senha();
        if(dados.ativo() != null) this.ativo = dados.ativo();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //controle de acesso por perfis
        return List.of(new SimpleGrantedAuthority(("ROLE_USER")));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    
}
