package it.unibo.gestione_concessionario.commons.dto;
import java.time.LocalDate;
public record Sconto(
    int percentuale,
    LocalDate dataInizio,
    LocalDate dataFine,
    String nuremo_telaio
) {

}
