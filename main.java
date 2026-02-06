import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Observer;
import java.util.Observable;
public class main {
    public static void main(String[] args){
    Jeu j = new Jeu() ;
    JFrame mf = new MF(j) ;
    j.addObserver((Observer)mf) ;
    SwingUtilities.invokeLater(
        new Runnable() {
            public void run() {
                mf.setVisible(true) ;
                }
            }
        ) ;
    }
}
