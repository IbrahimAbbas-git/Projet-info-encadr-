package src.com.projet.MVC.Modele;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;
public interface Case {

        public void setBackground(Color c);
        public void set(EnumCase e);
        public void addActionListener(ActionListener l );
        public void addMouseListener(MouseListener l );
        public void setText(String text) ;
        public void setFont(Font f) ;
        public void setIcon(ImageIcon ii) ;
        public Icon getIcon() ;
        public void setDisabledIcon(ImageIcon ii) ;
        public void setEnabled(boolean b) ;
}