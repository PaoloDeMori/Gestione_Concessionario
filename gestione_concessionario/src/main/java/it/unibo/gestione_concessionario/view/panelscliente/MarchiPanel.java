package it.unibo.gestione_concessionario.view.panelscliente;

import it.unibo.gestione_concessionario.commons.dto.Marchio;
import it.unibo.gestione_concessionario.view.CustomButton;
import it.unibo.gestione_concessionario.view.View;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class MarchiPanel extends JPanel {

    private JTable table;
    private String[] columnNames = {"Nome"};
    private Object[][] data;
    private CustomButton dipendenteButton;
    private List<Marchio> gMarchi = new ArrayList<>();

    public List<Marchio> getgMarchi() {
        return gMarchi;
    }

    public MarchiPanel() {
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("I marchi del nostro concessionario");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        this.add(titlePanel, BorderLayout.NORTH);

        dipendenteButton=new CustomButton("Info Dipendente Marchio Selezionato");
        this.add(dipendenteButton,BorderLayout.SOUTH);
        table = new PersTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void setMarchi(List<Marchio> marchi) {
        data = new Object[marchi.size()][2];
        for (int i = 0; i < marchi.size(); i++) {
            this.gMarchi=marchi;
            data[i][0] = marchi.get(i).nome();
        }

        DefaultTableModel tableModel = new TablesModel(data, columnNames);

        table.setModel(tableModel);
    }

    public JTable getTable() {
        return table;
    }


    public void setButtonActionListener(ActionListener al){
        this.dipendenteButton.addActionListener(al);
    }
}
