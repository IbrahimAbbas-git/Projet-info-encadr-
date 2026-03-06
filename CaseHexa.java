import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
    
class CaseHexa implements Case{
    private static final long serialVersionUID = 1L;
    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 50;
    public static final int LENGTH = 95;
    public static final int WIDTH = 105;
    private int row = 0;
    private int col = 0;
    public JButton j;
    public EnumCase value;

    public CaseHexa(int row, int col) {
        j = new JButton(){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Polygon hex = new Polygon();
                for (int i = 0; i < SIDES; i++) {
                    hex.addPoint((int) (SIDE_LENGTH + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                            (int) (SIDE_LENGTH + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
                }       
                g.drawPolygon(hex);
            }
        };
        j.setContentAreaFilled(false);
        j.setFocusPainted(true);
        j.setBorderPainted(false);
        j.setPreferredSize(new Dimension(WIDTH, LENGTH));
        this.row = row;
        this.col = col;
        j.setPreferredSize(new Dimension(50, 50));
        j.setOpaque(true);
        j.setContentAreaFilled(true);
        j.setBorderPainted(true);
        value = EnumCase.EST_VIDE;
    }

    public void paintComponent(Graphics g) {
        j.paintComponent(g);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
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
    
    public void setBounds(int x, int y, int width, int height) {
        j.setBounds(x, y, width, height);
    }
}