package it.unibo.gestione_concessionario.dto;
import java.time.LocalDate;
public record Sconto(
    int idSconto,
    double percentuale,
    LocalDate dataInizio,
    LocalDate dataFine,
    Auto nuremo_telaio,
    Dipendente dipendente

) {

}