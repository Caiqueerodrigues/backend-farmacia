package curso.remedios.remedio.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

import curso.remedios.remedio.Remedio;
import curso.remedios.remedio.Enums.Laboratorio;
import curso.remedios.remedio.Enums.Via;

public record DadosListagemRemedio(
    Long id,
    String nome,
    Via via,
    String lote,
    int quantidade,
    LocalDate validade,
    Laboratorio laboratorio
) {
    public DadosListagemRemedio (Remedio remedio) {
        this(
            remedio.getId(),
            remedio.getNome(),
            remedio.getVia(),
            remedio.getLote(),
            remedio.getQuantidade(),
            remedio.getValidade(),
            remedio.getLaboratorio()
        );
    }
}
