package Main;
import UI.MainFrame;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame("Cni Eugalp");
        });
    }
}
