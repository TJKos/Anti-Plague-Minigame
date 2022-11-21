package UI;

import Misc.*;
import Model.CountryTableModel;
import Model.HighScoresModel;
import Renderers.DateRenderer;
import Renderers.PointRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Vector;


public class MainFrame extends JFrame {
    private boolean backgroundToggle = true;
    private JLayeredPane menuLayeredPane;
    private boolean gameStarted = false;
    private boolean highScoresOpened = false;
    private DateThread dateThread;
    private VirusThread virusThread;
    private TransportThread transportThread;
    private GameFrame gameFrame;
    private Vector<Score> scores = new Vector<Score>(1);

    public MainFrame(String s) {
        setTitle(s);
        generateFrame();
    }

    public void generateFrame(){
        setLocationRelativeTo(null);
        setSize(1280,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon ladnyObrazek = new ImageIcon("Images/testicon2.png");
        setIconImage(ladnyObrazek.getImage());
        setResizable(false);
        setFocusable(false);

        menuLayeredPane = new JLayeredPane();
        menuLayeredPane.setBounds(0,0,1280,720);

        add(menuLayeredPane, BorderLayout.CENTER);

        NewGameButton newGameButton = new NewGameButton(this);
        HighScoresButton highScoresButton = new HighScoresButton(this);
        ExitButton exitButton = new ExitButton(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        int panelY = 100;

        buttonPanel.setBounds(this.getWidth()/2-panelY,panelY,200,400);
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 50));
        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoresButton);
        buttonPanel.add(exitButton);

        JLabel jLabel = new JLabel();
        jLabel.setOpaque(true);
        jLabel.setIcon(new ImageIcon("Images/virus4.jpg"));
        jLabel.setBounds(0,0,1280,720);

        Thread backgroundThread = new Thread(() -> {
            try {
                while (backgroundToggle) {
                    jLabel.setIcon(new ImageIcon("Images/virus1.jpg"));
                    Thread.sleep(3000);

                    jLabel.setIcon(new ImageIcon("Images/virus2.png"));
                    Thread.sleep(3000);

                    jLabel.setIcon(new ImageIcon("Images/virus3.jpg"));
                    Thread.sleep(3000);

                    jLabel.setIcon(new ImageIcon("Images/virus4.jpg"));
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        backgroundThread.start();

        menuLayeredPane.add(buttonPanel, JLayeredPane.DRAG_LAYER);
        menuLayeredPane.add(jLabel, JLayeredPane.DEFAULT_LAYER);

        setLocationRelativeTo(null);
        setVisible(true);
        menuLayeredPane.setVisible(true);

    }

    public void close(){
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void hideMenu(){
        menuLayeredPane.setVisible(false);
    }

    public void showMenu(){
        menuLayeredPane.setVisible(true);
    }

    public void startGame() {
        if (!gameStarted) {
            gameStarted = true;
            Object object = new Object();
            transportThread = new TransportThread(object);
            dateThread = new DateThread(object);
            virusThread = new VirusThread(object);


            String[] options = {"Easy", "Medium", "Hard"};
            int difficultyChoice = JOptionPane.showOptionDialog(this, "Choose your preferred difficulty.", "Difficulty choice", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            System.out.println(difficultyChoice); // 0: Easy, 1: Medium, 2: Hard

            if (difficultyChoice != -1) {
                virusThread.setDifficultyChoice(difficultyChoice);

                gameFrame = new GameFrame("Game", this);

                JLayeredPane jLayeredPane = new JLayeredPane();
                jLayeredPane.setBounds(0, 0, 1280, 720);

                JLabel map = new JLabel();
                map.setIcon(new ImageIcon("Images/Map/map.png"));
                map.setVisible(true);
                map.setOpaque(true);
                map.setBounds(0, 0, 1280, 720);

                CountryButton[] countryButtons = {
                        new CountryButton("Images/Map/Australia.png", 968, 483, 134, 134, "Australia"),
                        new CountryButton("Images/Map/Brazil.png", 358, 433, 128, 139, "Brazil"),
                        new CountryButton("Images/Map/China.png", 841, 232, 201, 143, "China"),
                        new CountryButton("Images/Map/Egypt.png", 680, 328, 39, 43, "Egypt"),
                        new CountryButton("Images/Map/France.png", 585, 246, 45, 45, "France"),
                        new CountryButton("Images/Map/Japan.png", 1023, 269, 55, 62, "Japan"),
                        new CountryButton("Images/Map/Poland.png", 646, 227, 35, 31, "Poland"),
                        new CountryButton("Images/Map/Russia.png", 690, 52, 534, 239, "Russia"),
                        new CountryButton("Images/Map/UK.png", 565, 207, 44, 48, "UK"),
                        new CountryButton("Images/Map/USA.png", 22, 135, 363, 222, "USA"),
                };

                DateRenderer dateRenderer = new DateRenderer();
                dateRenderer.setText(dateThread.getDate().toString());
                dateRenderer.setOpaque(true);
                dateRenderer.setVisible(true);
                dateRenderer.setBounds(0, 660, 80, 20);

                dateThread.setDateRenderer(dateRenderer);

                PointRenderer pointRenderer = new PointRenderer();
                pointRenderer.setText(pointRenderer.getPoints() + "");
                pointRenderer.setOpaque(true);
                pointRenderer.setVisible(true);
                pointRenderer.setBounds(90, 660, 80, 20);

                virusThread.setPointRenderer(pointRenderer);

                Country[] countries = {
                        new Country(CountryEnum.AUSTRALIA),
                        new Country(CountryEnum.BRAZIL),
                        new Country(CountryEnum.CHINA),
                        new Country(CountryEnum.EGYPT),
                        new Country(CountryEnum.FRANCE),
                        new Country(CountryEnum.JAPAN),
                        new Country(CountryEnum.POLAND),
                        new Country(CountryEnum.RUSSIA),
                        new Country(CountryEnum.UK),
                        new Country(CountryEnum.USA)
                };

                countries[0].setLinkedCountries(new Country[]{countries[1], countries[5]});
                countries[1].setLinkedCountries(new Country[]{countries[3], countries[9]});
                countries[2].setLinkedCountries(new Country[]{countries[7], countries[0]});
                countries[3].setLinkedCountries(new Country[]{countries[2], countries[7]});
                countries[4].setLinkedCountries(new Country[]{countries[9], countries[3]});
                countries[5].setLinkedCountries(new Country[]{countries[0], countries[1]});
                countries[6].setLinkedCountries(new Country[]{countries[4], countries[1]});
                countries[7].setLinkedCountries(new Country[]{countries[3], countries[5]});
                countries[8].setLinkedCountries(new Country[]{countries[4], countries[6]});
                countries[9].setLinkedCountries(new Country[]{countries[1], countries[8]});


                countries[2].setInfectedPeople(difficultyChoice == 0 ? 30 : difficultyChoice == 1 ? 40 : 50);
                String[] columns = {"Country", "Population", "Healthy", "Infected", "Dead", "Cured"};
                CountryTableModel countryTableModel = new CountryTableModel(countries, columns, this);

                JTable countryTable = new JTable();
                countryTable.setFocusable(false);
                countryTable.setModel(countryTableModel);

                transportThread.setCountryTableModel(countryTableModel);

                JScrollPane jScrollPane = new JScrollPane(countryTable);
                jScrollPane.setBounds(1280, 0, 400, 180);

                virusThread.setCountries(countryTableModel);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new GridLayout(3, 3));
                jPanel.setBounds(1280, 200, 386, 386);

                JButton[] jButtons = new JButton[9];

                for (int i = 0; i < jButtons.length; i++) {
                    jButtons[i] = new JButton();
                    jButtons[i].setFocusable(false);
                    jButtons[i].setMargin(new Insets(0, 0, 0, 0));
                    jPanel.add(jButtons[i]);
                }

                jButtons[0].setText("Clean hands");
                jButtons[0].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cost = 100;
                        if (pointRenderer.points >= cost && !virusThread.getUpgrades()[0]) {
                            String[] options = {"Yes", "No"};
                            int choice = JOptionPane.showOptionDialog((JButton) e.getSource(), "Would you like to buy the 'Clean hands' upgrade? - 100 points.", "Purchase upgrade", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choice == 0) {
                                virusThread.setUpgradeAtIndex(true, 0);
                                virusThread.setCureModifier(virusThread.getCureModifier()+10);
                                pointRenderer.setValue(pointRenderer.points - cost, false);
                                jButtons[0].setForeground(Color.green);
                            }
                        }
                    }
                });

                jButtons[1].setText("Medical facility");
                jButtons[1].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cost = 200;
                        if (pointRenderer.points >= cost && !virusThread.getUpgrades()[1]) {
                            String[] options = {"Yes", "No"};
                            int choice = JOptionPane.showOptionDialog((JButton) e.getSource(), "Would you like to buy the 'Medical facility' upgrade? - 200 points.", "Purchase upgrade", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choice == 0) {
                                virusThread.setUpgradeAtIndex(true, 1);
                                virusThread.setCureModifier(virusThread.getCureModifier()+10);
                                pointRenderer.setValue(pointRenderer.points - cost, false);
                                jButtons[1].setForeground(Color.green);
                            }
                        }
                    }
                });

                jButtons[2].setText("Medical upgrade");
                jButtons[2].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cost = 400;
                        if (pointRenderer.points >= cost && virusThread.getUpgrades()[1] && !virusThread.getUpgrades()[2]) {
                            String[] options = {"Yes", "No"};
                            int choice = JOptionPane.showOptionDialog((JButton) e.getSource(), "Would you like to buy the 'Invest into the medical facility' upgrade? - 400 points.", "Purchase upgrade", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choice == 0) {
                                virusThread.setUpgradeAtIndex(true, 2);
                                virusThread.setCureModifier(virusThread.getCureModifier()*3);
                                pointRenderer.setValue(pointRenderer.points - cost, false);
                                jButtons[2].setForeground(Color.green);
                            }
                        }
                    }
                });

                jButtons[3].setText("Stay @ Home");
                jButtons[3].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cost = 300;
                        if (pointRenderer.points >= cost && !virusThread.getUpgrades()[3]) {
                            String[] options = {"Yes", "No"};
                            int choice = JOptionPane.showOptionDialog((JButton) e.getSource(), "Would you like to buy the 'Encourage staying at home' upgrade? - 300 points.", "Purchase upgrade", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choice == 0) {
                                virusThread.setUpgradeAtIndex(true, 3);
                                virusThread.setDifficultyModifier1(virusThread.getDifficultyModifier1()+20);
                                pointRenderer.setValue(pointRenderer.points - cost, false);
                                jButtons[3].setForeground(Color.green);
                            }
                        }
                    }
                });

                jButtons[4].setText("Slower transport");
                jButtons[4].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cost = 100;
                        if (pointRenderer.points >= cost && !virusThread.getUpgrades()[4]) {
                            String[] options = {"Yes", "No"};
                            int choice = JOptionPane.showOptionDialog((JButton) e.getSource(), "Would you like to buy the 'Slower transport' upgrade? - 100 points.", "Purchase upgrade", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choice == 0) {
                                virusThread.setUpgradeAtIndex(true, 4);
                                transportThread.setUpgradeAtIndex(true, 4);
                                pointRenderer.setValue(pointRenderer.points - cost, false);
                                jButtons[4].setForeground(Color.green);
                            }
                        }
                    }
                });

                jButtons[5].setText("Slower transport++");
                jButtons[5].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cost = 200;
                        if (pointRenderer.points >= cost && virusThread.getUpgrades()[4] && !virusThread.getUpgrades()[5]) {
                            String[] options = {"Yes", "No"};
                            int choice = JOptionPane.showOptionDialog((JButton) e.getSource(), "Would you like to buy the 'Medical facility' upgrade? - 200 points.", "Purchase upgrade", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choice == 0) {
                                virusThread.setUpgradeAtIndex(true, 5);
                                transportThread.setUpgradeAtIndex(true, 5);
                                pointRenderer.setValue(pointRenderer.points - cost, false);
                                jButtons[5].setForeground(Color.green);
                            }
                        }
                    }
                });

                jButtons[6].setText("Plane ban");
                jButtons[6].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cost = 600;
                        if (pointRenderer.points >= cost && !virusThread.getUpgrades()[6]) {
                            String[] options = {"Yes", "No"};
                            int choice = JOptionPane.showOptionDialog((JButton) e.getSource(), "Would you like to buy the 'Plane ban' upgrade? - 600 points.", "Purchase upgrade", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choice == 0) {
                                virusThread.setUpgradeAtIndex(true, 6);
                                transportThread.setUpgradeAtIndex(true, 6);
                                pointRenderer.setValue(pointRenderer.points - cost, false);
                                jButtons[6].setForeground(Color.green);
                            }
                        }
                    }
                });

                jButtons[7].setText("Boat ban");
                jButtons[7].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cost = 600;
                        if (pointRenderer.points >= cost && !virusThread.getUpgrades()[7]) {
                            String[] options = {"Yes", "No"};
                            int choice = JOptionPane.showOptionDialog((JButton) e.getSource(), "Would you like to buy the 'Boat ban' upgrade? - 600 points.", "Purchase upgrade", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choice == 0) {
                                virusThread.setUpgradeAtIndex(true, 7);
                                transportThread.setUpgradeAtIndex(true, 7);
                                pointRenderer.setValue(pointRenderer.points - cost, false);
                                jButtons[7].setForeground(Color.green);
                            }
                        }
                    }
                });

                jButtons[8].setText("Heli ban");
                jButtons[8].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int cost = 600;
                        if (pointRenderer.points >= cost && !virusThread.getUpgrades()[8]) {
                            String[] options = {"Yes", "No"};
                            int choice = JOptionPane.showOptionDialog((JButton) e.getSource(), "Would you like to buy the 'Helicopter ban' upgrade? - 600 points.", "Purchase upgrade", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choice == 0) {
                                virusThread.setUpgradeAtIndex(true, 8);
                                transportThread.setUpgradeAtIndex(true, 8);
                                pointRenderer.setValue(pointRenderer.points - cost, false);
                                jButtons[8].setForeground(Color.green);
                            }
                        }
                    }
                });

                jLayeredPane.add(jPanel, JLayeredPane.DRAG_LAYER);

                jLayeredPane.add(map, JLayeredPane.DEFAULT_LAYER);
                for (int i = 0; i < countryButtons.length; i++) {
                    jLayeredPane.add(countryButtons[i], JLayeredPane.POPUP_LAYER);
//                countryButtons[i].setPointRenderer(pointRenderer);
                }

                for (int i = 0; i < transportThread.getPlanes().length; i++) {
                    jLayeredPane.add(transportThread.getPlanes()[i], JLayeredPane.DRAG_LAYER);
                }

                jLayeredPane.add(dateRenderer, JLayeredPane.DRAG_LAYER);
                jLayeredPane.add(pointRenderer, JLayeredPane.DRAG_LAYER);
                jLayeredPane.add(jScrollPane, JLayeredPane.DRAG_LAYER);
                JLabel whiteBackground = new JLabel();
                whiteBackground.setBounds(0,0,1680,720);
                whiteBackground.setBackground(Color.white);
                whiteBackground.setVisible(true);
                whiteBackground.setOpaque(true);
                jLayeredPane.add(whiteBackground, JLayeredPane.DEFAULT_LAYER);


                gameFrame.add(jLayeredPane);
                jLayeredPane.setVisible(true);

                startDate();
            }else{
                stopGame();
            }
        }

    }

    public void openHighScores(){
        if(!highScoresOpened) {
            highScoresOpened = true;
            try{
                loadFile("scores.txt");
                scores.sort(new Comparator<Score>() {
                    @Override
                    public int compare(Score o1, Score o2) {
                        return o2.getScore() - o1.getScore();
                    }
                });
                HighScoresModel highScoresModel = new HighScoresModel(scores);
                HighScoresFrame highScoresFrame = new HighScoresFrame("High Scores", this, highScoresModel);
                JList jList = new JList();
                jList.setFocusable(false);
                jList.setModel(highScoresModel);
                highScoresFrame.addJList(jList);

            }catch (Exception e){
                System.err.println(e.getMessage());
                System.out.println("Nie ma jeszcze żadnych wyników!");
                Thread threadNew = new Thread(()->{
                    try {
                        Thread.sleep(1000);
                        closeHighScores();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                });
                threadNew.start();
            }

        }

    }

    public void closeHighScores(){
        showMenu();
        highScoresOpened = false;
    }

    public void stopGame(){
        endDate();
        showMenu();
        gameStarted = false;
    }

    public void startDate() {
        transportThread.start();
        dateThread.start();
        virusThread.start();
    }

    public void endDate(){
        dateThread.setUpdateDate(false);
        virusThread.setUpdateVirus(false);
        transportThread.setUpdateTransport(false);

    }

    public void gameOver(boolean[] upgrades){
        endDate();
        showMenu();
        gameFrame.dispose();
        boolean repeat = true;
        String name = "";
        while (repeat) {
            name = JOptionPane.showInputDialog(this, "Enter your name", "Game over!", JOptionPane.PLAIN_MESSAGE);
            if(name != null && name.length() == 3 && !name.matches("[0-9]")){
                repeat = false;
            }else{
                System.out.println("Nazwa musi mieć dokładnie 3 litery!");
            }
        }

        int score1 = 0;
        if(virusThread.getUpgrades()[0]){
            score1+= 100;
        }
        if(virusThread.getUpgrades()[1]){
            score1+= 100;
        }
        if(virusThread.getUpgrades()[2]){
            score1+= 100;
        }
        if(virusThread.getUpgrades()[3]){
            score1+= 100;
        }
        if(virusThread.getUpgrades()[4]){
            score1+= 100;
        }
        if(virusThread.getUpgrades()[5]){
            score1+= 100;
        }
        if(virusThread.getUpgrades()[6]){
            score1+= 600;
        }
        if(virusThread.getUpgrades()[7]){
            score1+= 600;
        }
        if(virusThread.getUpgrades()[8]){
            score1+= 600;
        }


        long score2 = dateThread.getFirstDate().until(dateThread.getDate(), ChronoUnit.DAYS);

        int difficultyChoice = virusThread.getDifficultyChoice();
        double difficultyMultiplier = difficultyChoice == 0 ? 1 : difficultyChoice == 1 ? 1.2 : 1.5;
        scores.add(new Score(name, (int)(score1-(int)score2*difficultyMultiplier), difficultyChoice));
        try {
            saveFile(scores, "scores.txt");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        gameStarted = false;
    }

    public static void saveFile(Vector<Score> scores, String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(scores);
        oos.close();
        fos.close();

    }

    public void loadFile(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        scores = (Vector<Score>) ois.readObject();
    }

}
