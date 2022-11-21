package Misc;

import Model.CountryTableModel;
import UI.VehicleLabel;

import javax.swing.*;
import java.awt.*;

public class TransportThread extends Thread {
    private final Object object;
    private CountryTableModel countryTableModel;
    private int difficultyModifier1 = 50;
    private VehicleLabel[] vehicles;
    private boolean travel = true;
    private boolean updateTransport = true;
    private int amountToWait = 100;
    boolean[] upgrades = new boolean[9];

    public TransportThread(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        Country[] countries = countryTableModel.getCountries();
        double amountOfSteps = 35;
        int realAmount = 0;
        int[] Xs = new int[vehicles.length];
        int[] Ys = new int[vehicles.length];
        boolean flag = false;

        for (int i = 0; i < vehicles.length; i++) {
            countries[i / 2].getLinkedCountries()[flag ? 1 : 0].infect(countries[i / 2].getInfectedPeople() / difficultyModifier1);
            Xs[i] = (int) ((countries[i/2].getLinkedCountries()[flag ? 1 : 0].getX() - vehicles[i].getX())/amountOfSteps);
            Ys[i] = (int) ((countries[i/2].getLinkedCountries()[flag ? 1 : 0].getY() - vehicles[i].getY())/amountOfSteps);
            flag = !flag;
        }

        while (travel && updateTransport){
            realAmount++;
            try {
                Thread.sleep(amountToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            for (int i = 0, j = 0; i < vehicles.length; i++) {
                vehicles[i].setBounds((int) (vehicles[i].getX() + Xs[i]), (int) (vehicles[i].getY() + Ys[i]), 25, 25);
                if (i%2 !=  0){
                    if(vehicles[i].getX() == countries[j].getLinkedCountries()[1].getX() && vehicles[i].getY() == countries[j].getLinkedCountries()[1].getY() ){
                        travel = false;
                    }
                    j++;
                }else{
                    if(realAmount == amountOfSteps){
                        realAmount = 0;
                        boolean flag2 = false;
                        for (int k = 0; k < vehicles.length; k++) {
                            if((upgrades[6] && vehicles[k].getVehicleEnum() == VehicleEnum.PLANE)) {
                                System.out.println("Plane banned!");
                            }else if((upgrades[7] && vehicles[k].getVehicleEnum() == VehicleEnum.BOAT)) {
                                System.out.println("Boat banned!");
                            }else if((upgrades[8] && vehicles[k].getVehicleEnum() == VehicleEnum.HELI)) {
                                System.out.println("Helicopter banned!");
                            }else{
                                countries[k / 2].getLinkedCountries()[flag2 ? 1 : 0].infect(countries[k / 2].getInfectedPeople() / difficultyModifier1);
                            }
                                flag2 = !flag2;
                        }

                        travel = false;
                    }
                }
            }

            while (!travel && updateTransport){
                realAmount++;
                try {
                    Thread.sleep(amountToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0, j = 0; i < vehicles.length; i++) {

                    vehicles[i].setBounds((int)(vehicles[i].getX() - Xs[i]), (int)(vehicles[i].getY()-Ys[i]), 25, 25);

                    if (i%2 !=  0){
                        if(vehicles[i].getX() == countries[j].getX() && vehicles[i].getY() == countries[j].getY() ){
                            travel = true;
                        }
                        j++;
                    }else{
                        if(realAmount == amountOfSteps){
                            realAmount = 0;

                            travel = true;
                        }
                    }
                }

            }

        }


    }

    public JLabel[] getPlanes() {
        return vehicles;
    }

    public void setCountryTableModel(CountryTableModel countryTableModel) {
        this.countryTableModel = countryTableModel;
        vehicles = new VehicleLabel[countryTableModel.getCountries().length*2];
        for (int i = 0, k = 0; i < countryTableModel.getCountries().length; i++) {
            for (int j = 0; j < countryTableModel.getCountries()[i].getLinkedCountries().length; j++, k++) {
                vehicles[k] = new VehicleLabel();
                vehicles[k].setText(k+"");
                vehicles[k].setHorizontalTextPosition(JButton.CENTER);
                vehicles[k].setVerticalTextPosition(JButton.CENTER);
                vehicles[k].setForeground(Color.RED);
                vehicles[k].setVerticalAlignment(0);


                vehicles[k].setVisible(true);
                vehicles[k].setBounds(countryTableModel.getCountries()[i].getX(),countryTableModel.getCountries()[i].getY(),25,25);
            }
        }
    }

    public void setUpdateTransport(boolean updateTransport) {
        this.updateTransport = updateTransport;
    }

    public void setUpgradeAtIndex(boolean upgrade, int index) {
        upgrades[index] = upgrade;
        for (int i = 0; i < vehicles.length; i++) {
            if((upgrades[6] && vehicles[i].getVehicleEnum() == VehicleEnum.PLANE)) {
                vehicles[i].setVisible(false);
            }else if((upgrades[7] && vehicles[i].getVehicleEnum() == VehicleEnum.BOAT)) {
                vehicles[i].setVisible(false);
            }else if((upgrades[8] && vehicles[i].getVehicleEnum() == VehicleEnum.HELI)) {
                vehicles[i].setVisible(false);
            }
        }
        if (index == 4){
            amountToWait = 125;
        } else if (index == 5){
            amountToWait = 150;
        }
    }
}
