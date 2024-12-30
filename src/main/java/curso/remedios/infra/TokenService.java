package curso.remedios.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import curso.remedios.usuario.Usuario;

@Service
public class TokenService {

    public String generateToken(Usuario usuario) {
        try {
            var algorithm = Algorithm.HMAC256("123456");
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
}