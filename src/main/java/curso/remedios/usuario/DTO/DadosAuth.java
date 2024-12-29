package curso.remedios.usuario.DTO;

import jakarta.validation.constraints.NotBlank;

public record DadosAuth(
    @NotBlank
    String login,

    @NotBlank
    String senha
) {

}
