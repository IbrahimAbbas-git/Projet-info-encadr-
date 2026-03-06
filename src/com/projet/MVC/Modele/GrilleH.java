package src.com.projet.MVC.Modele;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;

import java.awt.*;

public class GrilleH implements Grille{
    public CaseHexa[][] tab;
    HashMap<CaseHexa,Point> map = new HashMap<>();

    public GrilleH(int xmax , int ymax){
        tab = new CaseHexa[xmax][ymax];
        map = new HashMap<CaseHexa,Point>();
        for (int i = 0; i < xmax; i++) {
            for (int j = 0; j < ymax; j++) {
                    tab[i][j] = new CaseHexa(i,j);
                    tab[i][j].set(EnumCase.EST_VIDE);
                map.put(tab[i][j], new Point(i,j));
            }
        }
    }
    public void setMine(int i, int j, int nbMine){
        ArrayList<Point> mine = new ArrayList<Point>();
        for(int k = 0;k<nbMine;k++){
            int rand_x, rand_y;
            do {
                Random random = new Random();
                rand_x = random.nextInt(10);
                rand_y = random.nextInt(10);
            } while (mine.contains(new Point(rand_x,rand_y))  || (i-1<=rand_x && rand_x<=i+1 && rand_y <=j+1 && rand_y >=j-1));
            mine.add(new Point(rand_x,rand_y));
            tab[rand_x][rand_y].set(EnumCase.MINE);
        }
    }
    public ArrayList<CaseHexa> getVoisin(CaseHexa c){
        Point p = map.get(c);
        ArrayList<CaseHexa> res = new ArrayList<CaseHexa>();
        if(p.x > 0){res.add(tab[p.x - 1][p.y]);}
        if(p.y > 0 ){res.add(tab[p.x][p.y - 1]);}
        if(p.x < tab.length-1){res.add(tab[p.x + 1][p.y]);}
        if(p.y < tab[0].length-1){res.add(tab[p.x][p.y + 1]);}
        if(p.x <tab.length - 1 && p.y !=0 ){res.add(tab[p.x + 1][p.y - 1]);}
        if(p.x <tab.length - 1 && p.y <tab[0].length - 1 ){res.add(tab[p.x + 1][p.y + 1]);}
        if(p.y <tab[0].length - 1 && p.x !=0 ){res.add(tab[p.x - 1][p.y + 1]);}
        if(p.x !=0 && p.y !=0 ){res.add(tab[p.x - 1][p.y - 1]);}
        return res;
    }
    public void updateGrille(CaseHexa c){
        Point p = map.get(c);
        if(tab[p.x][p.y].value == EnumCase.EST_CLICK) {
            return;
        }
        if (tab[p.x][p.y].value == EnumCase.MINE) {
            ImageIcon bombIcon = new ImageIcon(getClass().getResource("bombe.png"));
                Image imgbombe = bombIcon.getImage();
                Image scaledbombe = imgbombe.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                bombIcon = new ImageIcon(scaledbombe);

                c.setIcon(bombIcon);
                c.j.setBackground(Color.GRAY);
                c.j.setDisabledIcon(bombIcon);
                c.j.setText("");      // vide pour enlever le texte
                c.j.setEnabled(false);        // bloque le bouton
            return ;
        }
        tab[p.x][p.y].set(EnumCase.EST_CLICK);
        ArrayList<CaseHexa> voisins = getVoisin(c);
        int nb_mines = 0;
        for(CaseHexa v : voisins){
            if(v.value == EnumCase.MINE){
                nb_mines++;
            }
        }
        if(nb_mines > 0){
            ImageIcon nbIcon = new ImageIcon(getClass().getResource(nb_mines + ".png"));
            Image img = nbIcon.getImage();
            Image scaled = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            nbIcon = new ImageIcon(scaled);

            c.setIcon(nbIcon);
            c.j.setDisabledIcon(nbIcon);
            c.j.setBackground(Color.GRAY);
            c.j.setEnabled(false);
            return;
        }
        else{
            c.setText("");
            c.j.setBackground(Color.GRAY);
            c.j.setEnabled(false);
        }
        for(CaseHexa v : voisins){
            if(v.value == EnumCase.EST_VIDE){
                updateGrille(v);
            }
        }
    }

    public void Finpartie(){
        for(int i = 0;i < tab.length;i++){
            for(int j = 0;j < tab[0].length;j++){
                if(tab[i][j].value == EnumCase.MINE){
                    tab[i][j].setEnabled(false);
                    updateGrille(tab[i][j]);
                }
            }
        }
    }

    public CaseHexa getCase(int i , int j){return tab[i][j];}
    public void setCase(int i, int j,EnumCase e){tab[i][j].set(e);}
    public int getX(){return tab.length;}
    public int getY(){return tab[0].length;}
    public boolean victoire() {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                CaseHexa c = tab[i][j];
                if (c.value != EnumCase.MINE && c.value != EnumCase.EST_CLICK) {
                    return false; // Il reste au moins une case sûre non cliquée
                }
            }
        }
        return true; // Toutes les cases sûres sont découvertes
    }
}
