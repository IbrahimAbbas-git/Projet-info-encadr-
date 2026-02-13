import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;

class MF extends JFrame implements Observer {
    JButton[][] tab = new JButton[10][10] ;
    public MF(Jeu j) {
        build(j) ;

        setTitle("Démineur") ;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        pack() ; // ajuste à la taille des composants
        setLocationRelativeTo(null) ; // centre la fenêtre
    }

    public void update(Observable o, Object jeu) {
        Jeu j = (Jeu) jeu;
        tab[j.i][j.j].setBackground(Color.RED);
    }

    public void build(Jeu jeu) {
        JPanel pp = new JPanel(new GridLayout(10, 10));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                tab[i][j] = new JButton();
                tab[i][j].setPreferredSize(new Dimension(50, 50));

                int x = i;
                int y = j;

                tab[i][j].addActionListener(e -> {
                    jeu.set(x, y);
                });

                pp.add(tab[i][j]);
            }
        }

        this.add(pp);
    }    
}
