package src.com.projet.MVC.Vue_Controller;
import javax.swing.*;

import src.com.projet.MVC.Main;
import src.com.projet.MVC.Modele.Jeu;

import java.awt.* ;
import java.awt.event.MouseEvent;

public class SimpleUI extends JDialog {

    public SimpleUI(JFrame parent,String message,String titre) {
        super(parent, titre, true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 80));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; 
        gbc.anchor = GridBagConstraints.CENTER;
        // Insets(Haut, Gauche, Bas, Droite)
        gbc.insets = new Insets(20, 20, 50, 20); 
        panel.add(label, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.setOpaque(false); 

        // Bouton Rejouer
        JButton rejouerButton = new JButton("Rejouer");
        rejouerButton.setFont(new Font("Arial", Font.PLAIN, 30));
        rejouerButton.addActionListener(e -> {
            dispose();
            parent.dispose();
            Main.main(new String[0]);
        }); 

        // Bouton Quitter
        JButton quitterButton = new JButton("Quitter");
        quitterButton.setFont(new Font("Arial", Font.PLAIN, 30));
        quitterButton.addActionListener(e -> {
            dispose();
            parent.dispose();
        });

        buttonPanel.add(rejouerButton);
        buttonPanel.add(quitterButton);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(buttonPanel, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }


    public SimpleUI(JFrame parent, String message) {
        super(parent, message, true);

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 80));

        JPanel panel = new JPanel();
        panel.add(label);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public SimpleUI(String message, JFrame parent) {
        super(parent, message, true); // modale
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 80));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        JButton rejouerButton = new JButton("Rejouer");
        rejouerButton.setFont(new Font("Arial", Font.PLAIN, 30));
        rejouerButton.addActionListener(e -> {
            dispose();
            parent.dispose();// Ferme la fenêtre pour rejouer
            Main.main(new String[0]);// relance le main pour recommencer une partie
        }); 

        JButton quitterButton = new JButton("Quitter");
        quitterButton.setFont(new Font("Arial", Font.PLAIN, 30));
        quitterButton.addActionListener(e -> {
            dispose();
            parent.dispose();}
        ); // Ferme les fenêtres pour quitter
        
        quitterButton.setBounds(10, 70, 200, 50);
        rejouerButton.setBounds(10, 10, 200, 50);

        panel.add(rejouerButton);
        panel.add(quitterButton);
        add(panel);
        pack();
        setLocationRelativeTo(parent); // centre sur la fenêtre principale
    }
    public SimpleUI(Jeu jeu) {
        setTitle("Configuration du Démineur");

        JLabel label = new JLabel("Bienvenue dans le démineur !");
        label.setFont(new Font("Arial", Font.BOLD, 40)); // Titre principal
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton carreButton = new JButton("Grille carrée");
        carreButton.setFont(new Font("Arial", Font.BOLD, 25));
        JButton hexButton = new JButton("Grille hexagonale");
        hexButton.setFont(new Font("Arial", Font.PLAIN, 25));

        carreButton.addActionListener(e -> {
            jeu.setEstHex(false);
            carreButton.setFont(new Font("Arial", Font.BOLD, 25));
            hexButton.setFont(new Font("Arial", Font.PLAIN, 25));
        });
        hexButton.addActionListener(e -> {
            jeu.setEstHex(true);
            hexButton.setFont(new Font("Arial", Font.BOLD, 25));
            carreButton.setFont(new Font("Arial", Font.PLAIN, 25));
        });

        String[] tailles = { "Petit (10x10)", "Moyen (16x16)", "Grand (30x16)" };
        JComboBox<String> menuTailles = new JComboBox<>(tailles);
        menuTailles.setFont(new Font("Arial", Font.PLAIN, 20));

        String[] mines = { "Facile", "Moyen", "Difficile" };
        JComboBox<String> menuMines = new JComboBox<>(mines);
        menuMines.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton validerButton = new JButton("Valider");
        validerButton.setFont(new Font("Arial", Font.BOLD, 30));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Marges entre les éléments
        gbc.fill = GridBagConstraints.BOTH;      // Les composants occupent tout l'espace de leur cellule

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        panel.add(label, gbc);

        gbc.gridwidth = 1; 
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(carreButton, gbc);

        gbc.gridx = 1;
        panel.add(menuTailles, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(hexButton, gbc);

        gbc.gridx = 1;
        panel.add(menuMines, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(validerButton, gbc);

        validerButton.addActionListener(e -> {
            // On applique nos choix
            String choixT = (String) menuTailles.getSelectedItem();
            if (choixT.contains("10x10")) { jeu.setTailleX(10); jeu.setTailleY(10);}
            else if (choixT.contains("16x16")) { jeu.setTailleX(16); jeu.setTailleY(16);}
            else { jeu.setTailleX(30); jeu.setTailleY(16);}

            String choixM = (String) menuMines.getSelectedItem();
            if(choixM.contains("Facile")) jeu.setNbMines(jeu.getTailleX());
            else if(choixM.contains("Moyen")) jeu.setNbMines(jeu.getTailleX() * 2);
            else jeu.setNbMines(jeu.getTailleX() * 3);

            String nomSave = "../saves/save_" + jeu.getTailleX() + "_" + jeu.getTailleY() + "_" + jeu.getNbMines() + "_" + jeu.isEstHex() + ".dat";
            java.io.File f = new java.io.File(nomSave);

            if(f.exists()){
                int choix = JOptionPane.showConfirmDialog(
                    null,
                    "Une partie existe déjà avec ces paramètres. Voulez-vous la reprendre ?",
                    "Reprendre partie ?",
                    JOptionPane.YES_NO_OPTION
                );

                if(choix == JOptionPane.NO_OPTION){
                    // Supprime l'ancienne sauvegarde pour recommencer une partie propre
                    f.delete();
                }
            }

            dispose();
            jeu.initialiserPartie();
            MF mf = new MF(jeu);
            jeu.addObserver(mf);
        });

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }
}
