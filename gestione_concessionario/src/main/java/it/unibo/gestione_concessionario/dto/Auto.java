package it.unibo.gestione_concessionario.dto;

import java.util.Optional;
import java.time.LocalDate;
public record Auto(
    int nuremo_telaio,
    int prezzo,
    Optional<String> immatricolazione,
    Optional<String> targa,
    Optional<LocalDate> data,
    Configurazione configurazione
) {

}
