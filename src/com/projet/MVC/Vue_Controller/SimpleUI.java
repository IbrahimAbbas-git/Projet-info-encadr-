package com.projet.MVC.Vue_Controller ;
import javax.swing.* ;

import com.projet.MVC.Main ;
import com.projet.MVC.Modele.Jeu ;

import java.awt.* ;

/**
 * SimpleUI représente plusieurs fenêtres/dialogues de l'interface utilisateur du démineur.
 * <p>
 * Cette classe fournit différentes boîtes de dialogue pour :
 * <ul>
 *     <li>Afficher un message simple</li>
 *     <li>Proposer de rejouer ou quitter</li>
 *     <li>Configurer une nouvelle partie (taille et type de grille)</li>
 * </ul>
 * Elle étend {@link JDialog} pour un comportement modale.
 */
public class SimpleUI extends JDialog {

    /**
     * Constructeur principal pour afficher un message avec deux boutons : "Rejouer" et "Quitter".
     * <p>
     * Le bouton "Rejouer" relance le jeu en appelant {@link Main#main(String[])}.
     * <p>
     * Le bouton "Quitter" ferme simplement la fenêtre parent et le dialogue.
     *
     * @param parent fenêtre parent {@link JFrame} pour centrer le dialogue
     * @param message message à afficher
     * @param titre titre du dialogue
     */
    public SimpleUI(JFrame parent, String message, String titre) {
        // Appelle le constructeur de la classe parent
        super(parent, titre, true) ;
        
        // Création d'un panel
        JPanel panel = new JPanel(new GridBagLayout()) ;
        // Gestionnaire d'endroit d'emplacement des boutons (grille)
        GridBagConstraints gbc = new GridBagConstraints() ;
        
        // Création d'un nouveau message
        JLabel label = new JLabel(message) ;
        // Police du message
        label.setFont(new Font("Arial", Font.BOLD, 80)) ;
        
        // Abscisse de la grille
        gbc.gridx = 0 ;
        // Ordonnée de la grille
        gbc.gridy = 0 ;
        // Largeur de la grille
        gbc.gridwidth = 1 ;
        // Centralisation de la grille
        gbc.anchor = GridBagConstraints.CENTER ;
        // Marges de la grille (Haut, Gauche, Bas, Droite)
        gbc.insets = new Insets(20, 20, 50, 20);  
        // Ajout du message et de la grille dans le panel
        panel.add(label, gbc) ;

        // Ajout d'un panel de bouton
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0)) ;
        // Panel de bouton non-opaque
        buttonPanel.setOpaque(false);  

        // Bouton Rejouer
        JButton rejouerButton = new JButton("Rejouer") ;
        // Police du bouton
        rejouerButton.setFont(new Font("Arial", Font.PLAIN, 30)) ;
        // Actions du bouton
        rejouerButton.addActionListener(e -> {
            // Arrêt de la grille
            dispose() ;
            // Arrêt de la fenêtre
            parent.dispose() ;
            // Nouvelle partie
            Main.main(new String[0]) ;
        }) ; 

        // Bouton Quitter
        JButton quitterButton = new JButton("Quitter") ;
        // Police du bouton
        quitterButton.setFont(new Font("Arial", Font.PLAIN, 30)) ;
        // Actions du bouton
        quitterButton.addActionListener(e -> {
            // Arrêt de la grille
            dispose() ;
            // Arrêt de la fenêtre
            parent.dispose() ;
        }) ;

        // Ajoute les deux boutons au panel de boutons
        buttonPanel.add(rejouerButton) ;
        buttonPanel.add(quitterButton) ;

        // Ordonnée de la grille
        gbc.gridy = 1 ;
        // Marges de la grille
        gbc.insets = new Insets(0, 0, 20, 0) ;
        // Ajout de la grille et du panel de boutons au panel
        panel.add(buttonPanel, gbc) ;

        // Ajout du panel
        add(panel) ;
        // Ajustement de la taille
        pack() ;
        // Ajustement de la position
        setLocationRelativeTo(parent) ;
    }

    /**
     * Constructeur pour afficher un message simple dans un dialogue modale.
     *
     * @param parent fenêtre parent {@link JFrame} pour centrer le dialogue
     * @param message message à afficher
     */
    public SimpleUI(JFrame parent, String message) {
        // Appelle le constructeur de la classe parent
        super(parent, message, true) ;

        // Création d'un label avec le message
        JLabel label = new JLabel(message) ;
        // Police du label
        label.setFont(new Font("Arial", Font.BOLD, 80)) ;

        // Création d'un panel
        JPanel panel = new JPanel() ;
        // Ajout du label au panel
        panel.add(label) ;

        // Ajout du panel
        add(panel) ;
        // Ajustement de la taille
        pack() ;
        // Ajustement de la position
        setLocationRelativeTo(parent) ;
    }

    /**
     * Constructeur pour afficher un message simple avec les boutons "Rejouer" et "Quitter".
     * <p>
     * Les boutons sont positionnés sur le panneau et relancent ou ferment le jeu selon l'action.
     *
     * @param message message à afficher
     * @param parent fenêtre parent {@link JFrame} pour centrer le dialogue
     */
    public SimpleUI(String message, JFrame parent) {
        // Appelle le constructeur de la classe parent
        super(parent, message, true) ;

        // Création d'un label avec le message donné
        JLabel label = new JLabel(message) ;
        // Police du message
        label.setFont(new Font("Arial", Font.BOLD, 80)) ;
        // Centre le label
        label.setHorizontalAlignment(SwingConstants.CENTER) ;
        
        // Création d'un panel avec bordure
        JPanel panel = new JPanel(new BorderLayout()) ;
        // Ajoute le label au panel et centre la bordure
        panel.add(label, BorderLayout.CENTER) ;

        // Bouton rejouer
        JButton rejouerButton = new JButton("Rejouer") ;
        // Police du bouton
        rejouerButton.setFont(new Font("Arial", Font.PLAIN, 30)) ;
        // Actions du boutons
        rejouerButton.addActionListener(e -> {
            // Ferme la grille
            dispose() ;
            // Ferme la fenêtre
            parent.dispose() ;
            // Relance d'une partie
            Main.main(new String[0]) ;
        }) ; 

        // Bouton quitter
        JButton quitterButton = new JButton("Quitter") ;
        // Police du bouton
        quitterButton.setFont(new Font("Arial", Font.PLAIN, 30)) ;
        // Actions du bouton
        quitterButton.addActionListener(e -> {
            // Ferme la grille
            dispose() ;
            // Ferme la fenêtre
            parent.dispose() ;
        }) ;
        
        // Coordonnées des sommets des boutons
        quitterButton.setBounds(10, 70, 200, 50) ;
        rejouerButton.setBounds(10, 10, 200, 50) ;

        // Ajout des boutons au panel
        panel.add(rejouerButton) ;
        panel.add(quitterButton) ;
        // Ajout du panel
        add(panel) ;
        // Ajustement de la taille
        pack() ;
        // Ajustement de la position
        setLocationRelativeTo(parent) ;
    }

    /**
     * Constructeur pour la fenêtre de configuration de nouvelle partie.
     * <p>
     * Permet à l'utilisateur de sélectionner :
     * <ul>
     *     <li>Type de grille : carrée ou hexagonale</li>
     *     <li>Dimensions de la grille</li>
     *     <li>Difficulté / nombre de mines</li>
     * </ul>
     * Cette configuration est appliquée à l'objet {@link Jeu} passé en paramètre.
     *
     * @param jeu instance de {@link Jeu} à configurer
     */
    public SimpleUI(Jeu jeu) {
        // Titre de la fenêtre
        setTitle("Configuration du Démineur") ;

        // Création d'un label avec message
        JLabel label = new JLabel("Bienvenue dans le démineur !") ;
        // Police du label
        label.setFont(new Font("Arial", Font.BOLD, 40)) ;
        // Centrement du label
        label.setHorizontalAlignment(SwingConstants.CENTER) ;

        // Bouton de la grille carrée
        JButton carreButton = new JButton("Grille carrée") ;
        // Police du bouton
        carreButton.setFont(new Font("Arial", Font.BOLD, 25)) ;
        // Bouton de la grille hexagonale
        JButton hexButton = new JButton("Grille hexagonale") ;
        // Police du bouton
        hexButton.setFont(new Font("Arial", Font.PLAIN, 25)) ;

        // Actions du bouton de la grille carrée
        carreButton.addActionListener(e -> {
            // Indique que le jeu doit prendre une grille carrée
            jeu.setEstHex(false) ;
            // Police du bouton grille carré
            carreButton.setFont(new Font("Arial", Font.BOLD, 25)) ;
            // Police du bouton grille hexagonale
            hexButton.setFont(new Font("Arial", Font.PLAIN, 25)) ;
        }) ;

        // Actions du bouton de la grille hexagonale
        hexButton.addActionListener(e -> {
            // Indique que le jeu doit prendre une grille hexagonale
            jeu.setEstHex(true) ;
            // Police du bouton grille hexagonale
            hexButton.setFont(new Font("Arial", Font.BOLD, 25)) ;
            // Police du bouton grille carrée
            carreButton.setFont(new Font("Arial", Font.PLAIN, 25)) ;
        }) ;

        // Différentes tailles de grille
        String[] tailles = { "Petit (10x10)", "Moyen (16x16)", "Grand (30x16)" } ;
        // Bouton permettant de sélectionner la taille
        JComboBox<String> menuTailles = new JComboBox<>(tailles) ;
        // Police du bouton de taille
        menuTailles.setFont(new Font("Arial", Font.PLAIN, 20)) ;

        // Différents choix de nombre de mine
        String[] mines = { "Facile", "Moyen", "Difficile" } ;
        // Bouton permettant de sélectionner le nombre de mines
        JComboBox<String> menuMines = new JComboBox<>(mines) ;
        // Police du bouton de nombre de mines
        menuMines.setFont(new Font("Arial", Font.PLAIN, 20)) ;

        // Bouton de validation
        JButton validerButton = new JButton("Valider") ;
        // Police du bouton de validation
        validerButton.setFont(new Font("Arial", Font.BOLD, 30)) ;

        // Création d'un panel
        JPanel panel = new JPanel(new GridBagLayout()) ;
        // Gestionnaire d'endroit d'emplacement des boutons (grille)
        GridBagConstraints gbc = new GridBagConstraints() ;
        // Marges entre les éléments
        gbc.insets = new Insets(15, 15, 15, 15) ;
        // Les composants occupent tout l'espace de leur cellule
        gbc.fill = GridBagConstraints.BOTH ;

        // Abscisse de la grille
        gbc.gridx = 0 ;
        // Ordonnée de la grille
        gbc.gridy = 0 ;
        // Largeur de la grille
        gbc.gridwidth = 2 ; 
        // Ajout du label et de la grille
        panel.add(label, gbc) ;

        // Largeur de la grille
        gbc.gridwidth = 1 ; 
        // Abscisse de la grille
        gbc.gridx = 0 ;
        // Ordonnée de la grille
        gbc.gridy = 1 ;
        // Ajout du bouton grille carré et de la grille
        panel.add(carreButton, gbc) ;

        // Abscisse de la grille
        gbc.gridx = 1 ;
        // Ajout du bouton tailles et de la grille
        panel.add(menuTailles, gbc) ;

        // Abscisse de la grille
        gbc.gridx = 0 ;
        // Ordonnée de la grille
        gbc.gridy = 2 ;
        // Ajout du bouton grille hexagonale et de la grille
        panel.add(hexButton, gbc) ;

        // Abscisse de la grille
        gbc.gridx = 1 ;
        // Ajout du bouton nombre mines et de la grille
        panel.add(menuMines, gbc) ;

        // Abscisse de la grille
        gbc.gridx = 0 ;
        // Ordonnée de la grille
        gbc.gridy = 3 ;
        // Largeur de la grille
        gbc.gridwidth = 2 ;
        // Ajout du bouton validation et de la grille
        panel.add(validerButton, gbc) ;

        // Actions du bouton de validation
        validerButton.addActionListener(e -> {
            // Récupération des choix de taille
            String choixT = (String) menuTailles.getSelectedItem() ;
            // Si l'utilisateur demande une grille 10x10, soit
            if (choixT.contains("10x10")) {
                jeu.setTailleX(10) ;
                jeu.setTailleY(10) ;
            }
            else {
                // Si l'utilisateur demande une grille 16x16, soit
                if (choixT.contains("16x16")) {
                    jeu.setTailleX(16) ;
                    jeu.setTailleY(16) ;
                }
                // Si l'utilisateur demande une grille 30x16, soit
                else {
                    jeu.setTailleX(30) ;
                    jeu.setTailleY(16) ;
                }
            }

            // Récupération des choix de nombre de mines
            String choixM = (String) menuMines.getSelectedItem() ;
            // Si l'utilisateur veut une grille facile, il aura autant de mines que de colonnes
            if(choixM.contains("Facile")) {
                jeu.setNbMines(jeu.getTailleX()) ;
            }
            else {
                // Si l'utilisateur veut une grille moyenne, il aura 2 fois le nombre de colonnes en mines
                if(choixM.contains("Moyen")) {
                    jeu.setNbMines(jeu.getTailleX() * 2) ;
                }
                // Si l'utilisateur veut une grille difficile, il aura 3 fois le nombre de colonnes en mines
                else {
                    jeu.setNbMines(jeu.getTailleX() * 3) ;
                }
            }

            // Chemin du fichier de sauvegarde
            String nomSave = "../saves/save_" + jeu.getTailleX() + "_" + jeu.getTailleY() + "_" + jeu.getNbMines() + "_" + jeu.isEstHex() + ".dat" ;
            // Récupération du fichier
            java.io.File f = new java.io.File(nomSave) ;

            if (f.exists()) {
                // Si le fichier existe, proposer de reprendre une partie
                int choix = JOptionPane.showConfirmDialog(
                    null,
                    "Une partie existe déjà avec ces paramètres. Voulez-vous la reprendre ?",
                    "Reprendre partie ?",
                    JOptionPane.YES_NO_OPTION
                ) ;

                // Si l'utilisateur ne veut pas, supprimer la sauvegarde
                if (choix == JOptionPane.NO_OPTION) {
                    f.delete() ;
                }
            }

            // Fermer la fenêtre
            dispose() ;
            // Initialiser une partie
            jeu.initialiserPartie() ;
            // Afficher le jeu
            MF mf = new MF(jeu) ;
            // Permet d'afficher le jeu
            jeu.addObserver(mf) ;
        }) ;

        // Ajoute le panel
        add(panel) ;
        // Ajuste la taille
        pack() ;
        // Ajuste la position
        setLocationRelativeTo(null) ;
    }
}
