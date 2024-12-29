package curso.remedios.DTO;

public record ResponseDto(
    Object resposta,
    String msgErro,
    String msgSucesso,
    String msgAlerta
) {
}
