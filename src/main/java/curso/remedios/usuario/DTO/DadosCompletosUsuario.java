package curso.remedios.usuario.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCompletosUsuario(
    @NotNull
    Long id,

    @NotBlank
    String login,

    String senha

) {

}