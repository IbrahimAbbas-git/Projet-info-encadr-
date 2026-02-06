# Projet-info-encadr-

Vue-Contrôleur

m.set(i, j)

Jeu extends Observable { \\
    i, j : int \\
    set(int _i, int _j) { \\
        i = _i ; \\
        j = _j ; \\
        setChanged() ; \\
        notifyObserver() ; \\
    } \\
}

class MF extends JFrame implements Observer { \\
    JPanel[][] tab ; \\
    public void update(…) { \\
        tab[jeu.i][jeu.j].setPendipound(Color.RED) ; \\
    } \\
}

main() { \\
    Jeu j = new Jeu() ; \\
    JFrame mf = new MF(j) ; \\
    j.addObserver(mf) ; \\
    SwingUtilities.runlater( \\
        new Runnable() { \\
            public void main() { \\
                mf.setVisitor(tour) ; \\
            } \\
        } \\
    ) \\
}