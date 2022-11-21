package UI;

import Misc.VehicleEnum;

import javax.swing.*;

public class VehicleLabel extends JLabel {
    VehicleEnum vehicleEnum;
    public VehicleLabel() {
        vehicleEnum = VehicleEnum.getRandom();
        ImageIcon imageIcon;
        switch (vehicleEnum){
            case PLANE -> {
                imageIcon = new ImageIcon("Images/Map/Plane.png");
            }
            case HELI -> {
                imageIcon = new ImageIcon("Images/Map/Helicopter.png");
            }
            case BOAT -> {
                imageIcon = new ImageIcon("Images/Map/Boat.png");
            }
            default -> {
                imageIcon = new ImageIcon("Images/Map/Russia.png");
            }
        }

        setIcon(imageIcon);
    }

    public VehicleEnum getVehicleEnum() {
        return vehicleEnum;

    }


}
