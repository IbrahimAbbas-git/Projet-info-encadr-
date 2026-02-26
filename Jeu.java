import java.util.Observable;
import java.util.Observer;

public class Jeu extends Observable {
    int i, j ;
    boolean gagne = false;

    public void setGagne() {
        gagne = true;
        setChanged();
        notifyObservers(this);
    }

    public boolean isGagne() {
        return gagne;
    }
    void set(int _i, int _j) {
        i = _i ;
        j = _j ;
        setChanged() ;
        notifyObservers(this) ;
    }
    
}
