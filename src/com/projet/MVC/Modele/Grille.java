package com.projet.MVC.Modele ;

import java.util.ArrayList ;
import java.util.Random ;
import java.io.Serializable ;

/**
 * Classe abstraite représentant une grille du jeu de démineur.
 * <p>
 * La grille contient des cases, certaines pouvant être des mines.
 * <p>
 * Elle gère le placement des mines, la révélation des cases, et le calcul des voisins.
 */
public abstract class Grille implements Serializable {
    /**
     * Identifiant de version pour la sérialisation.
     */
    private static final long serialVersionUID = 1L ;

    /**
     * Tableau bidimensionnel représentant les cases de la grille.
     */
    protected Case[][] tab ;

    /**
     * Largeur de la grille (nombre de colonnes).
     */
    protected int x ;

    /**
     * Hauteur de la grille (nombre de lignes).
     */
    protected int y ;

    /**
     * Constructeur de la grille.
     * <p>
     * Initialise une grille de dimensions x * y et crée les cases vides.
     *
     * @param x nombre de colonnes
     * @param y nombre de lignes
     */
    public Grille(int x, int y) {

        this.x = x ;
        this.y = y ;

        // Tableau de cases pour la grille
        tab = new Case[x][y] ;

        for (int i = 0 ; i < x ; i++) {
            for (int j = 0 ; j < y ; j++) {
                // Initialisation de chaque case
                tab[i][j] = new Case() ;
            }
        }
    }

    /**
     * Retourne la case à la position donnée.
     *
     * @param i indice de la colonne
     * @param j indice de la ligne
     * @return la case correspondante
     */
    public Case getCase(int i, int j) {
        return tab[i][j] ;
    }

    /**
     * Retourne la largeur de la grille.
     *
     * @return nombre de colonnes
     */
    public int getX() {
        return x ;
    }

    /**
     * Retourne la hauteur de la grille.
     *
     * @return nombre de lignes
     */
    public int getY() {
        return y ;
    }

    /**
     * Place un certain nombre de mines aléatoirement sur la grille en évitant une zone sécurisée autour de la case initiale.
     *
     * @param safeX coordonnée x de la case sécurisée
     * @param safeY coordonnée y de la case sécurisée
     * @param nb nombre de mines à placer
     */
    public void placerMines(int safeX, int safeY, int nb) {

        Random r = new Random();
        // Compteur de mines
        int count = 0;

        while (count < nb) {

            // Placement de chaque mine à un endroit aléatoire
            int rx = r.nextInt(x) ;
            int ry = r.nextInt(y) ;

            // S'il y a une mine, chercher une autre case
            if (tab[rx][ry].mine) {
                continue ;
            }

            // Faire en sorte que si la case cliquée est sécurisée, ne rien faire (utile pour le clic de première cases)
            if (Math.abs(rx - safeX) <= 1 && Math.abs(ry - safeY) <= 1) {
                continue ;
            }

            // Indiquer qu'il y a une mine dans la case
            tab[rx][ry].mine = true ;
            // Incrémenter le compteur
            count++ ;
        }

        // Calcul des voisins
        calculVoisins() ;
    }

    /**
     * Calcule le nombre de mines voisines pour chaque case non minée.
     */
    protected void calculVoisins() {

        for (int i = 0 ; i < x ; i++) {
            for (int j = 0 ; j < y ; j++) {
                // S'il y a une mine dans la case, ne rien faire
                if (tab[i][j].mine) {
                    continue ;
                }

                // Nombre de mines voisines
                int n = 0 ;

                // Incrémenter le nombre de mines voisines pour chaque mine voisine
                for (Case c : getVoisins(i,j)) {
                    if (c.mine) {
                        n++ ;
                    }
                }

                // Affectation du nombre de mines voisines
                tab[i][j].nbVoisins = n;
            }
        }
    }

    /**
     * Révèle la case à la position donnée.
     * <p>
     * Si la case n'a pas de mines voisines, les cases voisines sont révélées récursivement.
     *
     * @param i indice de la colonne
     * @param j indice de la ligne
     */
    public void reveler(int i, int j) {

        Case c = tab[i][j] ;

        // Si une case a déjà été révélée ou bien qu'il y a un drapeau, ne rien faire
        if (c.revelee || c.drapeau) {
            return ;
        }

        // Indiquer que la case a été révélée
        c.revelee = true ;

        // S'il y a des mines voisines, s'arrêter ici
        if (c.nbVoisins != 0) {
            return ;
        }

        // S'il n'y a pas de mines voisines, continuer à révéler les cases voisines
        for (int[] v : getCoordVoisins(i,j)) {
            reveler(v[0],v[1]) ;
        }
    }

    /**
     * Vérifie si toutes les cases non minées ont été révélées.
     *
     * @return true si le joueur a gagné, false sinon
     */
    public boolean victoire() {

        for (int i = 0 ; i < x ; i++) {
            for (int j = 0 ; j < y ; j++) {
                // Si une mine n'a pas été révélée et qu'une case non-minée n'a pas été révélée, aucune victoire encore
                if (!tab[i][j].mine && !tab[i][j].revelee) {
                    return false ;
                }
            }
        }
        // Sinon, indique une victoire
        return true ;
    }

    /**
     * Retourne la liste des cases voisines d'une case donnée.
     *
     * @param i indice de la colonne
     * @param j indice de la ligne
     * @return liste des cases voisines
     */
    protected abstract ArrayList<Case> getVoisins(int i, int j) ;

    /**
     * Retourne la liste des coordonnées des cases voisines d'une case donnée.
     *
     * @param i indice de la colonne
     * @param j indice de la ligne
     * @return liste des coordonnées des cases voisines sous forme de tableau [x, y]
     */
    protected abstract ArrayList<int[]> getCoordVoisins(int i, int j) ;

    /**
     * Révèle toutes les mines de la grille.
     */
    public void revelerToutesLesMines(){
        for (int i = 0 ; i < x ; i++) {
            for (int j = 0 ; j < y ; j++) {
                // Si une mine existe, indiquer qu'elle a été révélée (utile à la fin d'une partie)
                if (tab[i][j].mine) {
                    tab[i][j].revelee = true ;
                }
            }
        }
    }
}
