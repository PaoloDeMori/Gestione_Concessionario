package it.unibo.gestione_concessionario.view.panelsDipendente;

import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.panelsCliente.PersTable;
import it.unibo.gestione_concessionario.view.panelsCliente.TablesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AutoScontateDipendente extends JPanel {

    private JTable autoTable;
    private DefaultTableModel tableModel;
    private Controller controller;

    public AutoScontateDipendente(Controller controller) {
        this.controller = controller;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        
        // Inizializza la tabella per visualizzare le auto filtrate
        tableModel = new TablesModel(new String[]{"Numero Telaio", "Modello", "Prezzo Scontato", "Data Immatricolazione", "Targa"});
        autoTable = new PersTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(autoTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Metodo per filtrare le auto in base ai criteri selezionati
    public void filtraAuto() {
        // Ottieni il marchio e la tipologia selezionati
        Marchio marchioSelezionato = this.controller.getUserMarchio();

        // Esegui la query di filtro tramite il controller
        List<Auto> autoFiltrate = controller.visualizzaAutoScontate(marchioSelezionato);

        // Rimuovi tutti i dati attuali dalla tabella
        tableModel.setRowCount(0);

        // Aggiungi i risultati filtrati nella tabella
        for (Auto auto : autoFiltrate) {
            tableModel.addRow(new Object[]{
                auto.getNumero_telaio(),
                auto.getDescrizioneModello(),  // Descrizione del modello
                auto.getPrezzo(),              // Prezzo
                auto.getData().isPresent() ? auto.getData().get() : "",  // Data immatricolazione
                auto.getTarga().isPresent() ? auto.getTarga().get() : "" // Targa (se presente)
            });
        }
    }
}
