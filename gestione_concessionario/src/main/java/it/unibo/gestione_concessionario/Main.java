package it.unibo.gestione_concessionario;

import java.sql.Connection;

import it.unibo.gestione_concessionario.commons.ConnectionFactory;
import it.unibo.gestione_concessionario.commons.ImpossibleToConnectException;
import it.unibo.gestione_concessionario.controller.Controller;
import it.unibo.gestione_concessionario.view.LoginView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Controller controller;
        Connection connection;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        if (args.length == 1) {
            if (args[0].equals("-cl")) {
                System.out.println("Inserisci nome utente DB");
                try {
                    String name = buffer.readLine();
                    System.out.println("Inserisci password");
                    String password = buffer.readLine();
                    System.out.println("Inserisci location db-> jdbc:");
                    String location = "jdbc:" + buffer.readLine();
                    connection = ConnectionFactory.build("gestione_concessionario_prova", location, name, password);
                    controller = new Controller(connection);
                    LoginView lv = new LoginView(controller);
                    lv.start();
                } catch (ImpossibleToConnectException e) {
                    System.err.println("Impossibile connettersi credenziali errate");
                } catch (IOException e) {
                    System.err.println("Impossibile connettersi credenziali errate");
                }
            }
            else{
                System.err.println("Argomento non valido");
            }
        } else {
            try {
                connection = ConnectionFactory.build("gestione_concessionario_prova",
                        "jdbc:mysql://localhost:3306/", "root", "cadmio");
                        controller = new Controller(connection);
                        LoginView lv = new LoginView(controller);
                        lv.start();
            } catch (ImpossibleToConnectException e) {
                System.err.println("Impossibile connettersi credenziali errate");
            }
        }
    }
}
