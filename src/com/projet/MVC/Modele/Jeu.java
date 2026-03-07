package src.com.projet.MVC.Modele;

import java.util.Observable;

public class Jeu extends Observable {

    private Grille grille;

    private boolean premierClic = true;
    private boolean perdu = false;
    private boolean gagne = false;

    public Jeu(){
        grille = new GrilleH(10,10);
    }

    public Grille getGrille(){
        return grille;
    }

    public boolean isPerdu(){ return perdu; }
    public boolean isGagne(){ return gagne; }

    public void clickGauche(int x,int y){

        if(perdu || gagne) return;

        if(premierClic){
            grille.placerMines(x,y,10);
            premierClic = false;
        }

        Case c = grille.getCase(x,y);

        if(c.mine){
            perdu = true;
            // on révèle toutes les mines pour que MF puisse les afficher
            grille.revelerToutesLesMines();
        } else {
            grille.reveler(x,y);
        }

        if(grille.victoire())
            gagne = true;

        setChanged();
        notifyObservers();
    }

    public void clickDroit(int x,int y){

        Case c = grille.getCase(x,y);

        if(c.revelee) return;

        c.drapeau = !c.drapeau;

        setChanged();
        notifyObservers();
    }

}
