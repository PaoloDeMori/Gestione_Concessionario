package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Garanzia;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddGaranziaDialog extends JDialog {
    private JTextField coperturaField;
    private JSpinner spData;
    private Controller controller;
    private String numeroTelaio;

    public AddGaranziaDialog(Controller controller, String numeroTelaio) {
        super();
        this.controller = controller;
        setLayout(new GridLayout(7, 2, 5, 5)); 
        this.numeroTelaio = numeroTelaio;
        this.setTitle("Crea Garanzia");

        add(new JLabel("Copertura:"));
        coperturaField = new JTextField();
        add(coperturaField);

        add(new JLabel("Scadenza:"));
        spData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spData, "yyyy-MM-dd");
        spData.setEditor(dateEditor);
        add(spData);

        JButton salvaContrattoButton = new CustomButton("Salva Garanzia");
        salvaContrattoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvaGaranzia();
            }
        });
        add(new JLabel());
        add(salvaContrattoButton);
        setSize(400, 300);
    }

    private void salvaGaranzia() {
        try {
            LocalDate dataScadenza = ((java.util.Date) spData.getValue()).toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
            if (dataScadenza.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "La data di scadenza deve essere futura");
                return;
            }
            if (coperturaField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci tutti i campi obbligatori");
                return;
            }
            Garanzia garanzia = new Garanzia(
                    dataScadenza,
                    coperturaField.getText(),
                    numeroTelaio);
            try {
                controller.addGaranzia(garanzia);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e, "Impossibile aggiungere garanzia", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(this, "Garanzia salvata con successo");
            dispose(); 
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti " + ex.getMessage());
        }
    }

    public void start() {
        this.setVisible(true);
    }
}