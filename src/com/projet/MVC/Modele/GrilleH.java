package com.projet.MVC.Modele ;

import java.util.ArrayList ;

/**
 * GrilleH représente une grille de démineur de type hexagonal.
 * <p>
 * Les cases ont jusqu'à 6 voisins selon leur ligne (paires ou impaires).
 * <p>
 * Cette classe étend la classe abstraite {@link Grille}.
 */
public class GrilleH extends Grille {

    /**
     * Constructeur de la grille hexagonale.
     * <p>
     * Initialise une grille de dimensions x * y.
     *
     * @param x nombre de colonnes
     * @param y nombre de lignes
     */
    public GrilleH(int x, int y){
        super(x, y) ;
    }

    /**
     * Retourne la liste des cases voisines d'une case donnée.
     * <p>
     * Pour GrilleH, les voisins sont déterminés selon la disposition hexagonale.
     *
     * @param i indice de la colonne de la case
     * @param j indice de la ligne de la case
     * @return liste des cases voisines
     */
    protected ArrayList<Case> getVoisins(int i, int j){

        // Tableau des voisins
        ArrayList<Case> res = new ArrayList<>() ;

        // Ajout du voisin pour chaque coordonnée de voisin déjà calculée
        for (int[] v : getCoordVoisins(i, j)) {
            res.add(tab[v[0]][v[1]]) ;
        }

        // Retourne le tableau des voisins
        return res ;
    }

    /**
     * Retourne la liste des coordonnées des cases voisines d'une case donnée.
     * <p>
     * Pour GrilleH, les voisins sont calculés différemment selon que la ligne est paire ou impaire pour respecter la structure hexagonale.
     *
     * @param i indice de la colonne de la case
     * @param j indice de la ligne de la case
     * @return liste des coordonnées des cases voisines sous forme de tableau [x, y]
     */
    protected ArrayList<int[]> getCoordVoisins(int i,int j){

        // Tableau des coordonnées des voisins
        ArrayList<int[]> res = new ArrayList<>() ;

        // Voisins pour les lignes paires
        int[][] voisinsPairs = {
            {-1, 0}, {-1, -1},
            {0, -1}, {0, 1},
            {1, 0}, {1, -1}
        } ;

        // Voisins pour les lignes impaires
        int[][] voisinsImpairs = {
            {-1, 1}, {-1, 0},
            {0, -1}, {0, 1},
            {1, 1}, {1, 0}
        } ;

        // Tableau des voisins
        int[][] voisins ;

        // Affectation des voisins selon la parité de la ligne
        if (i % 2 == 0) {
            voisins = voisinsPairs ;
        }
        else {
            voisins = voisinsImpairs ;
        }

        // Itération sur chaque voisin
        for (int[] v : voisins) {

            // Coordonnées des voisins
            int nx = i + v[0] ;
            int ny = j + v[1] ;

            // Si le voisin est dans la grille, ajouter ses coordonnées
            if (nx >= 0 && ny >= 0 && nx < x && ny < y) {
                res.add(new int[]{nx, ny}) ;
            }
        }

        // Renvoie le tableau des coordonnées des voisins
        return res ;
    }
}
