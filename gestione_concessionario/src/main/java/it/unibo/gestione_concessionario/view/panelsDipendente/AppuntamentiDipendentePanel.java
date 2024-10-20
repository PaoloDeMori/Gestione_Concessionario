package it.unibo.gestione_concessionario.view.panelsDipendente;


import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.controller.Controller;
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

public class AppuntamentiDipendentePanel extends JPanel {

    private JTable table;
    private String[] columnNames = {"Nome Cliente","Data", "Ora", "Durata", "Tipologia","Auto"};
    private Object[][] data;
    JPanel buttonPanel;
    Controller controller;

    // Costruttore che configura il layout generale
    public AppuntamentiDipendentePanel(Controller controller) {
        this.controller=controller;
        this.setLayout(new BorderLayout());

        // Pannello del titolo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("I Tuoi Appuntamenti");
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
    public void setAppuntamento(List<Appuntamento> appuntamenti) {
        // Crea i dati per la tabella
        data = new Object[appuntamenti.size()][6];
        for (int i = 0; i < appuntamenti.size(); i++) {
            String nomeCliente = this.controller.getClienteNameById(appuntamenti.get(i).id_cliente());
            data[i][0] = nomeCliente;
            data[i][1] = appuntamenti.get(i).data();
            data[i][2] = appuntamenti.get(i).ora();
            data[i][3] = appuntamenti.get(i).durata();
            data[i][4] = appuntamenti.get(i).tipologia();
            data[i][5] = appuntamenti.get(i).numero_telaio();
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

    

