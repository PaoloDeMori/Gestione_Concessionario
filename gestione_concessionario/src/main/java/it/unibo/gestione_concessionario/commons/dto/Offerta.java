package it.unibo.gestione_concessionario.commons.dto;
import java.time.LocalDate;
public record Offerta(
    int idOfferta,
    double percentuale,
    LocalDate dataInizio,
    LocalDate dataFine,
    Marchio marchio,
    Dipendente dipendente
) {

}
