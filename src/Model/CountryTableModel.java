package Model;

import Misc.Country;
import UI.MainFrame;

import javax.swing.table.AbstractTableModel;

public class CountryTableModel extends AbstractTableModel {
    MainFrame mainFrame;
    private String[] columns;
    private Country[] countries;

    public CountryTableModel(Country[] countries, String[] columns, MainFrame mainFrame) {
        this.countries = countries;
        this.columns = columns;
        this.mainFrame = mainFrame;
    }

    @Override
    public int getRowCount() {
        return countries.length;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0 -> {
                return countries[rowIndex].getCountryEnum();
            }
            case 1 -> {
                return countries[rowIndex].getPopulation();
            }
            case 2 -> {
                return countries[rowIndex].getHealthyPeople();
            }
            case 3 -> {
                return countries[rowIndex].getInfectedPeople();
            }
            case 4 -> {
                return countries[rowIndex].getDeadPeople();
            }
            case 5 -> {
                return countries[rowIndex].getCuredPeople();
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return String.class;
        } else {
            return Integer.class;
        }
    }

    public Country[] getCountries() {
        return countries;
    }

    public void invokeGameEnded(boolean[] upgrades){
        mainFrame.gameOver(upgrades);
    }
}
