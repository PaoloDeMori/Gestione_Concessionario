package it.unibo.gestione_concessionario.view.panels;

import it.unibo.gestione_concessionario.commons.dto.Marchio;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.util.List;
    
    public class MarchiPanel extends JPanel {
    
        private JTable table;
    
        public MarchiPanel(List<Marchio> marchi) {
            this.setLayout(new BorderLayout());
    
            // Creazione della tabella per visualizzare i nomi dei marchi
            String[] columnNames = {"Nome"};
            Object[][] data = new Object[marchi.size()][1];
    
            for (int i = 0; i < marchi.size(); i++) {
                data[i][0] = marchi.get(i).nome();
            }
    
            table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            this.add(scrollPane, BorderLayout.CENTER);
        }
    }
    




