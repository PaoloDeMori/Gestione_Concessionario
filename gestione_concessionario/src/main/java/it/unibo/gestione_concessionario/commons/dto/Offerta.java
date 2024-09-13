package it.unibo.gestione_concessionario.commons.dto;
import java.time.LocalDate;
public record Offerta(
    int idOfferta,
    int percentuale,
    LocalDate dataInizio,
    LocalDate dataFine,
    int ID_marchio,
    int ID_Dipendente
) {

}
