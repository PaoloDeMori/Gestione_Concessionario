package it.unibo.gestione_concessionario.view.panelscliente;

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

    public OptionalPanel() {
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Optionals");
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

    public void setOptional(List<Optionals> optionals) {
        data = new Object[optionals.size()][2];
        for (int i = 0; i < optionals.size(); i++) {
            data[i][0] = optionals.get(i).descrizione();
            data[i][1] = optionals.get(i).prezzo();
        }

        DefaultTableModel tableModel = new TablesModel(data, columnNames);

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
