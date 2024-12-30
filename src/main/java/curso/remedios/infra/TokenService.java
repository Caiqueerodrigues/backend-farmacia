package curso.remedios.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import curso.remedios.usuario.Usuario;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    //dizer que o valor da variavel vira do application.properties
    private String secret;

    public String generateToken(Usuario usuario) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer("remedios")
                .withSubject(usuario.getLogin())
                .withClaim("id", usuario.getId()) //pode ter varios
                .withClaim("usuario", usuario.getLogin()) //pode ter varios
                .withExpiresAt(dataExpiracao()) //expiração do token
                .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        //pega hora atual do BR, soma duas horas e converte para instant
    }

    public String getSubject(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("remedios")
                .build()
                .verify(token)
                .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido ou expirado!");
        }
    }
}
