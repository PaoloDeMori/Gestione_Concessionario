package it.unibo.gestione_concessionario.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class CustomButton extends JButton{
    public CustomButton(){
        super();
        this.create();
    }

    public CustomButton(String arg0){
        super(arg0);
        this.create();
    }

    private void create(){
        //caratteristiche grafiche
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(Color.BLACK);
        this.setForeground(new Color(255, 140, 0));
        //listener per modificare grafica in caso di passaggio col mouse
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent event){
                setBackground(new Color(255, 140, 0));
                setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent e){
                setBackground(Color.BLACK);
                setForeground(new Color(255, 140, 0));
            }
        });
    }
}
