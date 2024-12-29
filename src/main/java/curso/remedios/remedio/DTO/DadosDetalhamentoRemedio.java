package curso.remedios.remedio.DTO;

import java.time.LocalDate;

import curso.remedios.remedio.Enums.Laboratorio;
import curso.remedios.remedio.Enums.Via;

public record DadosDetalhamentoRemedio(
    Long id,
    String nome,
    Laboratorio laboratorio,
    Via via,
    int quantidade,
    LocalDate validade,
    String lote,
    Boolean ativo
) {

    public DadosDetalhamentoRemedio(curso.remedios.remedio.Remedio remedio) {
        this(
            remedio.getId(), 
            remedio.getNome(), 
            remedio.getLaboratorio(), 
            remedio.getVia(), 
            remedio.getQuantidade(), 
            remedio.getValidade(), 
            remedio.getLote(), 
            remedio.getAtivo()
        );
    }

}
