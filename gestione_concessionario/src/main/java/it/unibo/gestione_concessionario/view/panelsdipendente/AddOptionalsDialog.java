package it.unibo.gestione_concessionario.view.panelsdipendente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unibo.gestione_concessionario.commons.dto.Optionals;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

public class AddOptionalsDialog extends JDialog {
    Map<JCheckBox,Optionals> mapOptionalsList;
    JPanel maiPanel;
    CustomButton saveButton;
    AddAutoDipendente panel;

    AddOptionalsDialog(AddAutoDipendente panel, Controller controller) {
        super();
        this.panel=panel;
        setLayout(new BorderLayout(0, 5));
        this.add(new JLabel("Seleziona gli optionals della macchina:"),BorderLayout.NORTH);


        maiPanel=new JPanel(new BorderLayout());
        maiPanel.add(new JLabel("Optionals:"));
        Optionals [] optionals = panel.getOptional().toArray(new Optionals[0]);
        JPanel checkBoxPanel = new JPanel(new GridLayout(optionals.length, 1));
        mapOptionalsList = new HashMap<>();
        for(Optionals o : optionals ){
            JCheckBox checkBox = new JCheckBox(o.descrizione());
            mapOptionalsList.put(checkBox, o);
            checkBoxPanel.add(checkBox);
        }
        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        maiPanel.add(scrollPane,BorderLayout.CENTER);
        this.add(maiPanel,BorderLayout.CENTER);

        saveButton=new CustomButton("Aggiungi gli optional");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Optionals> opt = new ArrayList<>();
                for(JCheckBox o : mapOptionalsList.keySet()){
                    if(o.isSelected()){
                        opt.add(mapOptionalsList.get(o));
                    }
                }
                panel.setOptionals(opt);
                dispose();
            }
            
        });
        this.add(saveButton,BorderLayout.SOUTH);

    }

    public void start(){
        this.maiPanel.setPreferredSize(new Dimension(panel.getWidth()/4, panel.getHeight()/4));
        this.maiPanel.setMinimumSize(new Dimension(panel.getWidth()/4, panel.getHeight()/4));
        this.pack();
        this.setVisible(true);
    }

}
