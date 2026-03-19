package src.com.projet.MVC.Modele;

import java.util.ArrayList;

public class GrilleH extends Grille{

    public GrilleH(int x,int y){
        super(x,y);
    }

    protected ArrayList<Case> getVoisins(int i,int j){

        ArrayList<Case> res = new ArrayList<>();

        for(int[] v : getCoordVoisins(i,j)){
            res.add(tab[v[0]][v[1]]);
        }

        return res;
    }

    protected ArrayList<int[]> getCoordVoisins(int i,int j){

        ArrayList<int[]> res = new ArrayList<>();

        // voisins pour lignes paires
        int[][] voisinsPairs = {
            {-1, 0}, {-1, -1},
            {0, -1}, {0, 1},
            {1, 0}, {1, -1}
        };

        // voisins pour lignes impaires
        int[][] voisinsImpairs = {
            {-1, 1}, {-1, 0},
            {0, -1}, {0, 1},
            {1, 1}, {1, 0}
        };

        int[][] voisins;

        if(i % 2 == 0){
            voisins = voisinsPairs;
        } else {
            voisins = voisinsImpairs;
        }

        for(int[] v : voisins){

            int nx = i + v[0];
            int ny = j + v[1];

            if(nx>=0 && ny>=0 && nx<x && ny<y)
                res.add(new int[]{nx, ny});
        }

        return res;
    }
}
