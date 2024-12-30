package curso.remedios.usuario.DTO;

import jakarta.validation.constraints.NotBlank;

public record DadosUser(
    @NotBlank
    String login,

    @NotBlank
    String senha
) {

}
