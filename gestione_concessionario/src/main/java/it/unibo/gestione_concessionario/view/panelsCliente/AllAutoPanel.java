package it.unibo.gestione_concessionario.view.panelscliente;


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

    public AllAutoPanel() {
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Le auto del nostro concessionario");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        buttonPanel=new JPanel(new GridLayout(1,2));

        this.add(titlePanel, BorderLayout.NORTH);

        garanziaButton=new CustomButton("Garanzia Selezionato");
        buttonPanel.add(garanziaButton);

        optionalButton=new CustomButton("Optional Selezionato");
        buttonPanel.add(optionalButton);
        table = new PersTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void setAuto(List<Auto> auto) {
        data = new Object[auto.size()][6];
        for (int i = 0; i < auto.size(); i++) {
            data[i][0] = auto.get(i).getNumero_telaio();
            data[i][1] = auto.get(i).getPrezzo();
            data[i][2] = auto.get(i).getImmatricolazione();
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

    

