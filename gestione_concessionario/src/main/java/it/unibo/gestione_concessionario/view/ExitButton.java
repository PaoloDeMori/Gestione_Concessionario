package it.unibo.gestione_concessionario.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingConstants;


public class ExitButton extends JButton{
    public ExitButton(){
        super();
        this.create();
    }

    public ExitButton(String arg0){
        super(arg0);
        this.create();
    }

    private void create(){
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(Color.RED);
        this.setForeground(Color.BLACK);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event){
                setBackground(Color.BLACK);
                setForeground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent e){
                setBackground(Color.RED);
                setForeground(Color.BLACK);
            }
        });
    }
}
