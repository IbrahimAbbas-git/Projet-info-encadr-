package src.com.projet.MVC.Modele;

import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

public abstract class Grille implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Case[][] tab;
    protected int x;
    protected int y;

    public Grille(int x,int y){

        this.x = x;
        this.y = y;

        tab = new Case[x][y];

        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++)
                tab[i][j] = new Case();
    }

    public Case getCase(int i,int j){
        return tab[i][j];
    }

    public int getX(){ return x; }
    public int getY(){ return y; }

    public void placerMines(int safeX,int safeY,int nb){

        Random r = new Random();
        int count = 0;

        while(count < nb){

            int rx = r.nextInt(x);
            int ry = r.nextInt(y);

            if(tab[rx][ry].mine) continue;

            if(Math.abs(rx-safeX)<=1 && Math.abs(ry-safeY)<=1)
                continue;

            tab[rx][ry].mine = true;
            count++;
        }

        calculVoisins();
    }

    protected void calculVoisins(){

        for(int i=0;i<x;i++)
        for(int j=0;j<y;j++){

            if(tab[i][j].mine) continue;

            int n = 0;

            for(Case c : getVoisins(i,j))
                if(c.mine) n++;

            tab[i][j].nbVoisins = n;
        }
    }

    public void reveler(int i,int j){

        Case c = tab[i][j];

        if(c.revelee || c.drapeau)
            return;

        c.revelee = true;

        if(c.nbVoisins != 0)
            return;

        for(int[] v : getCoordVoisins(i,j))
            reveler(v[0],v[1]);
    }

    public boolean victoire(){

        for(int i=0;i<x;i++)
        for(int j=0;j<y;j++)
            if(!tab[i][j].mine && !tab[i][j].revelee)
                return false;

        return true;
    }

    protected abstract ArrayList<Case> getVoisins(int i,int j);

    protected abstract ArrayList<int[]> getCoordVoisins(int i,int j);

    public void revelerToutesLesMines(){
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                if(tab[i][j].mine)
                    tab[i][j].revelee = true;
            }
        }
    }
}
