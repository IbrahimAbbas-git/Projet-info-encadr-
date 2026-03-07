package src.com.projet.MVC;

import src.com.projet.MVC.Modele.Jeu;
import src.com.projet.MVC.Vue_Controller.MF;

public class Main {

    public static void main(String[] args){

        Jeu jeu = new Jeu();

        MF mf = new MF(jeu);

        jeu.addObserver(mf);

        mf.setVisible(true);
    }

}
