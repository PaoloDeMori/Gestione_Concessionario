package it.unibo.gestione_concessionario.view.panelsCliente;

import it.unibo.gestione_concessionario.commons.dto.Modello;
import it.unibo.gestione_concessionario.view.View;

import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

public class ModelliPanel extends JPanel {
    
    private JTable table;
    private String[] columnNames = {"Nome", "Anno", "Tipologia", "Marchio"};
    private Object[][] data;

    // Costruttore che configura il layout generale
    public ModelliPanel() {
        this.setLayout(new BorderLayout());

        // Pannello del titolo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("I modelli del nostro concessionario");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        // Aggiungi il pannello del titolo alla parte superiore del layout
        this.add(titlePanel, BorderLayout.NORTH);

        // Inizializza la tabella senza dati (i dati saranno impostati dal metodo setModelli)
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Metodo per impostare i modelli nella tabella
    public void setModelli(List<Modello> modelli) {
        // Crea i dati per la tabella
        data = new Object[modelli.size()][columnNames.length];  // Use columnNames.length for correct array size
        for (int i = 0; i < modelli.size(); i++) {
            Modello modello = modelli.get(i);
            data[i][0] = modello.descrizione();  // Nome
            data[i][1] = modello.anno();         // Anno
            data[i][2] = modello.tipologia();    // Tipologia
            data[i][3] = modello.marchio();      // Marchio
        }

        // Crea un modello di tabella personalizzato non modificabile con i dati aggiornati
        TablesModel tableModel = new TablesModel(data, columnNames);

        // Imposta il modello sulla tabella
        table.setModel(tableModel);
    }
}
