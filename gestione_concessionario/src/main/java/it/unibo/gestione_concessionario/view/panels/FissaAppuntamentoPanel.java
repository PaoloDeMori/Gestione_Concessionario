package it.unibo.gestione_concessionario.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.controller.Controller;

public class FissaAppuntamentoPanel extends JPanel {
    
    private JTextField idAppuntamentoField;
    private JTextField dataField;
    private JTextField oraField;
    private JTextField tipologiaField;
    private JTextField durataField;
    private JTextField numeroTelaioField;
    private JTextField nomeDipendenteField;
    private JTextField nomeClienteField;
    private JButton confermaButton;

    public FissaAppuntamentoPanel(Controller controller) {
        setLayout(new GridLayout(9, 2));

        // Create labels and text fields
        JLabel idAppuntamentoLabel = new JLabel("ID Appuntamento:");
        idAppuntamentoField = new JTextField();

        JLabel dataLabel = new JLabel("Data (yyyy-mm-dd):");
        dataField = new JTextField();

        JLabel oraLabel = new JLabel("Ora (HH:mm):");
        oraField = new JTextField();

        JLabel tipologiaLabel = new JLabel("Tipologia:");
        tipologiaField = new JTextField();

        JLabel durataLabel = new JLabel("Durata (HH:mm):");
        durataField = new JTextField();

        JLabel numeroTelaioLabel = new JLabel("Numero Telaio:");
        numeroTelaioField = new JTextField();

        JLabel nomeDipendenteLabel = new JLabel("Nome Dipendente:");
        nomeDipendenteField = new JTextField();

        JLabel nomeClienteLabel = new JLabel("Nome Cliente:");
        nomeClienteField = new JTextField();

        // Button to confirm and schedule the appointment
        confermaButton = new JButton("Fissa Appuntamento");

        // Add components to the panel
        add(idAppuntamentoLabel);
        add(idAppuntamentoField);
        add(dataLabel);
        add(dataField);
        add(oraLabel);
        add(oraField);
        add(tipologiaLabel);
        add(tipologiaField);
        add(durataLabel);
        add(durataField);
        add(numeroTelaioLabel);
        add(numeroTelaioField);
        add(nomeDipendenteLabel);
        add(nomeDipendenteField);
        add(nomeClienteLabel);
        add(nomeClienteField);
        add(new JLabel());  // Empty cell
        add(confermaButton);

        // Action listener for the confirm button
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idAppuntamento = Integer.parseInt(idAppuntamentoField.getText());
                    LocalDate data = LocalDate.parse(dataField.getText());
                    LocalTime ora = LocalTime.parse(oraField.getText());
                    String tipologia = tipologiaField.getText();
                    LocalTime durata = LocalTime.parse(durataField.getText());
                    String numeroTelaio = numeroTelaioField.getText();
                    String nomeDipendente = nomeDipendenteField.getText();
                    String nomeCliente = nomeClienteField.getText();

                    // Create the Appuntamento object
                    Appuntamento appuntamento = new Appuntamento(
                        idAppuntamento, data, ora, tipologia, durata, numeroTelaio, nomeDipendente, nomeCliente
                    );

                    // Call controller to save the appointment
                    boolean success = controller.fissaAppuntamento(appuntamento);
                    if (success) {
                        JOptionPane.showMessageDialog(FissaAppuntamentoPanel.this, "Appuntamento fissato con successo!");
                    } else {
                        JOptionPane.showMessageDialog(FissaAppuntamentoPanel.this, "Errore durante la creazione dell'appuntamento.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FissaAppuntamentoPanel.this, "Errore nei dati inseriti: " + ex.getMessage());
                }
            }
        });
    }
}
