package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Contratto;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Optional;

public class ContrattoDialog extends JDialog {
    private Contratto contratto;
    private JTextField prezzoField;
    private JComboBox<String> tipologiaBox;
    private JLabel nomeBancaLabel = new JLabel("Nome Banca (opzionale):");
    private JTextField nomeBancaField;
    private JLabel codiceFinanziamentoLabel = new JLabel("Codice Finanziamento (opzionale):");
    private JTextField codiceFinanziamentoField;
    private JLabel intestatarioLabel = new JLabel("Intestatario (opzionale):");
    private JTextField intestatarioField;
    private JLabel metodoDiPagamentoLabel = new JLabel("Metodo di Pagamento (opzionale):");
    private JTextField metodoDiPagamentoField;
    private boolean isConfirmed = false;
    private Controller controller;

    public ContrattoDialog(Controller controller, AddVenditaPanel addVenditaPanel) {
        super();
        this.controller = controller;
        setLayout(new GridLayout(7, 2, 5, 5)); 

        this.setTitle("Crea Contratto");
        add(new JLabel("Prezzo:"));
        prezzoField = new JTextField();
        add(prezzoField);

        add(new JLabel("Tipologia:"));
        String[] options = { "Finanziamento", "Unica Rata" };
        tipologiaBox = new JComboBox<>(options);
        tipologiaBox.addActionListener(e -> updateAvailableFields());
        add(tipologiaBox);

        add(nomeBancaLabel);
        nomeBancaField = new JTextField();
        add(nomeBancaField);

        add(codiceFinanziamentoLabel);
        codiceFinanziamentoField = new JTextField();
        add(codiceFinanziamentoField);

        add(intestatarioLabel);
        intestatarioField = new JTextField();
        add(intestatarioField);

        add(metodoDiPagamentoLabel);
        metodoDiPagamentoField = new JTextField();
        add(metodoDiPagamentoField);

        JButton salvaContrattoButton = new CustomButton("Salva Contratto");
        salvaContrattoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvaContratto();
                if (isConfirmed) {
                    addVenditaPanel.addContratto(getContratto());
                    JOptionPane.showMessageDialog(addVenditaPanel,
                            "Contratto inserito con successo: " + contratto.toString());
                } else {
                    JOptionPane.showMessageDialog(addVenditaPanel, "Contratto non valido");
                }
            }
        });
        add(new JLabel());
        add(salvaContrattoButton);
        updateAvailableFields();

        setSize(400, 300);
    }

    private void salvaContratto() {
        try {
            double prezzo = Double.parseDouble(prezzoField.getText());
            String tipologia = (String) tipologiaBox.getSelectedItem();

            Optional<String> nomeBanca = nomeBancaField.getText().isEmpty() ? Optional.empty()
                    : Optional.of(nomeBancaField.getText());
            Optional<String> codiceFinanziamento = codiceFinanziamentoField.getText().isEmpty() ? Optional.empty()
                    : Optional.of(codiceFinanziamentoField.getText());
            Optional<String> intestatario = intestatarioField.getText().isEmpty() ? Optional.empty()
                    : Optional.of(intestatarioField.getText());
            Optional<String> metodoDiPagamento = metodoDiPagamentoField.getText().isEmpty() ? Optional.empty()
                    : Optional.of(metodoDiPagamentoField.getText());
            if (tipologia.equals("Finanziamento")) {
                if(nomeBanca.isEmpty()||codiceFinanziamento.isEmpty()||intestatario.isEmpty()){
                    throw new IllegalArgumentException("Configurazione del contratto non valida");
                }
            }
            else if(tipologia.equals("Unica Rata")){
                if(metodoDiPagamento.isEmpty()){
                    throw new IllegalArgumentException("Configurazione del contratto non valida");
                }
            }
            contratto = new Contratto(
                    prezzo,
                    tipologia,
                    nomeBanca,
                    codiceFinanziamento,
                    intestatario,
                    metodoDiPagamento);

            int idContratto;
            try {
                idContratto = controller.addContratto(contratto);
                if (idContratto > 0) {
                    contratto.setIdContratto(idContratto);
                }    
            } catch (SQLException e) {
               JOptionPane.showMessageDialog(this, e.getMessage(), "errore nella creazione del contratto", JOptionPane.ERROR_MESSAGE);
            }
            isConfirmed = true;
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti: il prezzo deve essere un numero valido.");
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti " + ex.getMessage());
        }
    }

    public Contratto getContratto() {
        return contratto;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void start() {
        this.setVisible(true);
    }

    public void updateAvailableFields() {
        if (((String) tipologiaBox.getSelectedItem()).equals("Finanziamento")) {
            nomeBancaField.setVisible(true);
            codiceFinanziamentoField.setVisible(true);
            intestatarioField.setVisible(true);
            metodoDiPagamentoField.setVisible(false);
        } else if (((String) tipologiaBox.getSelectedItem()).equals("Unica Rata")) {
            nomeBancaField.setVisible(false);
            codiceFinanziamentoField.setVisible(false);
            intestatarioField.setVisible(false);
            metodoDiPagamentoField.setVisible(true);
        }
    }
}