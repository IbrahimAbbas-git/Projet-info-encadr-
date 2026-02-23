import javax.swing.*;
import java.awt.* ;

public class SimpleUI extends JDialog {

    public SimpleUI(JFrame parent) {
        super(parent, "Tu es tombé sur une mine !", true); // true = modale
        
        JLabel label = new JLabel("GAME OVER !");
        label.setFont(new Font("Arial", Font.BOLD, 80));
        
        JPanel panel = new JPanel();
        panel.add(label);
        
        add(panel);
        pack();
        setLocationRelativeTo(parent); // centre sur la fenêtre principale
    }
}
