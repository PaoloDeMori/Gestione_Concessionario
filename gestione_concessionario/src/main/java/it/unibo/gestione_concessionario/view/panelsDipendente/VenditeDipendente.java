package it.unibo.gestione_concessionario.view.panelsDipendente;


import it.unibo.gestione_concessionario.commons.dto.Vendita;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.panelsCliente.PersTable;
import it.unibo.gestione_concessionario.view.panelsCliente.TablesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VenditeDipendente extends JPanel {

    private JTable venditeTable;
    private DefaultTableModel tableModel;
    private Controller controller;

    public VenditeDipendente(Controller controller) {
        this.controller = controller;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        
        // Inizializza la tabella per visualizzare le auto filtrate
        tableModel = new TablesModel(new String[]{"Prezzo", "Tipologia", "Data", "Ora", "Dipendente", "Cliente","Numero Telaio"});
        venditeTable = new PersTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(venditeTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void filtraVendite() {
        List<Vendita> vendite = controller.allVendite();

        // Rimuovi tutti i dati attuali dalla tabella
        tableModel.setRowCount(0);

        // Aggiungi i risultati filtrati nella tabella
        for (Vendita vendita : vendite) {
            tableModel.addRow(new Object[]{
                vendita.contratto().getPrezzo(),
                vendita.contratto().getTipologia(),  // Descrizione del modello
                vendita.data(),             
                vendita.ora(),
                controller.getDipendenteNameById(vendita.id_dipendente()),
                controller.getClienteNameById(vendita.codCliente()), // Prezzo
                vendita.nuremo_telaio()
            });
        }
    }
}
