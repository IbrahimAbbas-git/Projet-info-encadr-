import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.awt.Point;

public class GrilleC implements Grille{
    public Case[][] tab;
    HashMap<Case,Point> map;
    public GrilleC(int xmax , int ymax){
        tab = new Case[xmax][ymax];
        map = new HashMap<Case,Point>();
        //for(int k = 0;k<nbMine;k++){
            //do {
                //Random random = new Random();
            //    int rand_x = random.nextInt(10);
              //  int rand_y = random.nextInt(10);
          //  } while ((rand_x,rand_y)is in mine);
         
        //}
        for (int i = 0; i < xmax; i++) {
            for (int j = 0; j < ymax; j++) {
                tab[i][j]= new Case();
                //tab[i][j].set(EnumCase.MINE);
                map.put(tab[i][j], new Point(i,j));
            }
        }
    }
    public ArrayList<Case> getVoisin(Case c){
        Point p = map.get(c);
        ArrayList<Case> res = new ArrayList<Case>();
        if(p.x > 0){res.add(tab[p.x - 1][p.y]);}
        if(p.y > 0 ){res.add(tab[p.x][p.y - 1]);}
        if(p.x < tab.length-1){res.add(tab[p.x + 1][p.y]);}
        if(p.y < tab.length-1){res.add(tab[p.x][p.y + 1]);}
        if(p.x <tab.length - 1 && p.y >0 ){res.add(tab[p.x + 1][p.y - 1]);}
        if(p.x <tab.length - 1 && p.y <tab.length - 1 ){res.add(tab[p.x + 1][p.y + 1]);}
        if(p.x >0 && p.y < tab.length - 1 ){res.add(tab[p.x - 1][p.y + 1]);}
        if(p.x >0 && p.y >0 ){
            res.add(tab[p.x - 1][p.y - 1]);
        }
        return res;
    }
    public void updateGrille(Case c){
        Point p = map.get(c);
        tab[p.x][p.y].value=EnumCase.EST_CLICK;
        ArrayList<Case> voisin = getVoisin(c);
        int nb_mines = 0; 
        for(int i = 0;i < voisin.size(); i++){
            if(voisin.get(i).value == EnumCase.MINE){
                nb_mines++;
            }
            else{
                if(voisin.get(i).value !=EnumCase.EST_CLICK ){
                    voisin.get(i).j.doClick();
                }
            }
            
        }
        if(c.value != EnumCase.MINE){
            //TODO changer pour mettre le numero nb_mines
        }
    }
    public Case getCase(int i , int j){return tab[i][j];}
    public void setCase(int i, int j,EnumCase e){tab[i][j].set(e);}
}
