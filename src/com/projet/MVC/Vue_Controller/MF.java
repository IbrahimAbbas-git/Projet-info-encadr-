package src.com.projet.MVC.Vue_Controller;

import java.util.Observer;
import java.util.Observable;

import javax.swing.*;
import java.awt.*;

import src.com.projet.MVC.Modele.*;

public class MF extends JFrame implements Observer {

    private Jeu jeu;
    private JButton[][] boutons;

    public MF(Jeu j){

        this.jeu = j;

        int x = j.getGrille().getX();
        int y = j.getGrille().getY();

        boutons = new JButton[x][y];

        setLayout(new GridLayout(x,y));

        for(int i=0;i<x;i++)
        for(int j2=0;j2<y;j2++){

            JButton b = new JButton();
            boutons[i][j2] = b;
            b.setPreferredSize(new Dimension(50, 50)); // taille fixe par bouton

            int xi = i;
            int yj = j2;

            b.addMouseListener(new java.awt.event.MouseAdapter(){

                public void mouseClicked(java.awt.event.MouseEvent e){

                    if(SwingUtilities.isLeftMouseButton(e))
                        jeu.clickGauche(xi,yj);
                    else
                        jeu.clickDroit(xi,yj);

                }
            });

            add(b);
        }

        pack();
        setTitle("Demineur");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void update(Observable o,Object arg){

        Grille g = jeu.getGrille();

        for(int i=0;i<g.getX();i++)
        for(int j=0;j<g.getY();j++){

            Case c = g.getCase(i,j);
            JButton b = boutons[i][j];

            if(c.drapeau){
                ImageIcon flagIcon = new ImageIcon(getClass().getResource("/img/drapeau.png"));
                Image img = flagIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                flagIcon = new ImageIcon(img);
                b.setText("");
                b.setIcon(flagIcon);
                b.revalidate();
                b.repaint();
            }
            else if(!c.revelee){
                b.setText("");
                b.setIcon(null);
                b.setBackground(null); // ou couleur par défaut
            }
            else if(c.mine){
                ImageIcon bombIcon = new ImageIcon(getClass().getResource("/img/bombe.png"));
                Image img = bombIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                bombIcon = new ImageIcon(img);
                b.setText("");
                b.setIcon(bombIcon);
                b.revalidate();
                b.repaint();
            }
            else if(c.nbVoisins > 0){
                ImageIcon numIcon = new ImageIcon(getClass().getResource("/img/" + c.nbVoisins + ".png"));
                Image img = numIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                numIcon = new ImageIcon(img);
                b.setText("");
                b.setIcon(numIcon);
                b.setBackground(Color.GRAY); // <-- ajouté
                b.revalidate();
                b.repaint();
            }
            else{
                b.setText("");
                b.setIcon(null);
                b.setBackground(Color.GRAY); // <-- cases vides révélées aussi
            }

        }

        if(jeu.isPerdu()){
            SimpleUI gameOver = new SimpleUI(this); // fenetre modale
            gameOver.setVisible(true);
        }

        if(jeu.isGagne()){
            SimpleUI win = new SimpleUI("VICTOIRE ! Tu as gagné !", this);
            win.setVisible(true);
        }
    }
}
