package src.com.projet.MVC.Modele;

import java.util.Observable;

import src.com.projet.MVC.Vue_Controller.MF;
import src.com.projet.MVC.Vue_Controller.SimpleUI;

public class Jeu extends Observable {

    private Grille grille;
    private int tailleX=10;
    private int tailleY=10;
    private int nbMines=10;
    private boolean estHex=false;
    private boolean premierClic = true;
    private boolean perdu = false;
    private boolean gagne = false;

    public Jeu(){
        SimpleUI acceuil = new SimpleUI(this);
        acceuil.setVisible(true);    
    }
    public void initialiserPartie() {
        if(estHex) {
            grille = new GrilleH(tailleX, tailleY);
        } else {
            grille = new GrilleC(tailleX, tailleY);
        }        
        MF mf = new MF(this);
        this.addObserver(mf);
        mf.setVisible(true);
    }

    public Grille getGrille(){
        return grille;
    }

    public void setGrille(Grille g){
        this.grille = g;

        premierClic = true;
        perdu = false;
        gagne = false;

        setChanged();
        notifyObservers("REBUILD");
    }

    public boolean isPerdu(){ return perdu; }
    public boolean isGagne(){ return gagne; }

    public void clickGauche(int x,int y){

        if(perdu || gagne) return;

        if(premierClic){
            grille.placerMines(x,y,nbMines);
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
    public void setTailleX(int X){
        this.tailleX = X;
    }
    public void setTailleY(int Y){
        this.tailleY = Y;
    }
    public void setNbMines(int nbMines){
        this.nbMines = nbMines;
    }
    public void setEstHex(boolean estHex){
        this.estHex = estHex;
    }
    public int getTailleX(){ return tailleX; }
    public int getTailleY(){ return tailleY; }
    public int getNbMines(){ return nbMines; }
    public boolean isEstHex(){ return estHex; }
}
