package it.unibo.gestione_concessionario.view.panelsDipendente;

import it.unibo.gestione_concessionario.commons.dto.Offerta;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.panelsCliente.PersTable;
import it.unibo.gestione_concessionario.view.panelsCliente.TablesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OfferteDisponibiliPanel extends JPanel {

    private JTable autoTable;
    private DefaultTableModel tableModel;
    private Controller controller;

    public OfferteDisponibiliPanel(Controller controller) {
        this.controller = controller;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        
        // Inizializza la tabella per visualizzare le auto filtrate
        tableModel = new TablesModel(new String[]{"percentuale", "data inizio", "data fine"});
        autoTable = new PersTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(autoTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Metodo per filtrare le auto in base ai criteri selezionati
    public void filtraOfferte() {
        // Esegui la query di filtro tramite il controller
        List<Offerta> offerte = controller.allOfferte();

        // Rimuovi tutti i dati attuali dalla tabella
        tableModel.setRowCount(0);

        // Aggiungi i risultati filtrati nella tabella
        for (Offerta off : offerte) {
            tableModel.addRow(new Object[]{
                off.percentuale(),
                off.dataInizio(),
                off.dataFine()
            });
        }
    }
}
