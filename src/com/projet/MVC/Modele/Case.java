package com.projet.MVC.Modele ;

import java.io.Serializable ;

/**
 * Représente une case du plateau de démineur.
 * <p>
 * Chaque case peut contenir une mine, être révélée, avoir un drapeau et connaître le nombre de mines voisines.
 */
public class Case implements Serializable {

    /**
     * Identifiant de version pour la sérialisation.
     */
    private static final long serialVersionUID = 1L ;

    /**
     * Indique si la case contient une mine.
     */
    public boolean mine = false ;

    /**
     * Indique si la case a été révélée.
     */
    public boolean revelee = false ;

    /**
     * Indique si un drapeau est posé sur la case.
     */
    public boolean drapeau = false ;

    /**
     * Nombre de mines dans les cases voisines.
     */
    public int nbVoisins = 0 ;

    /**
     * Constructeur par défaut de la case.
     * <p>
     * Initialise une case vide sans mine et non révélée.
     */
    public Case() {
        
    }
}
