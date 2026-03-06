package src.com.projet.MVC.Vue_Controller;
import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;

import src.com.projet.MVC.Modele.EnumCase;
import src.com.projet.MVC.Modele.GrilleH;
import src.com.projet.MVC.Modele.Jeu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MF extends JFrame implements Observer {

    GrilleH grille = new GrilleH(10,10);
    boolean PremierClic = true;

    public MF(Jeu j) {
        build(j) ;
        setTitle("Démineur") ;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        pack() ;
        setLocationRelativeTo(null) ;
    }

    public void update(Observable o, Object jeu) {
        Jeu j = (Jeu) jeu;
        grille.tab[j.i][j.j].setBackground(Color.GRAY);
    }

    public void build(Jeu jeu) {
        int offsetX = 42;
        int offsetY = 0;
        JPanel pp = new JPanel(new GridLayout(grille.getX(), grille.getY()));
        for (int i = 0; i < grille.getX(); i++) {
            for (int j = 0; j < grille.getY(); j++) {
                int x = i;
                int y = j;
    
                grille.tab[i][j].addMouseListener(new MouseAdapter() {
                   @Override
                   public void mouseClicked(MouseEvent e) {
                    if (PremierClic) {
                        PremierClic = false;
                        grille.setMine(x, y, 10);
                    }
                    jeu.set(x, y);
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        grille.updateGrille(grille.tab[x][y]);

                        if (grille.tab[x][y].value == EnumCase.MINE) {
                            grille.Finpartie();
                            SimpleUI fenetre = new SimpleUI(e); // GAME OVER
                            fenetre.setAlwaysOnTop(true);
                            fenetre.setVisible(true);
                        } else if (grille.victoire()) {
                            SimpleUI winFenetre = new SimpleUI("WIN ! Tu as tout gagné !\nQuelle prouesse !", MF.this);
                            winFenetre.setAlwaysOnTop(true);
                            winFenetre.setVisible(true);
                        }
                    }
                    else if( SwingUtilities.isRightMouseButton(e) ) {
                        if( grille.tab[x][y].value != EnumCase.EST_CLICK){
                            if( grille.tab[x][y].getIcon() == null ){
                                ImageIcon flagIcon = new ImageIcon(getClass().getResource("drapeau.png"));
                                Image imgflag = flagIcon.getImage();
                                Image scaledflag = imgflag.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                                flagIcon = new ImageIcon(scaledflag);
                                grille.tab[x][y].setIcon(flagIcon);
                            }
                            else{
                                grille.tab[x][y].setIcon(null);
                                grille.tab[x][y].j.setBackground(null);
                            }
                        }
                    }
                }
                });

                pp.add(grille.tab[i][j].j);


                grille.tab[i][j].setBounds(offsetY, offsetX, 105, 95);

                offsetX += 87;
            
            }     
            if(i%2 == 0) {
                offsetX = 0;
            } else {
                offsetX = 42;
            }
            offsetY += 76;          
        
        }
        this.add(pp);
    }    
}
