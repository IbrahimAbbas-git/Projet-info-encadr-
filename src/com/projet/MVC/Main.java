package src.com.projet.MVC;

import src.com.projet.MVC.Modele.Jeu;
import src.com.projet.MVC.Vue_Controller.MF;
import src.com.projet.MVC.Vue_Controller.SimpleUI;

public class Main {

    public static void main(String[] args){
        Jeu jeu = new Jeu(); // constructeur juste vide, ne crée pas de grille ni MF
        SimpleUI menu = new SimpleUI(jeu); // ouvre le menu
    }
}