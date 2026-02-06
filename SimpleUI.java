// Source - https://stackoverflow.com/a/73623183
// Posted by OscarRyz
// Retrieved 2026-02-06, License - CC BY-SA 4.0

import javax.swing.*;

public class SimpleUI {
  public static void main(String ... args) {
    var frame = new JFrame("Title goes here");
    var text = new JTextField(20);
    var button = new JButton("Click me");
    var panel = new JPanel();
    panel.add(text);
    panel.add(button);
    frame.add(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
