package src.com.projet.MVC.Modele;
import java.util.Observable;
import java.util.Observer;

public class Jeu extends Observable {
    public int i, j ;
    boolean gagne = false;

    public void setGagne() {
        gagne = true;
        setChanged();
        notifyObservers(this);
    }

    public boolean isGagne() {
        return gagne;
    }
    public void set(int _i, int _j) {
        i = _i ;
        j = _j ;
        setChanged() ;
        notifyObservers(this) ;
    }
    // public void update(Observable o, Object arg) {
    //         if ()
    // }
}
