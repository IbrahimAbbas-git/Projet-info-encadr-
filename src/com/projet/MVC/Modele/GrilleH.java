package src.com.projet.MVC.Modele;

import java.util.ArrayList;

public class GrilleH extends Grille{

    public GrilleH(int x,int y){
        super(x,y);
    }

    protected ArrayList<Case> getVoisins(int i,int j){

        ArrayList<Case> res = new ArrayList<>();

        int[][] dirs = {
            {-1,0},{1,0},
            {0,-1},{0,1},
            {-1,1},{1,-1}
        };

        for(int[] d : dirs){

            int nx = i+d[0];
            int ny = j+d[1];

            if(nx>=0 && ny>=0 && nx<x && ny<y)
                res.add(tab[nx][ny]);
        }

        return res;
    }

    protected ArrayList<int[]> getCoordVoisins(int i,int j){

        ArrayList<int[]> res = new ArrayList<>();

        int[][] dirs = {
            {-1,0},{1,0},
            {0,-1},{0,1},
            {-1,1},{1,-1}
        };

        for(int[] d : dirs){

            int nx = i+d[0];
            int ny = j+d[1];

            if(nx>=0 && ny>=0 && nx<x && ny<y)
                res.add(new int[]{nx,ny});
        }

        return res;
    }
}
