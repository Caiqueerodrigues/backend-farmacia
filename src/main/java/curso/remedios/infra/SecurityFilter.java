package curso.remedios.infra;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import curso.remedios.DTO.ResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//criado o filter, similar ao middleware do express
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        var subject = tokenService.getSubject(tokenJWT); //pegar o subject do token
        
        filterChain.doFilter(request, response); //chamar o proximo filtro, similar ao next() do express
    }
        
    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization"); //pegar o authorization do header
        
        if(authorization == null || authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
            var response = new ResponseDto("", "Token inválido!", "", "");
            throw new RuntimeException("Token inválido!");
        }
        return authorization;
    }
}
