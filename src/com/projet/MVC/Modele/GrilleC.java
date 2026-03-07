package src.com.projet.MVC.Modele;

import java.util.ArrayList;

public class GrilleC extends Grille{

    public GrilleC(int x,int y){
        super(x,y);
    }

    protected ArrayList<Case> getVoisins(int i,int j){

        ArrayList<Case> res = new ArrayList<>();

        for(int dx=-1;dx<=1;dx++)
        for(int dy=-1;dy<=1;dy++){

            if(dx==0 && dy==0) continue;

            int nx = i+dx;
            int ny = j+dy;

            if(nx>=0 && ny>=0 && nx<x && ny<y)
                res.add(tab[nx][ny]);
        }

        return res;
    }

    protected ArrayList<int[]> getCoordVoisins(int i,int j){

        ArrayList<int[]> res = new ArrayList<>();

        for(int dx=-1;dx<=1;dx++)
        for(int dy=-1;dy<=1;dy++){

            if(dx==0 && dy==0) continue;

            int nx = i+dx;
            int ny = j+dy;

            if(nx>=0 && ny>=0 && nx<x && ny<y)
                res.add(new int[]{nx,ny});
        }

        return res;
    }
}
