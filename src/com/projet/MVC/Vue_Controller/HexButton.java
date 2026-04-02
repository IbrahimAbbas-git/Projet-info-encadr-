package com.projet.MVC.Vue_Controller ;

import javax.swing.* ;
import java.awt.* ;

/**
 * HexButton est un JButton personnalisé dessiné sous forme d'hexagone.
 * <p>
 * Il gère son propre dessin et sa zone de clic en hexagone.
 */
public class HexButton extends JButton {

    /**
     * Polygone représentant la forme hexagonale du bouton
     */
    private Polygon hex ;

    /**
     * Constructeur du bouton hexagonal.
     * <p>
     * Désactive le remplissage standard, le focus et la bordure par défaut.
     */
    public HexButton() {
        // Désactivation du remplissage standard
        setContentAreaFilled(false) ;
        // Désactivation du focus
        setFocusPainted(false) ;
        // Désactivation de la bordure par défaut
        setBorderPainted(false) ;
    }

    /**
     * Crée le polygone hexagonal en fonction de la taille actuelle du bouton.
     * <p>
     * Cette méthode est appelée avant le dessin ou pour tester la zone de clic.
     */
    private void createHexagon() {
        // Largeur de l'hexagone
        int w = getWidth() ;
        // Hauteur de l'hexagone
        int h = getHeight() ;
        // Marge entre les hexagones (utile pour afficher le côté droit de la dernière colonne)
        int margin = 1 ;

        // Abscisses des sommets de l'hexagone
        int[] xPoints = {
            w / 2,
            w - margin,
            w - margin,
            w / 2,
            margin,
            margin
        } ;
        // Ordonnées des sommets de l'hexagone
        int[] yPoints = {
            margin,
            h / 4,
            3 * h / 4,
            h - margin,
            3 * h / 4,
            h / 4
        } ;

        // Création de l'hexagone
        hex = new Polygon(xPoints, yPoints, 6);
    }

    /**
     * Dessine le composant du bouton.
     * <p>
     * Remplit l'hexagone avec la couleur de fond du bouton.
     *
     * @param g le contexte graphique
     */
    @Override
    protected void paintComponent(Graphics g) {

        // Création de l'hexagone
        createHexagon() ;

        // Transtypage vers un graphique 2D
        Graphics2D g2 = (Graphics2D) g ;
        // Change la couleur en celle du fond d'écran
        g2.setColor(getBackground()) ;
        // Remplit l'hexagone avec la couleur
        g2.fillPolygon(hex) ;

        // Peint l'hexagone via la méthode de Graphics
        super.paintComponent(g) ;
    }

    /**
     * Dessine la bordure du bouton sous forme d'hexagone.
     *
     * @param g le contexte graphique
     */
    @Override
    protected void paintBorder(Graphics g) {

        // Création de l'hexagone
        createHexagon() ;

        // Transtypage vers un graphique 2D
        Graphics2D g2 = (Graphics2D) g ;
        // Change la couleur de bordure en noir
        g2.setColor(Color.BLACK);
        // Dessine l'hexagone
        g2.drawPolygon(hex);
    }

    /**
     * Vérifie si un point (x, y) est à l'intérieur de l'hexagone.
     * <p>
     * Utilisé pour détecter les clics correctement.
     *
     * @param x coordonnée x du point
     * @param y coordonnée y du point
     * @return true si le point est à l'intérieur de l'hexagone, false sinon
     */
    @Override
    public boolean contains(int x, int y) {

        // S'il n'y a pas d'hexagone, en créer un
        if (hex == null) {
            createHexagon();
        }

        // Renvoie si l'hexagone contient un certain point (via les coordonnées du point)
        return hex.contains(x, y);
    }

    /**
     * Retourne la taille préférée du bouton.
     * <p>
     * Par défaut, un bouton hexagonal de 50x50 pixels.
     *
     * @return dimension préférée du bouton
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50) ;
    }
}
