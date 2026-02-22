import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Case {
    JButton j;
    EnumCase value;
    public Case(){
                j = new JButton();
                j.setPreferredSize(new Dimension(50, 50));
                j.setOpaque(true);
                j.setContentAreaFilled(true);
                j.setBorderPainted(true);
                // Icon icon = new ImageIcon("le chemin de l'image")
                //j.setIcon(null);
                value = EnumCase.EST_VIDE;
    }
    public void setBackground(Color c){
        //value = e;
        j.setBackground(c);
    }   
    public void set(EnumCase e){
        value = e;
    }
    public void addActionListener(ActionListener l ){
        j.addActionListener(l);
    }

    public void setText(String text) {
        j.setText(text) ;
    }

    public void setFont(Font f) {
        j.setFont(f);
    }

    public void setIcon(ImageIcon ii) {
        j.setIcon(ii) ;
    }

    public void setDisabledIcon(ImageIcon ii) {
        j.setDisabledIcon(ii) ;
    }

    public void setEnabled(boolean b) {
        j.setEnabled(b) ;
    }
}
