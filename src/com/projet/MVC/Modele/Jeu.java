package src.com.projet.MVC.Modele;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JButton;

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
    public Jeu(int tailleX,int tailleY,int nbMines,boolean estHex){
        //pour recommencer avec le boutton recommencer
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        this.nbMines = nbMines;
        this.estHex = estHex;
        initialiserPartie();
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

    public int[] indice(JButton[][] boutons){
        Grille grille = getGrille();
        for(int i=0;i<grille.getX();i++)
        {   for(int j=0;j<grille.getY();j++){
            Case c = grille.getCase(i,j);
            if(c.revelee && c.nbVoisins>0){
                int nbcasevides = 0;
                int nbDrapeaux = 0;
                int indice[] = null;
                for( int[] v : grille.getCoordVoisins(i,j)){
                        Case vCase = grille.getCase(v[0],v[1]);
                        if(!vCase.revelee && !vCase.drapeau){
                            nbcasevides++;
                            indice = new int[3];
                            indice[0] = v[0];
                            indice[1] = v[1];
                        }
                        if(vCase.drapeau){
                            nbDrapeaux++;
                        }
                }
                if(nbcasevides-nbDrapeaux == c.nbVoisins && nbcasevides > 0 && c.nbVoisins> nbDrapeaux){
                    indice[2] = 1; // 1 = case avec mine
                    return indice;
                }
                if(nbDrapeaux == c.nbVoisins && nbcasevides > 0){
                    indice[2] = 0; // 0 = case sans mine
                    return indice;
                }
            }
            }
        }
        return null;
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
