package it.unibo.gestione_concessionario.view.panelsdipendente;

import javax.swing.*;

import it.unibo.gestione_concessionario.commons.Months;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.View;

import java.awt.*;

public class VisualizzaStatisticheDipendente extends JPanel {
    private JPanel maiPanel=new JPanel();

    private JLabel totaleVendite;
    private JLabel mediaVendite;
    private JLabel modelloPiuVenduto;
    private JLabel modelloMenoVenduto;
    private JLabel finanziamento;
    private JLabel unicaRata;
    private JLabel totAutoVendute;
    private JLabel percConSconto;



    private JLabel tfNomeDipendente;
    private JLabel VenditaPerMese;
    private JComboBox<Months> tfMese;
    Controller controller;
    int meseSelezionato = 1;

    public VisualizzaStatisticheDipendente(Controller controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Statistiche");
        title.setFont(View.titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(title);
        this.add(titlePanel,BorderLayout.NORTH);

        refreshMainPanel();
        this.add(maiPanel,BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void refreshMainPanel(){
        maiPanel.removeAll();
        maiPanel.setLayout(new GridLayout(16, 2, 50, 5));

        maiPanel.add(new JLabel("Guadagno Totale:"));
        totaleVendite = new JLabel(Integer.toString(controller.visualizzaTotaleVendite())+ " €");
        maiPanel.add(totaleVendite);

        maiPanel.add(new JLabel("Media Guadagno:"));
        mediaVendite = new JLabel(Integer.toString(controller.visualizzaMedia()) + " €");
        maiPanel.add(mediaVendite);

        maiPanel.add(new JLabel("Modello Più Venduto:"));
        modelloPiuVenduto = new JLabel(controller.nomeModelloPiuVenduto());
        maiPanel.add(modelloPiuVenduto);

        maiPanel.add(new JLabel("Modello Meno Venduto:"));
        modelloMenoVenduto = new JLabel(controller.nomeModelloMenoVenduto());
        maiPanel.add(modelloMenoVenduto);

        maiPanel.add(new JLabel("Numero Acquisti Con Finanziamento:"));
        finanziamento = new JLabel(Integer.toString(controller.numeroAcquistiConFinanziamento()));
        maiPanel.add(finanziamento);

        maiPanel.add(new JLabel("Numero Acquisti Con Unica Rata:"));
        unicaRata = new JLabel(Integer.toString(controller.numeroAcquistiConUnicaRata()));
        maiPanel.add(unicaRata);

        maiPanel.add(new JLabel("Numero Totale Auto Vendute:"));
        totAutoVendute = new JLabel(Integer.toString(controller.numeroTotaleAutoVendute()));
        maiPanel.add(totAutoVendute);

        maiPanel.add(new JLabel("Percentuale Auto Vendute Con Sconto:"));
        percConSconto = new JLabel(Integer.toString(controller.percentualeAutoVenduteConSconto()) + " %");
        maiPanel.add(percConSconto);

        JPanel mesiPanel = new JPanel(new GridLayout(1, 2, 3, 3));

        mesiPanel.add(new JLabel("Vendite per mese:"));
        Months option[] = { Months.GENNAIO, Months.FEBBRAIO, Months.MARZO, Months.APRILE, Months.MAGGIO, Months.GIUGNO, 
                            Months.LUGLIO, Months.AGOSTO, Months.SETTEMBRE, Months.OTTOBRE, Months.NOVEMBRE, Months.DICEMBRE };

        tfMese = new JComboBox<>(option);
        tfMese.addActionListener(e -> {
            updateMese();
        });
        mesiPanel.add(tfMese);
        maiPanel.add(mesiPanel);

        VenditaPerMese = new JLabel(Integer.toString(controller.visualizzaGuadagniAlMese(meseSelezionato)));
        maiPanel.add(VenditaPerMese);

        maiPanel.add(new JLabel("Nome Dipendente:"));
        tfNomeDipendente = new JLabel(controller.getDipendenteUser().toString());
        maiPanel.add(tfNomeDipendente);
        maiPanel.revalidate();
        maiPanel.repaint();
        this.revalidate();
        this.repaint();
    }

    public void updateMese() {
        meseSelezionato = ((Months) tfMese.getSelectedItem()).getNumeroMese();
        VenditaPerMese.setText(Integer.toString(controller.visualizzaGuadagniAlMese(meseSelezionato)));
    }

}