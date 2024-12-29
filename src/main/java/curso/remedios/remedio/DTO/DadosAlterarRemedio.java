package curso.remedios.remedio.DTO;

import curso.remedios.remedio.Enums.Laboratorio;
import curso.remedios.remedio.Enums.Via;
import jakarta.validation.constraints.NotNull;

public record DadosAlterarRemedio(
        //Aqui vai os itens que poderão ser alterados
        //tambem deve setar qual pode ou não ser null
        @NotNull
        Long id,
        String nome,
        Via via,
        Laboratorio laboratorio) {
}
