package curso.remedios.infra;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import curso.remedios.DTO.ResponseDto;
import curso.remedios.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//criado o filter, similar ao middleware do express
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        if(tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT); //pegar o subject do token
            var usuario = repository.findByLogin(subject); //buscar o usuario pelo login para dizer que esta logado

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); 
            //criar obj autenticação

            SecurityContextHolder.getContext().setAuthentication(authentication); //setar o usuario como autenticado
        }
        
        filterChain.doFilter(request, response); //chamar o proximo filtro, similar ao next() do express
    }
        
    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization"); //pegar o authorization do header
        
        if(authorization != null) {
            return authorization;
        }
        
        return null;
    }
}
