package it.unibo.gestione_concessionario.view.panelsDipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.dto.Contratto;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class ContrattoDialog extends JDialog {
    private Contratto contratto; // Contratto che verrà creato
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
        setLayout(new GridLayout(7, 2, 5, 5)); // Layout per il dialog

        this.setTitle("Crea Contratto");
        // Aggiungi i campi necessari per il contratto
        add(new JLabel("Prezzo:"));
        prezzoField = new JTextField();
        add(prezzoField);

        add(new JLabel("Tipologia:"));
        String[] options = { "Finanziamento", "Unica Rata" };
        tipologiaBox = new JComboBox<String>(options);
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

        // Pulsante per confermare il contratto
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
        add(new JLabel()); // Spazio vuoto per il layout
        add(salvaContrattoButton);
        updateAvailableFields();

        // Imposta la dimensione del dialog
        setSize(400, 300);
    }

    // Metodo per salvare il contratto
    private void salvaContratto() {
        try {
            // Validazione del prezzo
            double prezzo = Double.parseDouble(prezzoField.getText());
            String tipologia = (String) tipologiaBox.getSelectedItem();

            // Ottieni i valori opzionali
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
            // Creazione del contratto
            contratto = new Contratto(
                    prezzo,
                    tipologia,
                    nomeBanca,
                    codiceFinanziamento,
                    intestatario,
                    metodoDiPagamento);
            int idContratto = controller.addContratto(contratto);
            if (idContratto > 0) {
                contratto.setIdContratto(idContratto);
            }

            isConfirmed = true; // Imposta che il contratto è stato confermato
            dispose(); // Chiudi il dialog
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti: il prezzo deve essere un numero valido.");
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Errore nei dati inseriti " + ex.getMessage());
        }
    }

    // Metodo per ottenere il contratto creato
    public Contratto getContratto() {
        return contratto;
    }

    // Metodo per verificare se il contratto è stato confermato
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