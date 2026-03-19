package src.com.projet.MVC.Vue_Controller;

import javax.swing.*;
import java.awt.*;

public class HexButton extends JButton {

    private Polygon hex;

    public HexButton() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    private void createHexagon() {
        int w = getWidth();
        int h = getHeight();

        int margin = 1;

        int[] xPoints = {
            w/2,
            w - margin,
            w - margin,
            w/2,
            margin,
            margin
        };

        int[] yPoints = {
            margin,
            h/4,
            3*h/4,
            h - margin,
            3*h/4,
            h/4
        };

        hex = new Polygon(xPoints, yPoints, 6);
    }

    @Override
    protected void paintComponent(Graphics g) {

        createHexagon();

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.fillPolygon(hex);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        createHexagon();

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.drawPolygon(hex);
    }

    @Override
    public boolean contains(int x, int y) {

        if(hex == null){
            createHexagon();
        }

        return hex.contains(x, y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }
}
