package UI;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ExitButton extends JButton implements ChangeListener {
    String last = "";
    MainFrame mainFrame;
    public ExitButton(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setIcon(new ImageIcon(new ImageIcon("Images/Exit.png").getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT)));
        addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(getModel().isRollover() && !getModel().isPressed()){
            setIcon(new ImageIcon(new ImageIcon("Images/ExitHover.png").getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT)));
            if (last.equals("click")){
                buttonFunc();
            }
        }else if(getModel().isPressed()){
            setIcon(new ImageIcon(new ImageIcon("Images/ExitClicked.png").getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT)));
            last = "click";
        }else{
            setIcon(new ImageIcon(new ImageIcon("Images/Exit.png").getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT)));
            last = "";
        }
    }

    public void buttonFunc() {
        mainFrame.close();
    }

}
