package curso.remedios.infra;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Forbidden;

import curso.remedios.DTO.ResponseDto;
import curso.remedios.Exceptions.UsuarioInativoException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice //sempre que tiver uma excessao fará isso
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class) //especifico para 404 notFound
    public ResponseEntity<ResponseDto> tratador404() {
        var resposta = new ResponseDto("", "Recurso não encontrado!", "", "");
        return ResponseEntity.status(404).body(resposta);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //especifico para 400 bad request
    public ResponseEntity<?> tratador400(MethodArgumentNotValidException err) {
        var erros = err.getFieldErrors();
        var campos = erros.stream()
            .map(campo -> {
                FieldError fieldError = (FieldError) campo; // Cast para FieldError
                String errorMessage = fieldError.getField() + " " + fieldError.getDefaultMessage(); // Campo e mensagem de erro
                
                return Character.toUpperCase(errorMessage.charAt(0)) + errorMessage.substring(1); // Maiúscula na primeira letra
            })
            .collect(Collectors.joining(", ")); // Junta e concatena tudo com ","

        var resposta = new ResponseDto("", campos, "", "");
        return ResponseEntity.status(400).body(resposta);
    }

    @ExceptionHandler(UsuarioInativoException.class) //Criada para quando estiver inativo
    public ResponseEntity<ResponseDto> tratador403(UsuarioInativoException ex) {
        var resposta = new ResponseDto("", ex.getMessage(), "", "");
        return ResponseEntity.status(403).body(resposta);
    }
}
