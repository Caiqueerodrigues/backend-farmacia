package curso.remedios.remedio;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroRemedio(

        @NotBlank               // substitui o de cima e não aceita espaço em branco
        String nome,
        @Enumerated             //enum
        Via via,
        @NotBlank
        String lote,
        @NotNull
        int quantidade,
        @Future                 //não aceita data futura
        LocalDate validade,     // tipo de date JAVA
        @Enumerated
        Laboratorio laboratorio) {
}
