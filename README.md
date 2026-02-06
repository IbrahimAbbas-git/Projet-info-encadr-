# Projet-info-encadr-

Vue-Contrôleur

```java
m.set(i, j)

Jeu extends Observable {
    i, j : int
    set(int _i, int _j) {
        i = _i ;
        j = _j ;
        setChanged() ;
        notifyObserver() ;
    }
}

class MF extends JFrame implements Observer {
    JPanel[][] tab ;
    public void update(…) {
        tab[jeu.i][jeu.j].setBackground(Color.RED) ;
    }
}

main() {
    Jeu j = new Jeu() ;
    JFrame mf = new MF(j) ;
    j.addObserver(mf) ;
    SwingUtilities.runlater(
    new Runnable() {
        public void main() {
            mf.setVisitor(tour) ;
        }
    )
}

package Vue-Controleur

class MF extends JFrame {
    JFrame() {
        build() ;
    }
    JPanel[] tab = new JPanel[10][10]

    public void build() {
        JPanel pp = new JPanel(new Gridlayout(10, 10)) ;
        for (int i = 0 ; i < 10 ; i++) {
            for (int j = 0 ; j < 10 ; j++) {
                tab[i][j] = new JPanel() ;
                pp.add(tab[i][j]) ;
            }
        }
    }
}
```
