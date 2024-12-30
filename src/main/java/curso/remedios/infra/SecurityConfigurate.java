package curso.remedios.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //indica ao spring que irá alterar a configuração de segurança
public class SecurityConfigurate {

    @Autowired
    private SecurityFilter securityFilter;

    //configurar a aplicação como STATELESS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> 
            authz
                .requestMatchers(HttpMethod.POST, "/login").permitAll() //para permitir seguir a req sem token
                .requestMatchers(HttpMethod.GET, 
                   "/swagger-ui/**",    // Acesso ao Swagger UI
                    "/v3/api-docs/**",   // Acesso à documentação da API
                    "/swagger-ui.html",  // Acesso à página principal do Swagger
                    "/swagger-ui/index.html" // Acesso ao Swagger UI
                ).permitAll() //para permitir seguir a req sem token
                .anyRequest().authenticated()) //todas as outras precisan do token
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) 
                //adicionar 1°nosso filtro, 2° o do Spring
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        //retorna o AuthenticationManager necessario para a aplicação não falhar 
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //retorna o PasswordEncoder necessario para a aplicação não falhar 
        return new BCryptPasswordEncoder();
    }
}
