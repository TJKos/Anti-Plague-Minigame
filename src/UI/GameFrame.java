package UI;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements KeyListener {
    MainFrame mainFrame;
    public GameFrame(String name, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setTitle(name);
        setVisible(true);
        setSize(1680,720);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        ImageIcon ladnyObrazek = new ImageIcon("Images/testicon2.png");
        setIconImage(ladnyObrazek.getImage());
        setResizable(false);
        setFocusable(true);
        setLocationRelativeTo(null);
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isShiftDown() && e.isControlDown() && e.getKeyCode() == 81){
            mainFrame.stopGame();
            dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
