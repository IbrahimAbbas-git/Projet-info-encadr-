import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

public class GrilleC implements Grille{
    public Case[][] tab;
    HashMap<Case,Point> map = new HashMap<>();
    public GrilleC(int xmax , int ymax){
        tab = new Case[xmax][ymax];
        for (int i = 0; i < xmax; i++) {
            for (int j = 0; j < ymax; j++) {
                tab[i][j]= new Case();
                map.put(tab[i][j], new Point(i,j));
            }
        }
    }
    public ArrayList<Case> getVoisin(Case c){
        Point p = map.get(c);
        ArrayList<Case> res = new ArrayList<Case>();
        if(p.x != 0){res.add(tab[p.x - 1][p.y]);}
        if(p.y !=0 ){res.add(tab[p.x][p.y - 1]);}
        if(p.x < tab.length-1){res.add(tab[p.x + 1][p.y]);}
        if(p.y < tab[0].length-1){res.add(tab[p.x][p.y + 1]);}
        if(p.x <tab.length - 1 && p.y !=0 ){res.add(tab[p.x + 1][p.y - 1]);}
        if(p.x <tab.length - 1 && p.y <tab[0].length - 1 ){res.add(tab[p.x + 1][p.y + 1]);}
        if(p.y <tab[0].length - 1 && p.x !=0 ){res.add(tab[p.x - 1][p.y + 1]);}
        if(p.x !=0 && p.y !=0 ){res.add(tab[p.x - 1][p.y - 1]);}
        return res;
    }
    public Case getCase(int i , int j){return tab[i][j];}
    public void setCase(int i, int j,EnumCase e){tab[i][j].set(e);}
}
