package com.projet.MVC.Modele ;

import java.util.Observable ;

import java.io.FileInputStream ;
import java.io.FileOutputStream ;
import java.io.IOException ;
import java.io.ObjectInputStream ;
import java.io.ObjectOutputStream ;
import java.io.Serializable ;

/**
 * Représente une partie de démineur.
 * <p>
 * Gère la grille, les interactions du joueur, les clics gauche/droit, l'état de la partie (gagné/perdu), et la sauvegarde/chargement.
 * <p>
 * Cette classe étend {@link Observable} pour notifier une interface graphique.
 */

public class Jeu extends Observable implements Serializable {
    /**
     * Identifiant de version pour la sérialisation.
     */
    private static final long serialVersionUID = 1L ;

    /**
     * Grille de la partie
     */
    private Grille grille ;

    /**
     * Largeur de la grille
     */
    private int tailleX=10 ;

    /**
     * Hauteur de la grille
     */
    private int tailleY=10 ;

    /**
     * Nombre de mines
     */
    private int nbMines=10 ;

    /**
     * Indique si la grille est hexagonale
     */
    private boolean estHex=false ;

    /**
     * Indique si c'est le premier clic
     */
    private boolean premierClic = true ;

    /**
     * Indique si la partie est perdue
     */
    private boolean perdu = false ;

    /**
     * Indique si la partie est gagnée
     */
    private boolean gagne = false;

    /**
     * Constructeur par défaut.
     * <p>
     * Crée une instance de jeu avec les paramètres par défaut.
     */
    public Jeu() {
        
    }

    /**
     * Constructeur avec paramètres.
     * <p>
     * Initialise la grille et démarre une partie.
     *
     * @param tailleX largeur de la grille
     * @param tailleY hauteur de la grille
     * @param nbMines nombre de mines
     * @param estHex  true si la grille est hexagonale
     */
    public Jeu (int tailleX, int tailleY, int nbMines, boolean estHex) {
        this.tailleX = tailleX ;
        this.tailleY = tailleY ;
        this.nbMines = nbMines ;
        this.estHex = estHex ;
        // Initialise la partie
        initialiserPartie() ;
    }

    /**
     * Initialise une nouvelle partie.
     * <p>
     * Tente de charger une sauvegarde si elle existe, sinon crée une nouvelle grille.
     */
    public void initialiserPartie() {
        // Tenter de charger une sauvegarde
        Jeu sauvegarde = Jeu.charger(tailleX, tailleY, nbMines, estHex) ;

        if (sauvegarde != null) {
            // S'il y a une sauvegarde, restaurer l'état de la sauvegarde
            this.grille = sauvegarde.getGrille() ; 
            this.premierClic = sauvegarde.premierClic ;
            this.perdu = sauvegarde.perdu ;
            this.gagne = sauvegarde.gagne ;
        }
        else {
            // S'il n'y a pas de sauvegarde, créer une nouvelle grille en fonction de la forme de ses cases
            if (estHex) {
                grille = new GrilleH(tailleX, tailleY) ;
            }
            else {
                grille = new GrilleC(tailleX, tailleY) ;
            }
        }

        // Notifier les observateurs pour qu'ils mettent à jour la vue
        setChanged() ;
        notifyObservers("REBUILD") ;
    }

    /**
     * Retourne la grille de la partie.
     *
     * @return la grille
     */
    public Grille getGrille() {
        return grille ;
    }

    /**
     * Remplace la grille de la partie et réinitialise l'état.
     *
     * @param g nouvelle grille
     */
    public void setGrille(Grille g) {
        this.grille = g ;

        premierClic = true ;
        perdu = false ;
        gagne = false ;

        // Notifier les observateurs pour qu'ils mettent à jour la vue
        setChanged() ;
        notifyObservers("REBUILD") ;
    }

    /**
     * Indique si la partie est perdue ou non
     * 
     * @return true si la partie est perdue
     */
    public boolean isPerdu() {
        return perdu ;
    }

    /**
     * Indique si la partie est gagnée ou non
     * 
     * @return true si la partie est gagnée
     */
    public boolean isGagne() {
        return gagne ;
    }

    /**
     * Gestion d'un clic gauche sur une case.
     * <p>
     * Révèle la case, place les mines si c'est le premier clic, et vérifie victoire/perte.
     *
     * @param x indice de la colonne
     * @param y indice de la ligne
     */
    public void clickGauche(int x, int y) {

        // Si la partie est perdue ou gagnée, ne rien faire
        if (perdu || gagne) {
            return ;
        }

        // Si c'est le premier clic, faire en sorte que la première case ne soit pas une bombe, et placer les mines
        if (premierClic) {
            grille.placerMines(x, y, nbMines) ;
            premierClic = false ;
        }

        // Case cliquée
        Case c = grille.getCase(x, y) ;

        // S'il y a une mine, la partie est perdue, et on révèle toutes les mines
        if (c.mine) {
            perdu = true ;
            grille.revelerToutesLesMines() ;
        }
        // S'il n'y a pas de mine, révèle la case
        else {
            grille.reveler(x, y) ;
        }

        // Si la partie est gagnée, supprimer la sauvegarde si existante
        if (grille.victoire()) {
            gagne = true ;
            // Chemin de la sauvegarde
            String nomSave = getNomSauvegarde() ;
            // Fichier de sauvegarde
            java.io.File f = new java.io.File(nomSave) ;
            // Si le fichier existe, le supprimer et ajouter un message de suppression
            if (f.exists()) {
                f.delete() ;
                System.out.println("Sauvegarde supprimée car partie gagnée !") ;
            }
        }

        // Notifier les observateurs pour qu'ils mettent à jour la vue
        setChanged() ;
        notifyObservers("CLICK") ;
    }

    /**
     * Gestion d'un clic droit sur une case.
     * <p>
     * Permute le drapeau sur la case si elle n'est pas révélée.
     *
     * @param x indice de la colonne
     * @param y indice de la ligne
     */
    public void clickDroit(int x, int y) {

        // Case cliquée
        Case c = grille.getCase(x, y) ;

        // Si la case a déjà été cliquée auparavant, ne rien faire
        if (c.revelee) {
            return ;
        }

        // Ajoute un drapeau s'il n'y en a pas ou le supprime s'il y en a un
        c.drapeau = !c.drapeau ;

        // Notifier les observateurs pour qu'ils mettent à jour la vue
        setChanged() ;
        notifyObservers() ;
    }

    /**
     * Analyse la grille pour donner un indice sur les cases.
     * <p>
     * Vérifie si des cases peuvent être déduites comme contenant ou non une mine.
     *
     * @return tableau [x, y, type] ou null si aucun indice
     *         type = 1 si case contient mine, 0 sinon
     */
    public int[] indice() {
        // Grille actuelle
        Grille grille = getGrille() ;

        // Itération sur chaque case de la grille
        for (int i = 0 ; i < grille.getX() ; i++) {
            for (int j = 0 ; j < grille.getY() ; j++) {
                // Case actuelle de la grille
                Case c = grille.getCase(i, j) ;

                // Si la case est déjà révélée et qu'elle a des mines voisines
                if (c.revelee && c.nbVoisins > 0) {
                    // Nombre de cases voisines vides
                    int nbcasevides = 0 ;
                    // Nombre de drapeaux voisins
                    int nbDrapeaux = 0 ;
                    // Tableau contenant les coordonnées d'une case et si c'est une mine ou non
                    int indice[] = null ;

                    // Itération sur les voisins de la case
                    for (int[] v : grille.getCoordVoisins(i, j)) {
                        // Case voisine actuelle
                        Case vCase = grille.getCase(v[0], v[1]) ;

                        // Si la case voisine actuelle n'est pas révélée, et qu'elle n'est pas un drapeau
                        if (!vCase.revelee && !vCase.drapeau) {
                            // Incrémentation du nombre de cases voisines vides
                            nbcasevides++ ;
                            // Affectation d'un tableau de 3 entiers
                            indice = new int[3] ;
                            // Coordonnées de la case voisine stockés dans indice
                            indice[0] = v[0] ;
                            indice[1] = v[1] ;
                        }

                        // Si la case voisine actuelle est un drapeau, incrémenter le nombre de drapeaux
                        if (vCase.drapeau) {
                            nbDrapeaux++ ;
                        }
                    }

                    // S'il reste des mines voisines, qu'il reste des cases voisines non-révélées et que le nombre de cases voisines non-révélées restantes correspond au nombre de mines restantes, indique que la case voisine est minée
                    if (nbcasevides - nbDrapeaux == c.nbVoisins && nbcasevides > 0 && c.nbVoisins > nbDrapeaux) {
                        indice[2] = 1 ; // 1 = case avec mine
                        // Renvoie l'indice
                        return indice ;
                    }

                    // S'il y a autant de drapeaux que de voisins, et qu'il reste des cases voisines non-révélées, indique que la case voisine est non-minée
                    if (nbDrapeaux == c.nbVoisins && nbcasevides > 0){
                        indice[2] = 0 ; // 0 = case sans mine
                        // Renvoie l'indice
                        return indice ;
                    }
                }
            }
        }

        // Renvoie null si aucun indice
        return null ;
    }

    /** 
     * Définit la largeur de la grille.
     * 
     * @param X nouvelle largeur (nombre de colonnes)
     */
    public void setTailleX(int X) {
        this.tailleX = X ;
    }

    /** 
     * Définit la hauteur de la grille.
     * 
     * @param Y nouvelle hauteur (nombre de lignes)
     */
    public void setTailleY(int Y) {
        this.tailleY = Y ;
    }

    /** 
     * Définit le nombre de mines dans la grille.
     * 
     * @param nbMines nombre de mines
     */
    public void setNbMines(int nbMines) {
        this.nbMines = nbMines ;
    }

    /** 
     * Définit si la grille est hexagonale ou non.
     * 
     * @param estHex true si la grille est hexagonale, false sinon
     */
    public void setEstHex(boolean estHex) {
        this.estHex = estHex ;
    }

    /** 
     * Retourne la largeur actuelle de la grille.
     * 
     * @return nombre de colonnes
     */
    public int getTailleX() {
        return tailleX ;
    }

    /** 
     * Retourne la hauteur actuelle de la grille.
     * 
     * @return nombre de lignes
     */
    public int getTailleY() {
        return tailleY ;
    }

    /** 
     * Retourne le nombre de mines configuré pour la partie.
     * 
     * @return nombre de mines
     */
    public int getNbMines() {
        return nbMines ;
    }

    /** 
     * Indique si la grille est hexagonale.
     * 
     * @return true si la grille est hexagonale, false sinon
     */
    public boolean isEstHex(){
        return estHex ;
    }

    /**
     * Retourne le nom du fichier de sauvegarde pour cette configuration 
     * 
     * @return chemin du fichier de sauvegarde
     */
    private String getNomSauvegarde() {
        // Chemin du répertoire
        String dir = "../saves/" ;
        // Création du dossier si nécessaire
        new java.io.File(dir).mkdirs() ;
        // Renvoie le chemin du fichier de sauvegarde
        return dir + "save_" + tailleX + "_" + tailleY + "_" + nbMines + "_" + estHex + ".dat" ;
    }

    /**
     * Sauvegarde la partie dans un fichier
     */
    public void sauvegarder() {
        try {
            // Création du fichier de sauvegarde
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getNomSauvegarde())) ;
            // Ecriture de la partie actuelle
            oos.writeObject(this) ;
            // Fermeture du fichier
            oos.close() ;
            // Affichage message de sauvegarde
            System.out.println("Sauvegarde OK") ;
        }
        catch (IOException e) {
            // Si exception, l'afficher
            e.printStackTrace() ;
        }
    }

    /**
     * Charge une partie sauvegardée si elle existe.
     *
     * @param x largeur
     * @param y hauteur
     * @param mines nombre de mines
     * @param estHex true si grille hexagonale
     * @return instance de Jeu ou null si aucune sauvegarde trouvée
     */
    public static Jeu charger(int x, int y, int mines, boolean estHex) {
        // Chemin du fichier de sauvegarde
        String nom = "../saves/save_" + x + "_" + y + "_" + mines + "_" + estHex + ".dat" ;
        try {
            // Accès au fichier de sauvegarde
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nom)) ;
            // Lecture du fichier de sauvegarde, et récupération de la partie
            Jeu j = (Jeu) ois.readObject() ;
            // Fermeture du fichier
            ois.close() ;
            // Affichage message chargement
            System.out.println("Chargement OK") ;
            // Renvoie le jeu chargé depuis la sauvegarde
            return j ;
        }
        catch (Exception e) {
            // Ne rien retourner si erreur
            return null ;
        }
    }

    /**
     * Supprime la sauvegarde associée à la configuration courante
    */
    public void supprimerSauvegarde() {
        // Récupération du fichier de sauvegarde
        java.io.File f = new java.io.File(getNomSauvegarde()) ;
        // Si le fichier existe, le supprimer et afficher un message de réussite ou non de suppression
        if (f.exists()) {
            boolean ok = f.delete() ;
            if (ok) {
                System.out.println("Sauvegarde supprimée") ;
            }
            else {
                System.out.println("Impossible de supprimer la sauvegarde") ;
            }
        }
    }
}
