package it.unibo.gestione_concessionario.view.panelsDipendente;


import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.view.View;
import it.unibo.gestione_concessionario.view.panelsCliente.PersTable;
import it.unibo.gestione_concessionario.view.panelsCliente.TablesModel;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class AllAutoDipendente extends JPanel {

    private JTable table;
    private String[] columnNames = {"Modello","Numero Telaio", "Prezzo", "Immatricolazione", "Motore","alimentazione","Data Immatricolazione","Targa"};
    private Object[][] data;
    JPanel buttonPanel;

    // Costruttore che configura il layout generale
    public AllAutoDipendente() {
        this.setLayout(new BorderLayout());

        // Pannello del titolo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Le auto che gestisci");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        buttonPanel=new JPanel(new GridLayout(1,2));
        // Aggiungi il pannello del titolo alla parte superiore del layout
        this.add(titlePanel, BorderLayout.NORTH);
        // Inizializza la tabella senza dati (i dati saranno impostati dal metodo setMarchi)
        table = new PersTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Metodo per impostare i marchi nella tabella
    public void setAuto(List<Auto> auto) {
        // Crea i dati per la tabella
        data = new Object[auto.size()][8];
        for (int i = 0; i < auto.size(); i++) {
            data[i][0] = auto.get(i).getDescrizioneModello();
            data[i][1] = auto.get(i).getNumero_telaio();
            data[i][2] = auto.get(i).getPrezzo();
            data[i][3] = auto.get(i).getImmatricolazione();
            data[i][4] = auto.get(i).getMotore();
            data[i][5]=auto.get(i).getAlimentazione();
            data[i][6]=(auto.get(i).getImmatricolazione()==true) ? auto.get(i).getData().get() : "";
            data[i][7]=(auto.get(i).getImmatricolazione()==true) ? auto.get(i).getTarga().get() : "";
        }

        // Crea un modello di tabella personalizzato non modificabile con i dati aggiornati
        DefaultTableModel tableModel = new TablesModel(data, columnNames);

        // Imposta il modello sulla tabella
        table.setModel(tableModel);
    }

    public JTable getTable() {
        return table;
    }
}

    

