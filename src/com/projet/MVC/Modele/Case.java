package src.com.projet.MVC.Modele;

import java.io.Serializable;

public class Case implements Serializable {
    private static final long serialVersionUID = 1L;

    public boolean mine = false;
    public boolean revelee = false;
    public boolean drapeau = false;
    public int nbVoisins = 0;

}
