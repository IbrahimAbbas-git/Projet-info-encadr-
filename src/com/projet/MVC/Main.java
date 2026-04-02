package com.projet.MVC ;

import com.projet.MVC.Modele.Jeu ;
import com.projet.MVC.Vue_Controller.SimpleUI ;

/**
 * Classe Main du projet Démineur.
 * <p>
 * Point d'entrée de l'application. Permet de lancer le jeu en ouvrant le menu de configuration via {@link SimpleUI}.
 */
public class Main {

    /**
     * Constructeur vide.
     * <p>
     * Permet de créer une instance de Main si nécessaire.
     */
    public Main() {
        
    }

    /**
     * Méthode principale (point d'entrée) de l'application.
     * <p>
     * Crée une instance du modèle {@link Jeu} et lance le menu de configuration {@link SimpleUI} pour que l'utilisateur choisisse les paramètres de la partie.
     *
     * @param args arguments passés à l'application (non utilisés)
     */
    public static void main(String[] args) {
        // Créé un jeu vide
        Jeu jeu = new Jeu() ;
        // Ouvre le menu principal
        SimpleUI menu = new SimpleUI(jeu) ;
        // Rend le menu visible
        menu.setVisible(true) ;
    }
}