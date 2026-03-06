package src.com.projet.MVC.Modele;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;
public class CaseCarre implements Case{
    JButton j;
    EnumCase value;
    public CaseCarre(){
                j = new JButton();
                j.setPreferredSize(new Dimension(50, 50));
                j.setOpaque(true);
                j.setContentAreaFilled(true);
                j.setBorderPainted(true);
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

    public void addMouseListener(MouseListener l ){
        j.addMouseListener(l);
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
    public Icon getIcon() {
        return j.getIcon() ;
    }   

    public void setDisabledIcon(ImageIcon ii) {
        j.setDisabledIcon(ii) ;
    }

    public void setEnabled(boolean b) {
        j.setEnabled(b) ;
    }
}
