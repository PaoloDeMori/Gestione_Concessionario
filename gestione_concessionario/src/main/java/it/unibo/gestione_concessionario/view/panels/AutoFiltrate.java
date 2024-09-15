package it.unibo.gestione_concessionario.view.panels;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.commons.dto.Tipologia;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AutoFiltrate extends JPanel {

    private JComboBox<Marchio> marchioComboBox;
    private JComboBox<Tipologia> tipologiaComboBox;
    private JButton filtraButton;
    private JTable autoTable;
    private DefaultTableModel tableModel;
    private Controller controller;

    public AutoFiltrate(Controller controller) {
        this.controller = controller;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());

        // Pannello per selezione dei criteri di filtraggio
        JPanel filtroPanel = new JPanel(new GridLayout(3,2));

        // Inizializza ComboBox per Marchio e Tipologia
        marchioComboBox = new JComboBox<Marchio>(getMarchi().stream().toArray(Marchio[]::new));

        tipologiaComboBox = new JComboBox<Tipologia>(getTipologie().stream().toArray(Tipologia[]::new));

        // Aggiungi ComboBox al pannello
        filtroPanel.add(new JLabel("Seleziona Marchio:"));
        filtroPanel.add(marchioComboBox);

        filtroPanel.add(new JLabel("Seleziona Tipologia:"));
        filtroPanel.add(tipologiaComboBox);

        // Bottone per applicare il filtro
        filtraButton = new CustomButton("Filtra");
        filtroPanel.add(filtraButton);

        // Aggiungi il pannello di filtro nella parte superiore
        this.add(filtroPanel, BorderLayout.NORTH);

        // Inizializza la tabella per visualizzare le auto filtrate
        tableModel = new DefaultTableModel(new Object[]{"Numero Telaio", "Modello", "Prezzo", "Data Immatricolazione", "Targa"}, 0);
        autoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(autoTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Action Listener per il pulsante di filtraggio
        filtraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraAuto();
            }
        });
    }

    // Metodo per ottenere i marchi dal controller
    private List<Marchio> getMarchi() {
        return controller.allMarchi();
    }

    // Metodo per ottenere le tipologie dal controller
    private List<Tipologia> getTipologie() {
        return controller.allTipologie();
    }

    // Metodo per filtrare le auto in base ai criteri selezionati
    private void filtraAuto() {
        // Ottieni il marchio e la tipologia selezionati
        Marchio marchioSelezionato = (Marchio) marchioComboBox.getSelectedItem();
        Tipologia tipologiaSelezionata = (Tipologia) tipologiaComboBox.getSelectedItem();

        // Esegui la query di filtro tramite il controller
        List<Auto> autoFiltrate = controller.visualizzaAutoxMarchioxTipologia(marchioSelezionato, tipologiaSelezionata);

        // Rimuovi tutti i dati attuali dalla tabella
        tableModel.setRowCount(0);

        // Aggiungi i risultati filtrati nella tabella
        for (Auto auto : autoFiltrate) {
            tableModel.addRow(new Object[]{
                auto.numero_telaio(),
                auto.descrizioneModello(),  // Descrizione del modello
                auto.prezzo(),              // Prezzo
                auto.data().isPresent() ? auto.data().get() : "",  // Data immatricolazione
                auto.targa().isPresent() ? auto.targa().get() : "" // Targa (se presente)
            });
        }
    }
}
