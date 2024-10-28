package it.unibo.gestione_concessionario.view.panelscliente;

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

    public ModelliPanel() {
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("I modelli del nostro concessionario");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);

        this.add(titlePanel, BorderLayout.NORTH);

        table = new PersTable();
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void setModelli(List<Modello> modelli) {
        data = new Object[modelli.size()][columnNames.length]; 
        for (int i = 0; i < modelli.size(); i++) {
            Modello modello = modelli.get(i);
            data[i][0] = modello.descrizione();
            data[i][1] = modello.anno();
            data[i][2] = modello.tipologia();
            data[i][3] = modello.marchio();
        }

        TablesModel tableModel = new TablesModel(data, columnNames);

        table.setModel(tableModel);
    }
}
