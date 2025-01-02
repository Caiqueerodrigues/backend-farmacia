package curso.remedios.usuario.DTO;

import curso.remedios.usuario.Usuario;

public record ListagemUsuarios(
    Long id,
    String login,
    Boolean ativo
) {

    public ListagemUsuarios (Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getLogin(),
            usuario.getAtivo()
        );
    }
}
