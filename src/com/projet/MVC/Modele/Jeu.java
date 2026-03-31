package src.com.projet.MVC.Modele;

import java.util.Observable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import src.com.projet.MVC.Vue_Controller.MF;
import src.com.projet.MVC.Vue_Controller.SimpleUI;

public class Jeu extends Observable implements Serializable {
    private static final long serialVersionUID = 1L;

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
        // Tenter de charger une sauvegarde
        Jeu sauvegarde = Jeu.charger(tailleX, tailleY, nbMines, estHex);

        if (sauvegarde != null) {
            // Restaurer l'état depuis la sauvegarde
            this.grille = sauvegarde.getGrille(); 
            this.premierClic = sauvegarde.premierClic;
            this.perdu = sauvegarde.perdu;
            this.gagne = sauvegarde.gagne;
        } else {
            // Créer une nouvelle grille si pas de sauvegarde
            if (estHex) {
                grille = new GrilleH(tailleX, tailleY);
            } else {
                grille = new GrilleC(tailleX, tailleY);
            }
        }

        // Notifier les observateurs pour qu'ils mettent à jour la vue
        setChanged();
        notifyObservers("REBUILD"); // "REBUILD" ou autre string que MF écoute pour reconstruire l'affichage
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

        if(grille.victoire()) {
            gagne = true;
            String nomSave = getNomSauvegarde();
            java.io.File f = new java.io.File(nomSave);
            if(f.exists()){
                f.delete();
                System.out.println("Sauvegarde supprimée car partie gagnée !");
            }
        }

        setChanged();
        notifyObservers("CLICK");
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

    private String getNomSauvegarde() {
        String dir = "../saves/";
        new java.io.File(dir).mkdirs(); // crée le dossier si nécessaire
        return dir + "save_" + tailleX + "_" + tailleY + "_" + nbMines + "_" + estHex + ".dat";
    }

    public void sauvegarder() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(getNomSauvegarde())
            );
            oos.writeObject(this);
            oos.close();
            System.out.println("Sauvegarde OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Jeu charger(int x, int y, int mines, boolean estHex) {
        String nom = "../saves/save_" + x + "_" + y + "_" + mines + "_" + estHex + ".dat";
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nom));
            Jeu j = (Jeu) ois.readObject();
            ois.close();
            System.out.println("Chargement OK");
            return j;
        } catch (Exception e) {
            return null;
        }
    }

    public void supprimerSauvegarde() {
        java.io.File f = new java.io.File(getNomSauvegarde());
        if(f.exists()) {
            boolean ok = f.delete();
            if(ok) System.out.println("Sauvegarde supprimée");
            else System.out.println("Impossible de supprimer la sauvegarde");
        }
    }
}
