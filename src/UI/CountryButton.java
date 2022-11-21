package UI;

import Misc.CountryEnum;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class CountryButton extends JButton implements ChangeListener {
    private CountryEnum countryEnum;
    public CountryButton(String path, int x, int y, int width, int height, String countryName) {
        switch (countryName) {
            case "Australia" -> countryEnum = CountryEnum.AUSTRALIA;
            case "Brazil" -> countryEnum = CountryEnum.BRAZIL;
            case "China" -> countryEnum = CountryEnum.CHINA;
            case "Egypt" -> countryEnum = CountryEnum.EGYPT;
            case "France" -> countryEnum = CountryEnum.FRANCE;
            case "Japan" -> countryEnum = CountryEnum.JAPAN;
            case "Poland" -> countryEnum = CountryEnum.POLAND;
            case "Russia" -> countryEnum = CountryEnum.RUSSIA;
            case "UK" -> countryEnum = CountryEnum.UK;
            case "USA" -> countryEnum = CountryEnum.USA;
            default -> countryEnum = CountryEnum.NONE;
        }
        setText(countryName);
        setFont(new Font("Comic Sans", Font.BOLD, 10));
        setIcon(new ImageIcon(path));
        setVisible(true);
        setOpaque(true);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder());
        setBorderPainted(true);
        setBounds(x,y,width,height);
        setFocusable(false);
        addChangeListener(this);
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
        setForeground(Color.RED);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(getModel().isRollover() && !getModel().isPressed()){
            setBorder(BorderFactory.createLineBorder(Color.RED, 3));

        }else if(getModel().isPressed()){
            setBorder(BorderFactory.createEmptyBorder());
            System.out.println(countryEnum);
        }else{
            setBorder(BorderFactory.createEmptyBorder());
        }
    }
}
