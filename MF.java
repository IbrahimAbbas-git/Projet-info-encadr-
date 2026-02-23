import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;

class MF extends JFrame implements Observer {
    GrilleC grille = new GrilleC(10,10,70);
    public MF(Jeu j) {
        build(j) ;

        setTitle("Démineur") ;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        pack() ;
        setLocationRelativeTo(null) ;
    }

    public void update(Observable o, Object jeu) {
        Jeu j = (Jeu) jeu;
        grille.tab[j.i][j.j].setBackground(Color.GRAY);
    }

    public void build(Jeu jeu) {
        JPanel pp = new JPanel(new GridLayout(10, 10));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = i;
                int y = j;

                grille.tab[i][j].addActionListener(e -> {
                    jeu.set(x, y);
                    ImageIcon bombIcon = new ImageIcon(getClass().getResource("bombe.png"));
                    Image img = bombIcon.getImage();
                    Image scaled = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    bombIcon = new ImageIcon(scaled);
                    if(grille.tab[x][y].value == EnumCase.MINE){
                        grille.tab[x][y].setIcon(bombIcon);
                        grille.tab[x][y].setDisabledIcon(bombIcon);
                        grille.tab[x][y].setText("");      // vide pour enlever le texte
                        grille.tab[x][y].setEnabled(false);        // bloque le bouton
                        SimpleUI fenetre = new SimpleUI(this) ;
                        fenetre.setAlwaysOnTop(true);
                        fenetre.setVisible(true);
                    }
                    else{grille.updateGrille(grille.tab[x][y]);}
                });

                pp.add(grille.tab[i][j].j);
            }
        }

        this.add(pp);
    }    
}
