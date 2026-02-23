import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;

import java.awt.*;

public class GrilleC implements Grille{
    public Case[][] tab;
    HashMap<Case,Point> map = new HashMap<>();

    public GrilleC(int xmax , int ymax, int nbMine){
        tab = new Case[xmax][ymax];
        map = new HashMap<Case,Point>();
        ArrayList<Point> mine = new ArrayList<Point>();
        for(int k = 0;k<nbMine;k++){
            int rand_x, rand_y;
            do {
                Random random = new Random();
                rand_x = random.nextInt(10);
                rand_y = random.nextInt(10);
            } while (mine.contains(new Point(rand_x,rand_y)));
            mine.add(new Point(rand_x,rand_y));
            tab[rand_x][rand_y] = new Case();
            tab[rand_x][rand_y].set(EnumCase.MINE);
        }
        for (int i = 0; i < xmax; i++) {
            for (int j = 0; j < ymax; j++) {
                if(tab[i][j] == null){
                    tab[i][j] = new Case();
                    tab[i][j].set(EnumCase.EST_VIDE);
                }
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
        if(p.y < tab[0].length-1){res.add(tab[p.x][p.y + 1]);}
        if(p.x <tab.length - 1 && p.y !=0 ){res.add(tab[p.x + 1][p.y - 1]);}
        if(p.x <tab.length - 1 && p.y <tab[0].length - 1 ){res.add(tab[p.x + 1][p.y + 1]);}
        if(p.y <tab[0].length - 1 && p.x !=0 ){res.add(tab[p.x - 1][p.y + 1]);}
        if(p.x !=0 && p.y !=0 ){res.add(tab[p.x - 1][p.y - 1]);}
        // if(p.y < tab.length-1){res.add(tab[p.x][p.y + 1]);}
        // if(p.x <tab.length - 1 && p.y >0 ){res.add(tab[p.x + 1][p.y - 1]);}
        // if(p.x <tab.length - 1 && p.y <tab.length - 1 ){res.add(tab[p.x + 1][p.y + 1]);}
        // if(p.x >0 && p.y < tab.length - 1 ){res.add(tab[p.x - 1][p.y + 1]);}
        // if(p.x >0 && p.y >0 ){
        //     res.add(tab[p.x - 1][p.y - 1]);
        // }
        return res;
    }
    public void updateGrille(Case c){
        Point p = map.get(c);
        if(tab[p.x][p.y].value != EnumCase.MINE){
            tab[p.x][p.y].set(EnumCase.EST_CLICK);
        }
        else{
            System.out.println("BOOM! Game Over!");
        }
        ArrayList<Case> voisin = getVoisin(c);
        int nb_mines = 0; 
        for(int i = 0;i < voisin.size(); i++){
            if(voisin.get(i).value == EnumCase.MINE){
                nb_mines++;
            }
            if(voisin.get(i).value == EnumCase.EST_VIDE && c.getIcon() == null){
                voisin.get(i).j.doClick();
            }
            
        }
        if(c.value != EnumCase.MINE && nb_mines > 0){
            ImageIcon nbIcon = new ImageIcon(getClass().getResource(nb_mines + ".png"));
            Image img = nbIcon.getImage();
            Image scaled = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            nbIcon = new ImageIcon(scaled);
            c.setIcon(nbIcon);            
        }
    }

public void Finpartie(){
    for(int i = 0;i < tab.length;i++){
        for(int j = 0;j < tab[0].length;j++){
            tab[i][j].j.doClick();
            tab[i][j].setEnabled(false);
        }
    }
}

    public Case getCase(int i , int j){return tab[i][j];}
    public void setCase(int i, int j,EnumCase e){tab[i][j].set(e);}
}
