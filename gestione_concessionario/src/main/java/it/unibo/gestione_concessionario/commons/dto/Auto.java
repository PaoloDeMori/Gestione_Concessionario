package it.unibo.gestione_concessionario.commons.dto;

import java.util.Optional;
import java.time.LocalDate;
public record Auto(
    String nuremo_telaio,
    Double prezzo,
    boolean immatricolazione,
    Optional<String> targa,
    Optional<LocalDate> data,
    //Configurazione configurazione
    String descrizioneModello,
    String motore,
    String alimentazione
) {

}
