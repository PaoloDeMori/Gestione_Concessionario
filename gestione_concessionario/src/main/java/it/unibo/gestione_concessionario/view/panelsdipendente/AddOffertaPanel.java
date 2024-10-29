package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Offerta;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;
import it.unibo.gestione_concessionario.view.View;

import java.awt.*;
import java.time.LocalDate;

public class AddOffertaPanel extends JPanel {

    private JPanel mainPanel;
    private JSpinner spData;
    private JSpinner spData2;
    private JSpinner spNumero;
    private JLabel modello;
    private Controller controller;
    CustomButton saveOfferta;

    public AddOffertaPanel(Controller controller) {
        setLayout(new BorderLayout());
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Aggiungi Un Offerta");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);

        mainPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        this.controller = controller;
        mainPanel.add(new JLabel("Data Inizio:"));
        spData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData.setEditor(dateEditor);
        mainPanel.add(spData);

        mainPanel.add(new JLabel("Data Fine:"));
        spData2 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData2.setEditor(dateEditor2);
        mainPanel.add(spData2);

        mainPanel.add(new JLabel("Percentuale (1-100):"));
        spNumero = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        mainPanel.add(spNumero);

        mainPanel.add(new JLabel("Marchio:"));
        modello = new JLabel(this.controller.getUserMarchio().nome());

        mainPanel.add(modello);

        saveOfferta = new CustomButton("Inserisci");
        mainPanel.add(saveOfferta);

        saveOfferta.addActionListener(e -> {
            try {
                Offerta offerta = this.getOfferta();
                controller.addOfferta(offerta);
                JOptionPane.showMessageDialog(this, "Offerta creata:\n" + offerta);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nella creazione dell' Offerta: " + ex.getMessage());
            }
        });
        this.add(mainPanel, BorderLayout.CENTER);

    }

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
                this.controller.id_DipendenteByEmail(this.controller.getDipendenteUser().eMail()));
    }

}