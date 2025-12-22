import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainWindow());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}