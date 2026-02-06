# Projet-info-encadr-

Vue-Contrôleur

m.set(i, j)

Jeu extends Observable { \
&nbsp;&nbsp;&nbsp;&nbsp;i, j : int \
&nbsp;&nbsp;&nbsp;&nbsp;set(int _i, int _j) { \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;i = _i ; \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;j = _j ; \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;setChanged() ; \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;notifyObserver() ; \
&nbsp;&nbsp;&nbsp;&nbsp;} \
}

class MF extends JFrame implements Observer { \
&nbsp;&nbsp;&nbsp;&nbsp;JPanel[][] tab ; \
&nbsp;&nbsp;&nbsp;&nbsp;public void update(…) { \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;tab[jeu.i][jeu.j].setPendipound(Color.RED) ; \
&nbsp;&nbsp;&nbsp;&nbsp;} \
}

main() { \
&nbsp;&nbsp;&nbsp;&nbsp;Jeu j = new Jeu() ; \
&nbsp;&nbsp;&nbsp;&nbsp;JFrame mf = new MF(j) ; \
&nbsp;&nbsp;&nbsp;&nbsp;j.addObserver(mf) ; \
&nbsp;&nbsp;&nbsp;&nbsp;SwingUtilities.runlater( \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;new Runnable() { \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void main() { \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;mf.setVisitor(tour) ; \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} \
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} \
&nbsp;&nbsp;&nbsp;&nbsp;) \
}