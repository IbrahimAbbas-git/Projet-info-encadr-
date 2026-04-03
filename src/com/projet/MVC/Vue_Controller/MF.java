package com.projet.MVC.Vue_Controller ;

import java.util.Observer ;
import java.util.Observable ;

import javax.swing.* ;
import javax.swing.border.LineBorder ;

import java.awt.* ;

import com.projet.MVC.Modele.* ;

/**
 * MF représente la fenêtre principale du démineur et sert de vue.
 * <p>
 * Cette classe implémente {@link Observer} pour recevoir les notifications du modèle {@link Jeu} et mettre à jour l'affichage de la grille en conséquence.
 * <p>
 * Elle gère également l'interface utilisateur avec les boutons, le menu et les interactions souris (clic gauche/droit).
 */
public class MF extends JFrame implements Observer {

    /**
     * Jeu comprenant la partie pour jouer
     */
    private Jeu jeu ;

    /**
     * Boutons à afficher
     */    
    private JButton[][] boutons ;

    /**
     * Panel pour tout placer manuellement
     */
    JPanel mainPanel = new JPanel(null) ;

    /**
     * Constructeur principal de la fenêtre.
     * <p>
     * Initialise la grille visuelle avec tous les boutons correspondants aux cases, crée les boutons de sauvegarde, de retour au menu, d'indice et configure le menu de la fenêtre (recommencer, quitter, mines restantes).
     *
     * @param j instance de {@link Jeu} que cette vue observe
     */
    public MF(Jeu j) {
        // Jeu actuel
        this.jeu = j ;
        // Vérifie si la grille est hexagonale ou non
        boolean estHex = (jeu.getGrille() instanceof GrilleH) ;

        // Longueur de la grille
        int x = j.getGrille().getX() ;
        // Largeur de la grille
        int y = j.getGrille().getY() ;
        // Taille de case
        int taille = 50 ;
        // Affichage de la taille de la grille
        System.out.println("Taille de la grille : " + x + "x" + y) ;
        // Créations de boutons pour les cases
        boutons = new JButton[x][y] ;

        // Itération sur toutes les cases
        for (int i = 0 ; i < x ; i++) {
            for (int j2 = 0 ; j2 < y ; j2++) {
                // Création d'un nouveau bouton
                JButton b = new JButton() ;
                // Couleur du bouton => noir
                b.setBackground(Color.BLACK) ;
                // Si la grille est hexagonale, faire des boutons hexagonaux
                if (estHex) {
                    b = new HexButton() ;
                }
                // Sinon, faire des boutons carrés
                else {
                    b = new JButton() ;
                }
                // Affectation du bouton
                boutons[i][j2] = b ;

                // Coordonnées de la case
                int xi = i ;
                int yj = j2 ;

                // Actions dépendant de la souris
                b.addMouseListener(new java.awt.event.MouseAdapter() {
                    // Fonction dépendante du clic de la souris
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        // Si la souris a été cliquée à gauche, lance le processus de clic gauche
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            jeu.clickGauche(xi,yj) ;
                        }
                        // Sinon, lance le processus de clic droit
                        else {
                            jeu.clickDroit(xi,yj) ;
                        } 
                    }
                }) ;

                // Positionnement des cases
                int posX, posY ;

                // Si la grille est hexagonale, positionnement particulier, comprenant un certain décalage et en fonction de la parité de ligne
                if (estHex) {
                    int decalageX = taille / 2 ;

                    posX = j2 * taille ;
                    posY = i * (int)(taille * 0.75) ;

                    if (i % 2 == 1) {
                        posX += decalageX ;
                    }
                }
                // Sinon, positionnement en fonction de la taille et des coordonnées
                else {
                    posX = j2 * taille ;
                    posY = i * taille ;
                }

                // Ajout des sommets de la cases
                b.setBounds(posX, posY, taille, taille) ;
                // Ajout du bouton
                mainPanel.add(b) ;
            }
        }

        // Bouton sauvegarder
        JButton saveButton = new JButton("Sauvegarder") ;
        // Limites du bouton sauvegarder
        saveButton.setBounds(10, 10, 150, 40) ;
        // Ajout du bouton sauvegarder
        mainPanel.add(saveButton) ;

        // Actions du bouton sauvegarder
        saveButton.addActionListener(e -> {
            // Sauvegarde le jeu
            jeu.sauvegarder() ;
            // Ajout d'un message de sauvegarde
            JOptionPane.showMessageDialog(this, "Partie sauvegardée !") ;
        }) ;

        // Bouton retour au menu
        JButton menuButton = new JButton("Retour menu") ;
        // Limites du bouton retour menu
        menuButton.setBounds(170, 10, 200, 40) ;
        // Ajout du bouton retour menu
        mainPanel.add(menuButton) ;

        // Actions du bouton retour menu
        menuButton.addActionListener(e -> {
            // Sauvegarde le jeu
            jeu.sauvegarder() ;
            // Ajout d'un message de sauvegarde
            JOptionPane.showMessageDialog(this, "Partie sauvegardée !") ;
            // Ferme la fenêtre
            dispose() ;
            // Revient au menu principal
            new Jeu() ;
            // Ouvre le menu principal
            SimpleUI menu = new SimpleUI(jeu) ;
            // Rend le menu visible
            menu.setVisible(true) ;
        }) ;

        // Titre de la fenêtre
        setTitle("Demineur") ;

        // Barre de menu
        JMenuBar menuBar = new JMenuBar() ;
        // Couleur de la barre menu
        menuBar.setBackground(Color.LIGHT_GRAY) ; 
        // Label indiquant le nombre de mines
        JLabel minesLabel = new JLabel(" 🚩 Mines : " + jeu.getNbMines() + " ") ;
        // Couleur du label nombre mines
        minesLabel.setFont(new Font("Arial", Font.BOLD, 14)) ;

        // Bouton de recommencement de partie
        JButton resetButton = new JButton("🔄 Recommencer") ;
        // Police du bouton recommencer
        resetButton.setFont(new Font("Arial", Font.PLAIN, 14)) ;
        // Actions du boutons recommencer
        resetButton.addActionListener(e -> {
            // Supprimer la sauvegarde existante
            jeu.supprimerSauvegarde() ;

            // Récupérer les paramètres actuels
            int X = jeu.getTailleX() ;
            int Y = jeu.getTailleY() ;
            int nbMines = jeu.getNbMines() ;

            // Créer un nouveau jeu avec les mêmes paramètres
            Jeu nouveauJeu = new Jeu(X, Y, nbMines, estHex) ;

            // Créer une nouvelle fenêtre MF pour le nouveau jeu
            MF nouvelleFenetre = new MF(nouveauJeu) ;

            // Ajouter la nouvelle vue comme observateur du nouveau jeu
            nouveauJeu.addObserver(nouvelleFenetre) ;

            // Fermer l'ancienne fenêtre
            dispose() ;
        }) ;

        // Bouton quitter
        JButton quitButton = new JButton("❌ Quitter") ;
        // Police bouton quitter
        quitButton.setFont(new Font("Arial", Font.PLAIN, 14)) ;
        // Actions bouton quitter
        quitButton.addActionListener(e -> {
            // Fermer la fenêtre
            dispose() ;
            // Crée un nouveau jeu
            jeu = new Jeu() ; 
        }) ;

        // Bouton d'indice
        JButton indiceButton = new JButton("💡 Indice") ;
        // Police bouton indice
        indiceButton.setFont(new Font("Liberation Serif", Font.PLAIN, 14)) ;
        // Actions bouton indice
        indiceButton.addActionListener(e -> {
            // Récupère les indices
            int[] indice = jeu.indice() ;
            // S'il y a un indice
            if (indice != null) {
                // Bouton pointant sur la case indicée
                JButton button = boutons[indice[0]][indice[1]] ;
                // S'il y a une mine, colorier cette case en vert
                if (indice[2] == 1) {
                    button.setBorder(new LineBorder(Color.GREEN, 3)) ;
                    button.setBackground(Color.GREEN) ;
                }
                // S'il n'y a pas de mine, colorier cette case en bleu
                else {
                    button.setBorder(new LineBorder(Color.BLUE, 3)) ;
                    button.setBackground(Color.BLUE) ;
                }
                // Temps limite d'apparition de la couleur avant de revenir à la couleur initiale
                Timer timer = new Timer(1000, event -> {
                    button.setBorder(UIManager.getBorder("Button.border")) ;
                    button.setBackground(Color.GRAY) ;
                }) ;
                // Ne pas réitérer le timer
                timer.setRepeats(false) ;
                // Commencer le timer
                timer.start() ;
            }
            // S'il n'y a pas d'indice
            else {
                // Changer le texte du bouton en "pas d'indice"
                indiceButton.setText("pas d'indice") ;
                // Temps limite d'apparition avant de revenir à l'état principal
                Timer t = new Timer(1000, event -> {
                    indiceButton.setText("💡 Indice") ;
                }) ;
                // Ne pas réitérer le timer
                t.setRepeats(false) ;
                // Commencer le timer
                t.start() ;
            }
        }) ;
        
        // Ajout du label mines à la barre menu
        menuBar.add(minesLabel) ;
        // Ajout du bouton recommencer à la barre menu
        menuBar.add(resetButton) ;
        // Ajout du bouton quitter à la barre menu
        menuBar.add(quitButton) ;
        // Ajout du bouton indice à la barre menu
        menuBar.add(indiceButton) ;
        // Ajout de la barre menu
        add(menuBar) ;
        // Ajouter la barre menu
        this.setJMenuBar(menuBar) ;
        // Ajuster la position
        setLocationRelativeTo(null) ;
        // Fermer si la fenêtre se ferme
        setDefaultCloseOperation(EXIT_ON_CLOSE) ;

        // Décalage des boutons
        int decalageX = taille / 2 ;
        // Hauteur réservée pour les boutons
        int espaceBoutons = 70 ;
        // Largeur boutons
        int largeur = y * taille + decalageX ;
        // Hauteur boutons
        int hauteur = (int)(x * taille * 0.75 + taille) ;
        // Ajustement de la taille du panel
        mainPanel.setPreferredSize(new Dimension(largeur, hauteur + espaceBoutons)) ;
        
        // Ajout d'un moyen de scroll
        JScrollPane scrollPane = new JScrollPane(mainPanel) ;
        // Ajout d'une barre de scroll horizontale
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) ;
        // Ajout d'une barre de scroll verticale
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED) ;
        // Ajout de la barre de scroll
        setContentPane(scrollPane) ;

        // Positions des sommets du bouton sauvegarde
        saveButton.setBounds(10, hauteur + 10, 150, 40) ;
        // Positions des sommets du bouton menu
        menuButton.setBounds(170, hauteur + 10, 200, 40) ;

        // Réactualiser
        getContentPane().revalidate() ;
        // Repeindre
        getContentPane().repaint() ;
        // Taille de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize() ;
        // Ajustement de la taille en fonction de l'écran
        setSize(Math.min(largeur, (int)(screenSize.width * 0.9)), Math.min(hauteur + espaceBoutons, (int)(screenSize.height * 0.9))) ;
        // Ajustement de la taille
        pack() ;
        // Gestion de la taille
        addComponentListener(new java.awt.event.ComponentAdapter() {
            // Redimensionnement de la taille
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                redimensionner() ;
            }
        }) ;
        // Mise à jour via "REBUILD"
        update(jeu, "REBUILD") ;
        // Rendre visible
        setVisible(true) ;
        // Redimensionnement
        redimensionner() ;
    }

    /**
     * Méthode appelée automatiquement lorsqu'une notification est envoyée par le modèle {@link Jeu}.
     * <p>
     * Met à jour les boutons selon l'état actuel de la grille :
     * cases révélées, mines, drapeaux, chiffres, victoire ou défaite.
     *
     * @param o observable émetteur (ici, le jeu)
     * @param arg argument optionnel passé par le modèle
     */
    public void update(Observable o, Object arg) {

        // Si on a donné en argument "REBUILD"
        if (arg != null && arg.equals("REBUILD")) {
            // Met à jour les boutons
            mettreAJourBoutons() ;
            // Redimensionne la fenêtre
            redimensionner() ;
            // Arrête la fonction
            return ;
        }

        // Grille de la partie
        Grille g = jeu.getGrille() ;

        // Itération sur toutes les cases
        for (int i = 0 ; i < g.getX() ; i++) {
            for (int j = 0 ; j < g.getY() ; j++) {
                // Case actuelle
                Case c = g.getCase(i, j) ;
                // Bouton correspondant aux coordonnées actuelles
                JButton b = boutons[i][j] ;
                
                // S'il y a un drapeau dans la case
                if (c.drapeau) {
                    // Récupère l'image de drapeau
                    ImageIcon flagIcon = new ImageIcon(getClass().getResource("/img/drapeau.png")) ;
                    // Change la taille de l'image
                    Image img = flagIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH) ;
                    // Crée l'image
                    flagIcon = new ImageIcon(img) ;
                    // Enlève le texte
                    b.setText("") ;
                    // Ajoute l'image
                    b.setIcon(flagIcon) ;
                    // Change la couleur de fond
                    b.setBackground(Color.yellow) ;
                    // Revalide
                    b.revalidate() ; 
                    // Repeint
                    b.repaint() ;
                }
                else {
                    // Si la case n'est pas révélée
                    if (!c.revelee) {
                        // Enlever le texte
                        b.setText("") ;
                        // N'ajouter aucune image
                        b.setIcon(null) ;
                        // Changer la couleur de fond
                        b.setBackground(Color.gray) ;
                    }
                    else {
                        // Si la case est minée
                        if (c.mine) {
                            // Récupère l'image de mine
                            ImageIcon bombIcon = new ImageIcon(getClass().getResource("/img/bombe.png")) ;
                            // Change la taille de l'image
                            Image img = bombIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH) ;
                            // Crée l'image
                            bombIcon = new ImageIcon(img) ;
                            // Enlève le texte
                            b.setText("") ;
                            // Ajoute l'image
                            b.setIcon(bombIcon) ;
                            // Change la couleur de fond
                            b.setBackground(Color.WHITE) ;
                            // Revalide
                            b.revalidate() ;
                            // Repeint
                            b.repaint() ;
                        }
                        else {
                            // S'il y a au moins un voisin miné
                            if (c.nbVoisins > 0) {
                                // Récupère l'image du nombre de voisins minés
                                ImageIcon numIcon = new ImageIcon(getClass().getResource("/img/" + c.nbVoisins + ".png")) ;
                                // Change la taille de l'image
                                Image img = numIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH) ;
                                // Crée l'image
                                numIcon = new ImageIcon(img) ;
                                // Enlève le texte
                                b.setText("") ;
                                // Ajoute l'image
                                b.setIcon(numIcon) ;
                                // Change la couleur de fond
                                b.setBackground(Color.WHITE) ; 
                                // Revalide
                                b.revalidate() ;
                                // Repeint
                                b.repaint();
                            }
                            // Si la case ne contient rien
                            else {
                                // Change la couleur de fond
                                b.setBackground(Color.WHITE) ;
                                // Enlève le texte
                                b.setText("") ;
                                // Enlève l'image
                                b.setIcon(null) ;
                            }
                        }
                    }
                }
            }
        }

        // Si la partie est perdu
        if (jeu.isPerdu()) {
            // Ajoute un message de perte en fenêtre
            SimpleUI gameOver = new SimpleUI(this ,"GAME OVER !", "Tu es tombé sur une mine !") ;
            // Rendre visible la fenêtre
            gameOver.setVisible(true) ;
        }

        // Si la partie est gagnée
        if (jeu.isGagne()) {
            // Ajoute un message de victoire en fenêtre
            SimpleUI win = new SimpleUI(this, "VICTOIRE !", "Félicitations !") ;
            // Rendre visible la fenêtre
            win.setVisible(true) ;
        }
    }

    /**
     * Redimensionne dynamiquement les boutons en fonction de la taille de la fenêtre.
     * <p>
     * Si la grille est hexagonale, les positions sont recalculées avec décalage des lignes impaires.
     * <p>
     * Pour les grilles carrées, chaque bouton est redimensionné proportionnellement.
     */
    private void redimensionner() {

        // Grille de la partie
        Grille g = jeu.getGrille() ;
        // Vérifie si la grille est hexagonale ou non
        boolean estHex = (g instanceof GrilleH) ;

        // Coordonnées de la grille
        int x = g.getX() ;
        int y = g.getY() ;

        // Largeur de la fenêtre
        int largeurFenetre = getContentPane().getWidth() ;
        // Hauteur de la fenêtre
        int hauteurFenetre = getContentPane().getHeight() ;

        // Taille de la grille
        int taille ;

        // Si la grille est hexagonale
        if (estHex) {
            // Calcul de la bonne taille de grille
            int tailleX = largeurFenetre / (y + 1) ;
            int tailleY = (int)(hauteurFenetre / (x * 0.75 + 1)) ;
            taille = Math.min(tailleX, tailleY) ;
            int decalageX = taille / 2 ;
            int largeurGrille = (int)(y * taille + decalageX) ;
            int offsetX = (largeurFenetre - largeurGrille) / 2 ;

            // Itération sur toutes les cases
            for (int i = 0 ; i < x ; i++) {
                for (int j = 0 ; j < y ; j++) {
                    // Bouton correspondant à la case actuelle
                    JButton b = boutons[i][j] ;

                    // Positions de la case
                    int posX = offsetX + j * taille ;
                    int posY = (int)(i * taille * 0.75) ;

                    // Si la ligne est paire, décaler un peu
                    if (i % 2 == 1) {
                        posX += decalageX ;
                    }

                    // Sommets du bouton
                    b.setBounds(posX, posY, taille, taille) ;
                }
            }

        }
        // Si la ligne est carrée
        else {
            // Calcul de la bonne taille de grille
            int tailleX = largeurFenetre / y ;
            int tailleY = hauteurFenetre / x ;
            taille = Math.min(tailleX, tailleY) ;
            int offsetX = (largeurFenetre - y * taille) / 2 ;
            int offsetY = (hauteurFenetre - x * taille) / 2 ;

            // Itération sur les cases de la grille
            for (int i = 0 ; i < x ; i++) {
                for (int j = 0 ; j < y ; j++) {
                    // Bouton correspondant à la case actuelle
                    JButton b = boutons[i][j] ;
                    // Positions de la case
                    int posX = offsetX + j * taille ;
                    int posY = offsetY + i * taille ;
                    // Sommets du bouton
                    b.setBounds(posX, posY, taille, taille) ;
                }
            }
        }
        // Repeindre
        repaint() ;
    }

    /**
     * Met à jour l'affichage de tous les boutons selon l'état de la grille du jeu.
     * <p>
     * Les cases sont affichées avec :
     * <ul>
     *     <li>Un drapeau si la case est marquée</li>
     *     <li>Une mine si la case contient une mine révélée</li>
     *     <li>Une image du nombre de mines voisines sinon</li>
     *     <li>Fond gris pour les cases non révélées</li>
     * </ul>
     */
    private void mettreAJourBoutons() {
        // Grille du jeu
        Grille g = jeu.getGrille() ;

        // Itération sur les cases de la grille
        for (int i = 0 ; i < g.getX() ; i++) {
            for (int j = 0 ; j < g.getY() ; j++) {
                // Case actuelle
                Case c = g.getCase(i,j) ;
                // Bouton correspondant à la case actuelle
                JButton b = boutons[i][j] ;

                // S'il y a un drapeau dans la case
                if (c.drapeau) {
                    // Récupère l'image de drapeau
                    ImageIcon flagIcon = new ImageIcon(getClass().getResource("/img/drapeau.png")) ;
                    // Change la taille de l'image
                    Image img = flagIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH) ;
                    // Crée l'image
                    flagIcon = new ImageIcon(img) ;
                    // Enlève le texte
                    b.setText("") ;
                    // Ajoute l'image
                    b.setIcon(flagIcon) ;
                    // Change la couleur de fond
                    b.setBackground(Color.yellow) ;
                }
                else {
                    // Si la case n'est pas révélée
                    if (!c.revelee) {
                        // Enlever le texte
                        b.setText("") ;
                        // N'ajouter aucune image
                        b.setIcon(null) ;
                        // Changer la couleur de fond
                        b.setBackground(Color.gray) ;
                    }
                    else {
                        // Si la case est minée
                        if (c.mine) {
                            // Récupère l'image de mine
                            ImageIcon bombIcon = new ImageIcon(getClass().getResource("/img/bombe.png")) ;
                            // Change la taille de l'image
                            Image img = bombIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH) ;
                            // Crée l'image
                            bombIcon = new ImageIcon(img) ;
                            // Enlève le texte
                            b.setText("") ;
                            // Ajoute l'image
                            b.setIcon(bombIcon) ;
                            // Change la couleur de fond
                            b.setBackground(Color.WHITE) ;
                        }
                        else {
                            // S'il y a au moins un voisin miné
                            if (c.nbVoisins > 0) {
                                // Récupère l'image du nombre de voisins minés
                                ImageIcon numIcon = new ImageIcon(getClass().getResource("/img/" + c.nbVoisins + ".png")) ;
                                // Change la taille de l'image
                                Image img = numIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH) ;
                                // Crée l'image
                                numIcon = new ImageIcon(img) ;
                                // Enlève le texte
                                b.setText("") ;
                                // Ajoute l'image
                                b.setIcon(numIcon) ;
                                // Change la couleur de fond
                                b.setBackground(Color.WHITE) ; 
                            }
                            // Si la case ne contient rien
                            else {
                                // Change la couleur de fond
                                b.setBackground(Color.WHITE) ;
                                // Enlève le texte
                                b.setText("") ;
                                // Enlève l'image
                                b.setIcon(null) ;
                            }
                        }
                    }
                }
            }
        }
    }
}

