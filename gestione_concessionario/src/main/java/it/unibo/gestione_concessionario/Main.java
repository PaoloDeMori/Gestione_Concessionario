package it.unibo.gestione_concessionario;

import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.LoginView;

public class Main {
        public static void main(String[] args) {
        LoginView lv = new LoginView(new Controller());
        lv.start();
    }
}
