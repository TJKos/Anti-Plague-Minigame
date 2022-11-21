package UI;

import Model.HighScoresModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HighScoresFrame extends JFrame implements KeyListener {
    MainFrame mainFrame;
    HighScoresModel highScoresModel;
    public HighScoresFrame(String name, MainFrame mainFrame, HighScoresModel highScoresModel) {
        this.mainFrame = mainFrame;
        this.highScoresModel = highScoresModel;
        setTitle(name);
        setVisible(true);
        setSize(300,200);
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
            mainFrame.closeHighScores();
            dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void addJList(JList jList) {
        JScrollPane jScrollPane = new JScrollPane(jList);
        jScrollPane.setFocusable(false);
        getContentPane().add(jScrollPane);
    }
}
