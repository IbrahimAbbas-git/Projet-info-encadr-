import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class MF extends JFrame implements Observer {

    GrilleC grille = new GrilleC(10,10);
    boolean PremierClic = true;

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
                grille.tab[i][j].addMouseListener(new MouseAdapter() {
                   @Override
                   public void mouseClicked(MouseEvent e) {
                    if (PremierClic) {
                        PremierClic = false;
                        grille.setMine(x, y, 20);
                    }
                    jeu.set(x, y);
                    if( SwingUtilities.isLeftMouseButton(e) ) {
                        grille.updateGrille(grille.tab[x][y]);
                        if(grille.tab[x][y].value == EnumCase.MINE){
                            grille.Finpartie();
                            SimpleUI fenetre = new SimpleUI(e) ;
                            fenetre.setAlwaysOnTop(true);
                            fenetre.setVisible(true);
                        }
                    }
                    else if( SwingUtilities.isRightMouseButton(e) ) {
                        if( grille.tab[x][y].value != EnumCase.EST_CLICK){
                            if( grille.tab[x][y].getIcon() == null ){
                                ImageIcon flagIcon = new ImageIcon(getClass().getResource("drapeau.png"));
                                Image imgflag = flagIcon.getImage();
                                Image scaledflag = imgflag.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                                flagIcon = new ImageIcon(scaledflag);
                                grille.tab[x][y].setIcon(flagIcon);
                            }
                            else{
                                grille.tab[x][y].setIcon(null);
                            }
                        }
                    }
                }
                });
                // grille.tab[i][j].addActionListener(e -> {
                //     if (PremierClic) {
                //         PremierClic = false;
                //         grille.setMine(x, y, 20);
                //     }
                //     jeu.set(x, y);
                //     grille.updateGrille(grille.tab[x][y]);
                //     if(grille.tab[x][y].value == EnumCase.MINE){
                //         grille.Finpartie();
                //         SimpleUI fenetre = new SimpleUI(this) ;
                //         fenetre.setAlwaysOnTop(true);
                //         fenetre.setVisible(true);
                //     }
                // });

                pp.add(grille.tab[i][j].j);
            }
        }

        this.add(pp);
    }    
}
