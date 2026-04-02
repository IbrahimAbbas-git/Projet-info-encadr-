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
    JPanel mainPanel = new JPanel(null); // panel pour tout placer manuellement

    public MF(Jeu j){

        this.jeu = j;
        boolean estHex = (jeu.getGrille() instanceof GrilleH);

        int x = j.getGrille().getX();
        int y = j.getGrille().getY();
        int taille = 50;
        System.out.println("Taille de la grille : " + x + "x" + y);
        boutons = new JButton[x][y];

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
            mainPanel.add(b);
        }

        JButton saveButton = new JButton("Sauvegarder");
        saveButton.setBounds(10, 10, 150, 40);
        mainPanel.add(saveButton);

        saveButton.addActionListener(e -> {
            jeu.sauvegarder();
            JOptionPane.showMessageDialog(this, "Partie sauvegardée !");
        });

        JButton menuButton = new JButton("Retour menu");
        menuButton.setBounds(170, 10, 200, 40);
        mainPanel.add(menuButton);

        menuButton.addActionListener(e -> {
            jeu.sauvegarder();

            JOptionPane.showMessageDialog(this, "Partie sauvegardée !");

            dispose();
            new Jeu();
        });

        setTitle("Demineur");

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.LIGHT_GRAY); 
        JLabel minesLabel = new JLabel(" 🚩 Mines : " + jeu.getNbMines() + " ");
        minesLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton resetButton = new JButton("🔄 Recommencer");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 14));
        resetButton.addActionListener(e -> {
            // Supprimer la sauvegarde existante si elle existe
            jeu.supprimerSauvegarde();

            // Récupérer les paramètres actuels
            int X = jeu.getTailleX();
            int Y = jeu.getTailleY();
            int nbMines = jeu.getNbMines();

            // Créer un nouveau jeu avec les mêmes paramètres
            Jeu nouveauJeu = new Jeu(X, Y, nbMines, estHex);

            // Créer une nouvelle fenêtre MF pour le nouveau jeu
            MF nouvelleFenetre = new MF(nouveauJeu);

            // Ajouter la nouvelle vue comme observateur du nouveau jeu
            nouveauJeu.addObserver(nouvelleFenetre);

            // Fermer l’ancienne fenêtre
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
            int[] indice = jeu.indice();
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
                    button.setBackground(Color.GRAY);
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

        int espaceBoutons = 70; // hauteur réservée pour les boutons
        // largeur réelle = colonnes + décalage possible sur une ligne impaire
        int largeur = y * taille + decalageX;
        // hauteur inchangée (déjà inclut le 0.75)
        int hauteur = (int)(x * taille * 0.75 + taille);
        mainPanel.setPreferredSize(new Dimension(largeur, hauteur + espaceBoutons));
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setContentPane(scrollPane);

        saveButton.setBounds(10, hauteur + 10, 150, 40);  // 10px en dessous de la grille
        menuButton.setBounds(170, hauteur + 10, 200, 40);

        getContentPane().revalidate();
        getContentPane().repaint();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(Math.min(largeur, (int)(screenSize.width * 0.9)), Math.min(hauteur + espaceBoutons, (int)(screenSize.height * 0.9)));
        pack();
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                redimensionner();
            }
        });
        update(jeu, "REBUILD");
        setVisible(true);
        redimensionner();
    }

    public void update(Observable o,Object arg){

        if(arg != null && arg.equals("REBUILD")){
            mettreAJourBoutons();
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
                ImageIcon bombIcon = new ImageIcon(getClass().getResource("/img/bombe.png"));
                Image img = bombIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                bombIcon = new ImageIcon(img);
                b.setText("");
                b.setIcon(bombIcon);
                b.setBackground(Color.WHITE);
                b.revalidate();
                b.repaint();
            }
            else if(c.nbVoisins > 0){
                ImageIcon numIcon = new ImageIcon(getClass().getResource("/img/" + c.nbVoisins + ".png"));
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

    private void mettreAJourBoutons() {
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
                b.setBackground(Color.yellow);
            }
            else if(!c.revelee){
                b.setText("");
                b.setIcon(null);
                b.setBackground(Color.gray);
            }
            else if(c.mine){
                ImageIcon bombIcon = new ImageIcon(getClass().getResource("/img/bombe.png"));
                Image img = bombIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                bombIcon = new ImageIcon(img);
                b.setText("");
                b.setIcon(bombIcon);
                b.setBackground(Color.WHITE);
            }
            else if(c.nbVoisins > 0){
                ImageIcon numIcon = new ImageIcon(getClass().getResource("/img/" + c.nbVoisins + ".png"));
                Image img = numIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                numIcon = new ImageIcon(img);
                b.setText("");
                b.setIcon(numIcon);
                b.setBackground(Color.WHITE); 
            }
            else{
                b.setBackground(Color.WHITE);
                b.setText("");
                b.setIcon(null);
            }
        }
    }
}
