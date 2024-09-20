package it.unibo.gestione_concessionario.view.panelsCliente;

import it.unibo.gestione_concessionario.commons.dto.Optionals;
import it.unibo.gestione_concessionario.view.CustomButton;
import it.unibo.gestione_concessionario.view.View;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class OptionalPanel extends JPanel {

    private JTable table;
    private String[] columnNames = {"Descrizione","Prezzo"};
    private Object[][] data;
    private CustomButton dipendenteButton;

    // Costruttore che configura il layout generale
    public OptionalPanel() {
        this.setLayout(new BorderLayout());

        // Pannello del titolo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Optionals");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        // Aggiungi il pannello del titolo alla parte superiore del layout
        this.add(titlePanel, BorderLayout.NORTH);

        dipendenteButton=new CustomButton("Info Dipendente Marchio Selezionato");
        this.add(dipendenteButton,BorderLayout.SOUTH);
        // Inizializza la tabella senza dati (i dati saranno impostati dal metodo setMarchi)
        table = new PersTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Metodo per impostare i marchi nella tabella
    public void setOptional(List<Optionals> optionals) {
        // Crea i dati per la tabella
        data = new Object[optionals.size()][2];
        for (int i = 0; i < optionals.size(); i++) {
            data[i][0] = optionals.get(i).descrizione();
            data[i][1] = optionals.get(i).prezzo();
        }

        // Crea un modello di tabella personalizzato non modificabile con i dati aggiornati
        DefaultTableModel tableModel = new TablesModel(data, columnNames);

        // Imposta il modello sulla tabella
        table.setModel(tableModel);
        table.revalidate();
        table.repaint();
        this.revalidate();
        this.repaint();
        this.add(table);
    }

    public JTable getTable() {
        return table;
    }


    public void setButtonActionListener(ActionListener al){
        this.dipendenteButton.addActionListener(al);
    }
}
