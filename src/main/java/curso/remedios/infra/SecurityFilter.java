package curso.remedios.infra;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//criado o filter, similar ao middleware do express
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);
        
        filterChain.doFilter(request, response); //chamar o proximo filtro, similar ao next() do express
    }
        
        private String recuperarToken(HttpServletRequest request) {
            var authorization = request.getHeader("Authorization"); //pegar o authorization do header
            
            if(authorization == null || authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
                throw new RuntimeException("Token inválido!");
            }
            return authorization;
        }

}
