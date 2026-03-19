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
        boolean estHex = (jeu.getGrille() instanceof GrilleH);

        int x = j.getGrille().getX();
        int y = j.getGrille().getY();
        int taille = 50;

        boutons = new JButton[x][y];

        setLayout(null);

        for(int i=0;i<x;i++)
        for(int j2=0;j2<y;j2++){

            JButton b = new JButton();
            if(estHex){
                b = new HexButton();
            } else {
                b = new JButton();
            }
            boutons[i][j2] = b;

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

            int posX, posY;

            if(estHex){
                int decalageX = taille / 2;

                posX = j2 * taille;
                posY = i * (int)(taille * 0.75);

                if(i % 2 == 1){
                    posX += decalageX;
                }
            } else {
                posX = j2 * taille;
                posY = i * taille;
            }

            b.setBounds(posX, posY, taille, taille);

            add(b);
        }

        setTitle("Demineur");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int decalageX = taille / 2;

        // largeur réelle = colonnes + décalage possible sur une ligne impaire
        int largeur = y * taille + decalageX;

        // hauteur inchangée (déjà inclut le 0.75)
        int hauteur = (int)(x * taille * 0.75 + taille);

        getContentPane().setPreferredSize(new Dimension(largeur, hauteur));
        pack();
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                redimensionner();
            }
        });
        setVisible(true);
        redimensionner();
    }

    public void update(Observable o,Object arg){

        if(arg != null && arg.equals("REBUILD")){
            redimensionner();
            return;
        }

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

    private void redimensionner() {

        Grille g = jeu.getGrille();
        boolean estHex = (g instanceof GrilleH);

        int x = g.getX();
        int y = g.getY();

        int largeurFenetre = getContentPane().getWidth();
        int hauteurFenetre = getContentPane().getHeight();

        int taille;

        if(estHex){

            int tailleX = largeurFenetre / (y + 1);
            int tailleY = (int)(hauteurFenetre / (x * 0.75 + 1));

            taille = Math.min(tailleX, tailleY);

            int decalageX = taille / 2;
            int largeurGrille = (int)(y * taille + decalageX);
            int offsetX = (largeurFenetre - largeurGrille) / 2;

            for(int i = 0; i < x; i++){
                for(int j = 0; j < y; j++){

                    JButton b = boutons[i][j];

                    int posX = offsetX + j * taille;
                    int posY = (int)(i * taille * 0.75);

                    if(i % 2 == 1){
                        posX += decalageX;
                    }

                    b.setBounds(posX, posY, taille, taille);
                }
            }

        } else {

            int tailleX = largeurFenetre / y;
            int tailleY = hauteurFenetre / x;

            taille = Math.min(tailleX, tailleY);

            int offsetX = (largeurFenetre - y * taille) / 2;
            int offsetY = (hauteurFenetre - x * taille) / 2;

            for(int i = 0; i < x; i++){
                for(int j = 0; j < y; j++){

                    JButton b = boutons[i][j];

                    int posX = offsetX + j * taille;
                    int posY = offsetY + i * taille;

                    b.setBounds(posX, posY, taille, taille);
                }
            }
        }

        repaint();
    }
}
