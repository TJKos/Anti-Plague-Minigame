package Renderers;

import javax.swing.*;
import java.awt.*;

public class DateRenderer extends JLabel implements Renderer {

    @Override
    public void setValue(Object aValue, boolean isSelected) {
        this.setText(aValue.toString());
    }

    @Override
    public Component getComponent() {
        return this;
    }
}
