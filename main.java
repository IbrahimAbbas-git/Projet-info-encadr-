import javax.swing.*;
import java.util.Observer;
import java.util.Observable;

public class main {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getCrossPlatformLookAndFeelClassName()
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
