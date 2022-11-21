package Misc;

import Model.CountryTableModel;
import Renderers.PointRenderer;

import java.util.Arrays;

public class VirusThread extends Thread {
    private final Object object;
    private CountryTableModel countryTableModel;
    private PointRenderer pointRenderer;
    private boolean updateVirus = true;
    private int difficultyChoice = -1;
    private int difficultyModifier1;
    private int cureModifier = 1;
    private int speedModifier = 1000;
    boolean[] upgrades = new boolean[9];

    public VirusThread(Object object){
        this.object = object;
        Arrays.fill(upgrades, false);
    }

    @Override
    public void run() {
        int difficultyModifier1 = -1;
        int difficultyModifier2 = -1;

        switch (difficultyChoice){
            case 0 -> {
                difficultyModifier1 = 60;
                difficultyModifier2 = 6;
            }
            case 1 -> {
                difficultyModifier1 = 50;
                difficultyModifier2 = 5;

            }
            case 2 -> {
                difficultyModifier1 = 30;
                difficultyModifier2 = 3;
            }
            default -> {
                System.out.println("Błąd");
            }
        }
        while (updateVirus) {

            synchronized (object) {
                int valueToAdd = 0;
                for (Country country : countryTableModel.getCountries()) {

                    int healthyToInfected = country.getInfectedPeople();
                    if (country.getHealthyPeople() > 0){
                        healthyToInfected = country.getInfectedPeople()+country.getInfectedPeople()/difficultyModifier2 >= country.getHealthyPeople() ? country.getHealthyPeople()+country.getInfectedPeople() : country.getInfectedPeople()+country.getInfectedPeople()/difficultyModifier2;
                    }
                    country.setInfectedPeople(healthyToInfected);

                    int infectedToDead = (country.getInfectedPeople() > 2) && (country.getInfectedPeople() < 50) && (country.getHealthyPeople() == 0) ? 1 : country.getInfectedPeople()/difficultyModifier1;
                    country.setDeadPeople(country.getDeadPeople() + infectedToDead);
                    country.setInfectedPeople(country.getInfectedPeople() - infectedToDead);
                    country.setPopulation(country.getStartingPopulation() - country.getDeadPeople());

                    int amountToCure = country.getInfectedPeople() > 0 ? (country.getCuredPeople()+cureModifier > country.getInfectedPeople() ? country.getInfectedPeople() : cureModifier) : 0;
                    country.setCuredPeople(country.getCuredPeople() + amountToCure);
                    country.setInfectedPeople(country.getInfectedPeople() - amountToCure);
                    country.setHealthyPeople(country.getStartingPopulation()-country.getInfectedPeople()-country.getDeadPeople()-country.getCuredPeople());
                    valueToAdd = amountToCure > 1 ? valueToAdd + amountToCure : valueToAdd+1;

                }

                pointRenderer.setValue(Math.min(pointRenderer.getPoints() + valueToAdd/10, pointRenderer.getPoints()+20), false);

                countryTableModel.fireTableDataChanged();

                int gameOver1 = 0;
                int gameOver2 = 0;
                for (Country country : countryTableModel.getCountries()) {
                    if (country.getInfectedPeople() == country.getPopulation()) {
                        gameOver1++;
                    }else if(country.getCuredPeople() + country.getHealthyPeople() == country.getPopulation()){
                        gameOver2++;
                    }
                }

                if (gameOver1 == 10 || gameOver2 == 10) {
                    countryTableModel.invokeGameEnded(upgrades);
                }
            }

            try {
                Thread.sleep(speedModifier);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUpgradeAtIndex(boolean upgrade, int index) {
        this.upgrades[index] = upgrade;
    }

    public boolean[] getUpgrades() {
        return upgrades;
    }

    public void setUpdateVirus(boolean updateVirus) {
        this.updateVirus = updateVirus;
    }

    public void setCountries(CountryTableModel countryTableModel) {
        this.countryTableModel = countryTableModel;
    }

    public void setDifficultyChoice(int difficultyChoice) {
        this.difficultyChoice = difficultyChoice;
    }

    public void setDifficultyModifier1(int difficultyModifier1) {
        this.difficultyModifier1 = difficultyModifier1;
    }

    public void setCureModifier(int cureModifier) {
        this.cureModifier = cureModifier;
    }

    public void setPointRenderer(PointRenderer pointRenderer) {
        this.pointRenderer = pointRenderer;
    }

    public int getDifficultyModifier1() {
        return difficultyModifier1;
    }

    public int getCureModifier() {
        return cureModifier;
    }

    public int getDifficultyChoice() {
        return difficultyChoice;
    }
}

