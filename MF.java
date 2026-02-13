import java.util.Observable;
import java.util.Observer;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

class MF extends JFrame implements Observer {
    JPanel[][] tab = new JPanel[10][10] ;
    public MF(Jeu j) {
        build(j);

        setTitle("Démineur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // ajuste à la taille des composants
        setLocationRelativeTo(null); // centre la fenêtre
    }

    public void update(Observable o, Object jeu) {
        Jeu j = (Jeu) jeu ;
        tab[j.i][j.j].setBackground(java.awt.Color.RED);
    }
    public void build(Jeu jeu) {
        JPanel pp = new JPanel(new GridLayout(10, 10)) ;
        for (int i = 0 ; i < 10 ; i++) {
            for (int j = 0 ; j < 10 ; j++) {
                //if(i==jeu.i && j==jeu.j) {
                //    tab[i][j] = new JPanel() ;
                //    tab[i][j].setBackground(java.awt.Color.RED);
               // } else {
                tab[i][j] = new JPanel() ;
                var text = new javax.swing.JLabel(i + "," + j) ;
                tab[i][j].add(text);
                pp.add(tab[i][j]) ;//}
            }
        }
        this.add(pp);
    }
    
}