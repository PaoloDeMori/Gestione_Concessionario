package it.unibo.gestione_concessionario.view.panelsdipendente;


import it.unibo.gestione_concessionario.commons.dto.Auto;
import it.unibo.gestione_concessionario.view.CustomButton;
import it.unibo.gestione_concessionario.view.View;
import it.unibo.gestione_concessionario.view.panelscliente.PersTable;
import it.unibo.gestione_concessionario.view.panelscliente.TablesModel;

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

public class AllAutoDipendente extends JPanel {

    private JTable table;
    private String[] columnNames = {"Modello","Numero Telaio", "Prezzo", "Immatricolazione", "Motore","alimentazione","Data Immatricolazione","Targa"};
    private Object[][] data;
    JPanel buttonPanel;
    private CustomButton garanziaButton;
    private CustomButton optionalButton;

    public AllAutoDipendente() {
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Le auto che gestisci");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        buttonPanel=new JPanel(new GridLayout(1,2));
        
        garanziaButton=new CustomButton("Garanzia Selezionato");
        buttonPanel.add(garanziaButton);

        optionalButton=new CustomButton("Optional Selezionato");
        buttonPanel.add(optionalButton);


        this.add(titlePanel, BorderLayout.NORTH);
        table = new PersTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
    }

    public void setAuto(List<Auto> auto) {
        data = new Object[auto.size()][8];
        for (int i = 0; i < auto.size(); i++) {
            data[i][0] = auto.get(i).getDescrizioneModello();
            data[i][1] = auto.get(i).getNumero_telaio();
            data[i][2] = auto.get(i).getPrezzo();
            data[i][3] = auto.get(i).getImmatricolazione();
            data[i][4] = auto.get(i).getMotore();
            data[i][5]=auto.get(i).getAlimentazione();
            data[i][6]=(auto.get(i).getImmatricolazione()) ? auto.get(i).getData().get() : "";
            data[i][7]=(auto.get(i).getImmatricolazione()) ? auto.get(i).getTarga().get() : "";
        }

        DefaultTableModel tableModel = new TablesModel(data, columnNames);

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

    

