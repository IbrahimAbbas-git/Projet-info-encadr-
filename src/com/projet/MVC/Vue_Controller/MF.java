package src.com.projet.MVC.Vue_Controller;

import java.util.Observer;
import java.util.Observable;

import javax.swing.*;
import javax.swing.border.LineBorder;

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
        System.out.println("Taille de la grille : " + x + "x" + y);
        boutons = new JButton[x][y];

        setLayout(null);

        for(int i=0;i<x;i++)
        for(int j2=0;j2<y;j2++){

            JButton b = new JButton();
            b.setBackground(Color.BLACK);
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

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.LIGHT_GRAY); 
        JLabel minesLabel = new JLabel(" 🚩 Mines : " + jeu.getNbMines() + " ");
        minesLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton resetButton = new JButton("🔄 Recommencer");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 14));
        resetButton.addActionListener(e -> {
            int X = jeu.getTailleX();
            int Y = jeu.getTailleY();
            int nbMines = jeu.getNbMines();
            boolean esthex = jeu.getGrille() instanceof GrilleH;
            jeu = new Jeu(X, Y, nbMines, esthex);
            dispose();
        });

        JButton quitButton = new JButton("❌ Quitter");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        quitButton.addActionListener(e -> {
            dispose();
            jeu = new Jeu();         
        });
        JButton indiceButton = new JButton("💡 Indice");
        indiceButton.setFont(new Font("Liberation Serif", Font.PLAIN, 14));
        indiceButton.addActionListener(e -> {
            int[] indice = jeu.indice(boutons);
            if (indice != null) {
                JButton button = boutons[indice[0]][indice[1]];
                if(indice[2] == 1){
                    button.setBorder(new LineBorder(Color.GREEN, 3));
                    button.setBackground(Color.GREEN);
                } else {
                button.setBorder(new LineBorder(Color.BLUE, 3));
                button.setBackground(Color.BLUE);
                }
                Timer timer = new Timer(1000, event -> {
                    button.setBorder(UIManager.getBorder("Button.border"));
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                   indiceButton.setText("pas d'indice");
                   Timer t = new Timer(1000, event -> {
                       indiceButton.setText("💡 Indice");
                   });
                   t.setRepeats(false);
                   t.start();
            }
        });
        menuBar.add(minesLabel);
        menuBar.add(resetButton);
        menuBar.add(quitButton);
        menuBar.add(indiceButton);
        add(menuBar);
        this.setJMenuBar(menuBar);
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
                ImageIcon flagIcon = new ImageIcon(getClass().getResource("/src/img/drapeau.png"));
                Image img = flagIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                flagIcon = new ImageIcon(img);
                b.setText("");
                b.setIcon(flagIcon);
                b.setBackground(Color.yellow);
                b.revalidate(); 
                b.repaint();
            }
            else if(!c.revelee){
                b.setText("");
                b.setIcon(null);
                b.setBackground(Color.gray); // ou couleur par défaut
            }
            else if(c.mine){
                ImageIcon bombIcon = new ImageIcon(getClass().getResource("/src/img/bombe.png"));
                Image img = bombIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                bombIcon = new ImageIcon(img);
                b.setText("");
                b.setIcon(bombIcon);
                b.setBackground(Color.WHITE);
                b.revalidate();
                b.repaint();
            }
            else if(c.nbVoisins > 0){
                ImageIcon numIcon = new ImageIcon(getClass().getResource("/src/img/" + c.nbVoisins + ".png"));
                Image img = numIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                numIcon = new ImageIcon(img);
                b.setText("");
                b.setIcon(numIcon);
                b.setBackground(Color.WHITE); 
                b.revalidate();
                b.repaint();
            }
            else{
                b.setBackground(Color.WHITE);
                b.setText("");
                b.setIcon(null);
            }
        }

        if(jeu.isPerdu()){
            SimpleUI gameOver = new SimpleUI(this,"GAME OVER !","Tu es tombé sur une mine !");
            gameOver.setVisible(true);
        }

        if(jeu.isGagne()){
            SimpleUI win = new SimpleUI(this,"VICTOIRE !","Félicitations !");
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
