package src.com.projet.MVC;
import javax.swing.*;

import src.com.projet.MVC.Modele.Jeu;
import src.com.projet.MVC.Vue_Controller.MF;

import java.util.Observer;
import java.util.Observable;

public class main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        Jeu j = new Jeu();

        SwingUtilities.invokeLater(() -> {
            MF mf = new MF(j);
            j.addObserver((Observer) mf);
            mf.setVisible(true);
        });
    }
}
