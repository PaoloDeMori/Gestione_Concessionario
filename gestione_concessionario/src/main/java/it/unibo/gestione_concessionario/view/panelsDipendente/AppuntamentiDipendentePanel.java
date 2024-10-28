package it.unibo.gestione_concessionario.view.panelsdipendente;


import it.unibo.gestione_concessionario.commons.dto.Appuntamento;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.View;
import it.unibo.gestione_concessionario.view.panelscliente.PersTable;
import it.unibo.gestione_concessionario.view.panelscliente.TablesModel;

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

    public AppuntamentiDipendentePanel(Controller controller) {
        this.controller=controller;
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("I Tuoi Appuntamenti");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        buttonPanel=new JPanel(new GridLayout(1,2));
        this.add(titlePanel, BorderLayout.NORTH);
        table = new PersTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void setAppuntamento(List<Appuntamento> appuntamenti) {
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

        DefaultTableModel tableModel = new TablesModel(data, columnNames);

        table.setModel(tableModel);
    }

    public JTable getTable() {
        return table;
    }
}

    

