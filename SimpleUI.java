import javax.swing.*;
import java.awt.* ;
import java.awt.event.MouseEvent;

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
    public SimpleUI(MouseEvent e) {
        Component bouton = (Component) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(bouton);// on recupere la fenetre( principale ) qui contient le bouton

        setTitle("Tu es tombé sur une mine !");
        setModal(true); // Rend la fenêtre modale (bloque le reste)

        JLabel label = new JLabel("GAME OVER !");
        label.setFont(new Font("Arial", Font.BOLD, 80));
        
        JPanel panel = new JPanel();
        panel.add(label);
        
        add(panel);
        pack();

        setLocationRelativeTo(win); // Centre la fenêtre par rapport à la fenêtre principale
    
    }

    public SimpleUI(JFrame parent, String message) {
        super(parent, message, true);

        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 80));

        JPanel panel = new JPanel();
        panel.add(label);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public SimpleUI(String message, JFrame parent) {
        super(parent, message, true); // modale
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 80));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        
        add(panel);
        pack();
        setLocationRelativeTo(parent); // centre sur la fenêtre principale
    }
}
