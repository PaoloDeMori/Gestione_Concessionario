package it.unibo.gestione_concessionario.view.panelsDipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Offerta;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.time.LocalDate;

public class AddOffertaPanel extends JPanel {

    private JSpinner spData;
    private JSpinner spData2;
    private JSpinner spNumero;
    private JLabel modello;
    private Controller controller;
    CustomButton saveOfferta;

    public AddOffertaPanel(Controller controller) {
        setLayout(new GridLayout(9, 2, 5, 5)); // 8 righe, 2 colonne con spazi di 5px tra le celle
        this.controller = controller;
        add(new JLabel("Data Inizio:"));
        spData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData.setEditor(dateEditor);
        add(spData);

        add(new JLabel("Data Fine:"));
        spData2 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData2.setEditor(dateEditor2);
        add(spData2);

        add(new JLabel("Percentuale (1-100):")); // Etichetta per il JSpinner
        spNumero = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // JSpinner per il numero da 1 a 100
        add(spNumero);

        add(new JLabel("Marchio:"));
        modello = new JLabel(this.controller.getUserMarchio().nome());

        add(modello);

        saveOfferta = new CustomButton("Inserisci");
        this.add(saveOfferta);

        saveOfferta.addActionListener(e -> {
            try {
                Offerta offerta = this.getOfferta();
                controller.addOfferta(offerta);
                JOptionPane.showMessageDialog(this, "Offerta creata:\n" + offerta);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nella creazione dell' Offerta: " + ex.getMessage());
            }
        });

    }

    // Metodo per ottenere i dati dall'interfaccia
    public Offerta getOfferta() {
        LocalDate dataInizio = ((java.util.Date) spData.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        LocalDate dataFine = ((java.util.Date) spData2.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        int percentuale = ((int) spNumero.getValue());

        return new Offerta(
                percentuale,
                dataInizio,
                dataFine,
                this.controller.getUserMarchio().idMarchio(),
                this.controller.id_DipendenteByEmail(this.controller.getDipendenteUser().eMail())
                );
    }

}