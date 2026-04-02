package com.projet.MVC.Modele ;

import java.util.ArrayList ;

/**
 * GrilleC représente une grille de démineur de type classique.
 * <p>
 * Chaque case peut avoir jusqu'à 8 voisins (cases adjacentes, y compris diagonales).
 * <p>
 * Cette classe étend la classe abstraite {@link Grille}.
 */
public class GrilleC extends Grille {

    /**
     * Constructeur de la grille classique.
     * <p>
     * Initialise une grille de dimensions x * y.
     *
     * @param x nombre de colonnes
     * @param y nombre de lignes
     */
    public GrilleC(int x, int y) {
        super(x, y) ;
    }

    /**
     * Retourne la liste des cases voisines d'une case donnée.
     * <p>
     * Pour GrilleC, toutes les cases adjacentes (y compris diagonales) sont considérées.
     *
     * @param i indice de la colonne de la case
     * @param j indice de la ligne de la case
     * @return liste des cases voisines
     */
    protected ArrayList<Case> getVoisins(int i, int j) {

        // Tableau des voisins
        ArrayList<Case> res = new ArrayList<>() ;

        // Itération sur toutes les cases voisines
        for (int dx = -1 ; dx <= 1 ; dx++) {
            for (int dy = -1 ; dy <= 1 ; dy++) {

                // Ne rien faire pour la case actuelle
                if (dx == 0 && dy == 0) {
                    continue ;
                }

                // Coordonnées des voisins
                int nx = i + dx ;
                int ny = j + dy ;

                // Ajoute le voisin s'il est dans la grille
                if (nx >= 0 && ny >= 0 && nx < x && ny < y) {
                    res.add(tab[nx][ny]) ;
                }
            }
        }

        // Renvoie le tableau des voisins
        return res ;
    }

    /**
     * Retourne la liste des coordonnées des cases voisines d'une case donnée.
     * <p>
     * Pour GrilleC, toutes les cases adjacentes (y compris diagonales) sont considérées.
     *
     * @param i indice de la colonne de la case
     * @param j indice de la ligne de la case
     * @return liste des coordonnées des cases voisines sous forme de tableau [x, y]
     */
    protected ArrayList<int[]> getCoordVoisins(int i, int j) {

        // Tableau des coordonnées des voisins
        ArrayList<int[]> res = new ArrayList<>() ;

        for (int dx = -1 ; dx <= 1 ; dx++) {
            for (int dy = -1 ; dy <= 1 ; dy++) {

                // Ne rien faire pour la case actuelle
                if (dx == 0 && dy == 0) {
                    continue ;
                }

                // Coordonnées des voisins
                int nx = i + dx ;
                int ny = j + dy ;

                // Ajoute les coordonnées du voisin si le voisin est dans la grille
                if (nx >= 0 && ny >= 0 && nx < x && ny < y) {
                    res.add(new int[]{nx,ny}) ;
                }
            }
        }

        // Renvoie le tableau des coordonnées des voisins
        return res ;
    }
}
