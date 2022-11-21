package Misc;

public class Country {
    private CountryEnum countryEnum;
    private int startingPopulation;
    private int population;
    private int healthyPeople;
    private int infectedPeople;
    private int deadPeople;
    private int curedPeople = 0;
    private int x;
    private int y;
    private Country[] linkedCountries;


    public CountryEnum getCountryEnum() {
        return countryEnum;
    }

    public int getPopulation() {
        return population;
    }

    public int getHealthyPeople() {
        return healthyPeople;
    }

    public int getInfectedPeople() {
        return infectedPeople;
    }

    public int getDeadPeople(){
        return deadPeople;
    }

    public int getCuredPeople() {
        return curedPeople;
    }

    public int getStartingPopulation() {
        return startingPopulation;
    }

    public Country[] getLinkedCountries() {
        return linkedCountries;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setHealthyPeople(int healthyPeople) {
        this.healthyPeople = healthyPeople;
    }

    public void setInfectedPeople(int infectedPeople) {
        this.infectedPeople = infectedPeople;
    }

    public void setDeadPeople(int deadPeople) {
        this.deadPeople = deadPeople;
    }

    public void setCuredPeople(int curedPeople) {
        this.curedPeople = curedPeople;
    }

    public void setLinkedCountries(Country[] linkedCountries) {
        this.linkedCountries = linkedCountries;
    }

    public void infect(int infectedAmount){
        if (healthyPeople > 0) {
            int healthyToInfected = infectedPeople + infectedAmount >= healthyPeople ? healthyPeople + infectedPeople : infectedPeople + infectedAmount;

            infectedPeople = healthyToInfected;
            healthyPeople = getStartingPopulation() - infectedPeople - deadPeople - curedPeople;
        }
    }

    public Country(CountryEnum countryEnum) {
        this.countryEnum = countryEnum;
        switch (countryEnum){
            case AUSTRALIA -> {
                population = 25360;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 1035;
                y = 550;
            }
            case BRAZIL -> {
                population = 211000;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 422;
                y = 502;
            }
            case CHINA -> {
                population = 1398000;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 941;
                y = 303;
            }
            case EGYPT -> {
                population = 100400;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 700;
                y = 345;
            }
            case FRANCE -> {
                population = 67060;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 607;
                y = 268;
            }
            case JAPAN -> {
                population = 126300;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 1050;
                y = 300;
            }
            case POLAND -> {
                population = 37970;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 663;
                y = 242;
            }
            case RUSSIA -> {
                population = 144400;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 957;
                y = 171;
            }
            case UK -> {
                population = 66650;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 587;
                y = 231;
            }
            case USA -> {
                population = 328200;
                healthyPeople = population;
                infectedPeople = 0;
                deadPeople = 0;
                x = 203;
                y = 246;
            }
            case NONE -> {
                population = 0;
                healthyPeople = 0;
                infectedPeople = 0;
                deadPeople = 0;
                x = 0;
                y = 0;
            }
        }
        startingPopulation = population;

    }
}
