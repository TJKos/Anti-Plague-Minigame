package Renderers;

import javax.swing.*;
import java.awt.*;

public class PointRenderer extends JLabel implements Renderer {
    public int points = 90;


    @Override
    public void setValue(Object aValue, boolean isSelected) {
        setText(aValue+"");
        this.points = (int)aValue;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public int getPoints() {
        return points;
    }
}
