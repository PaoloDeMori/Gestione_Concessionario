package it.unibo.gestione_concessionario.view.panels;


import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.view.CustomButton;
import it.unibo.gestione_concessionario.view.View;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class AllAutoPanel extends JPanel {

    private JTable table;
    private String[] columnNames = {" Numero Telaio", "Prezzo", "Immatricolazione"};
    private Object[][] data;
    JPanel buttonPanel;
    private CustomButton garanziaButton;
    private CustomButton optionalButton;

    // Costruttore che configura il layout generale
    public AllAutoPanel() {
        this.setLayout(new BorderLayout());

        // Pannello del titolo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Le auto del nostro concessionario");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        buttonPanel=new JPanel(new GridLayout(1,2));

        // Aggiungi il pannello del titolo alla parte superiore del layout
        this.add(titlePanel, BorderLayout.NORTH);

        garanziaButton=new CustomButton("Garanzia Selezionato");
        buttonPanel.add(garanziaButton);

        optionalButton=new CustomButton("Optional Selezionato");
        buttonPanel.add(optionalButton);
        // Inizializza la tabella senza dati (i dati saranno impostati dal metodo setMarchi)
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Metodo per impostare i marchi nella tabella
    public void setAuto(List<Auto> auto) {
        // Crea i dati per la tabella
        data = new Object[auto.size()][6];
        for (int i = 0; i < auto.size(); i++) {
            data[i][0] = auto.get(i).numero_telaio();
            data[i][1] = auto.get(i).prezzo();
            data[i][2] = auto.get(i).immatricolazione();
        }

        // Crea un modello di tabella personalizzato non modificabile con i dati aggiornati
        DefaultTableModel tableModel = new TablesModel(data, columnNames);

        // Imposta il modello sulla tabella
        table.setModel(tableModel);
    }

    public JTable getTable() {
        return table;
    }


    public void setGaranziaButtonActionListener(ActionListener al){
        this.garanziaButton.addActionListener(al);
    }

    public void setOptionalButtonActionListener(ActionListener al){
        this.optionalButton.addActionListener(al);
    }
}

    

