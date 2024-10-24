package it.unibo.gestione_concessionario.view.panelsDipendente;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.FlowLayout;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import it.unibo.gestione_concessionario.commons.dto.Optionals;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.CustomButton;

public class AddOptionalsDialog extends JDialog {
    Map<JCheckBox,Optionals> tfoptionalsList;
    JPanel maiPanel;
    CustomButton saveButton;

    AddOptionalsDialog(AddAutoDipendente panel, Controller controller) {
        super();
        setLayout(new BorderLayout(0, 5));
        this.add(new JLabel("Seleziona gli optionals della macchina:"),BorderLayout.NORTH);


        maiPanel=new JPanel(new FlowLayout());
        maiPanel.add(new JLabel("Optionals:"));
        Optionals [] optionals = panel.getOptional().toArray(new Optionals[0]);
        JPanel checkBoxPanel = new JPanel(new GridLayout(optionals.length, 1));
        tfoptionalsList = new HashMap<>();
        for(Optionals o : optionals ){
            JCheckBox checkBox = new JCheckBox(o.descrizione());
            tfoptionalsList.put(checkBox, o);
            checkBoxPanel.add(checkBox);
        }
        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        maiPanel.add(scrollPane);
        this.add(maiPanel,BorderLayout.CENTER);

        saveButton=new CustomButton("Aggiungi gli optional");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<Optionals> opt = new ArrayList<>();
                for(JCheckBox o : tfoptionalsList.keySet()){
                    if(o.isSelected()){
                        opt.add(tfoptionalsList.get(o));
                    }
                }
                panel.setOptionals(opt);
                dispose();
            }
            
        });
        this.add(saveButton,BorderLayout.SOUTH);

    }

}
