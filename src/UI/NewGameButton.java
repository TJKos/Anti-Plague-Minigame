package UI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class NewGameButton extends JButton implements ChangeListener {
    private String last = "";
    private MainFrame mainFrame;
    public NewGameButton(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setIcon(new ImageIcon(new ImageIcon("Images/newGame.png").getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT)));
        addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(getModel().isRollover() && !getModel().isPressed()){
            setIcon(new ImageIcon(new ImageIcon("Images/newGameHover.png").getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT)));
            if (last.equals("click")){
                buttonFunc();
            }
        }else if(getModel().isPressed()){
            setIcon(new ImageIcon(new ImageIcon("Images/newGameClicked.png").getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT)));
            last = "click";
        }else{
            setIcon(new ImageIcon(new ImageIcon("Images/newGame.png").getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT)));
            last = "";
        }
    }

    void buttonFunc(){
        mainFrame.hideMenu();
        mainFrame.startGame();
    }

}
