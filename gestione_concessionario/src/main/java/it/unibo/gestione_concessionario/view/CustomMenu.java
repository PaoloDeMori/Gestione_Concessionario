package it.unibo.gestione_concessionario.view;

import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomMenu extends JMenu {
    public CustomMenu() {
        super();
        this.create();
    }

    public CustomMenu(String arg0) {
        super(arg0);
        this.create();
    }

    private void create() {
        this.setHorizontalTextPosition(SwingConstants.LEFT);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(Color.BLACK);
        this.setForeground(new Color(255, 140, 0));
        this.setOpaque(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                setBackground(new Color(255, 140, 0));
                setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.BLACK);
                setForeground(new Color(255, 140, 0));
            }
        });
    }
}
