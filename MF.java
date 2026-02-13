import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;

class MF extends JFrame implements Observer {
    GrilleC grille = new GrilleC(10,10);
    public MF(Jeu j) {
        build(j) ;

        setTitle("Démineur") ;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        pack() ;
        setLocationRelativeTo(null) ;
    }

    public void update(Observable o, Object jeu) {
        Jeu j = (Jeu) jeu;
        grille.tab[j.i][j.j].setBackground(Color.RED);
    }

    public void build(Jeu jeu) {
        JPanel pp = new JPanel(new GridLayout(10, 10));
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = i;
                int y = j;

                grille.tab[i][j].addActionListener(e -> {
                    jeu.set(x, y);
                    //grille.tab[x][y].setText("X");
                });

                pp.add(grille.tab[i][j].j);
            }
        }

        this.add(pp);
    }    
}
