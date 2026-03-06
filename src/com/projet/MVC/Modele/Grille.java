package src.com.projet.MVC.Modele;
import java.util.ArrayList;

public interface Grille {
    public void setMine(int i, int j, int nbMine);
    public void updateGrille(Case c);
    public int getX();
    public int getY();
    public boolean victoire();
    public void Finpartie();
}
