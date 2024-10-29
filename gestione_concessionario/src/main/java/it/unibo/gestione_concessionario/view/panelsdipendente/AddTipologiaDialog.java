package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Tipologia;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddTipologiaDialog extends JDialog {
    private JTextField caratteristicheField;
    private JTextField nomeField;
    private Controller controller;

    public AddTipologiaDialog(Controller controller,CreaModelloDialog creaModelloDialog) {
        super();
        this.controller = controller;
        setLayout(new GridLayout(7, 2, 5, 5));
        this.setTitle("Crea Tipologia");

        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("Caratteristiche:"));
        caratteristicheField = new JTextField();
        add(caratteristicheField);

        JButton salvTipologiaButton = new CustomButton("Salva Tipologia");
        salvTipologiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvaTipologia();
                creaModelloDialog.updateTipologie();
            }
        });
        add(new JLabel());
        add(salvTipologiaButton);
        setSize(400, 300);
    }

    private void salvaTipologia() {
        try {
            if (nomeField.getText().trim().isEmpty()||caratteristicheField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci tutti i campi obbligatori");
                return;
            }
            Tipologia tipologia = new Tipologia(
                    nomeField.getText(),
                    caratteristicheField.getText());
            try {
                controller.addTipologia(tipologia);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e, "Impossibile aggiungere Tipologia", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(this, "Tipologia salvata con successo");
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti " + ex.getMessage());
        }
    }

    public void start() {
        this.setVisible(true);
    }
}