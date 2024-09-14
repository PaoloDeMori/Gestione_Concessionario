package it.unibo.gestione_concessionario.view.panels;

import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.view.View;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class MarchiPanel extends JPanel {

    private JTable table;
    private String[] columnNames = {"Nome"};
    private Object[][] data;

    // Costruttore che configura il layout generale
    public MarchiPanel() {
        this.setLayout(new BorderLayout());

        // Pannello del titolo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("I marchi del nostro concessionario");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        // Aggiungi il pannello del titolo alla parte superiore del layout
        this.add(titlePanel, BorderLayout.NORTH);

        // Inizializza la tabella senza dati (i dati saranno impostati dal metodo setMarchi)
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Metodo per impostare i marchi nella tabella
    public void setMarchi(List<Marchio> marchi) {
        // Crea i dati per la tabella
        data = new Object[marchi.size()][1];
        for (int i = 0; i < marchi.size(); i++) {
            data[i][0] = marchi.get(i).nome();
        }

        // Crea un modello di tabella personalizzato non modificabile con i dati aggiornati
        DefaultTableModel tableModel = new TablesModel(data, columnNames);

        // Imposta il modello sulla tabella
        table.setModel(tableModel);
    }
}
