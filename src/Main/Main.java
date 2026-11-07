package main;

import Controller.MovieController;
import javax.swing.SwingUtilities;
import view.MovieView;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovieController controller = new MovieController();
            new MovieView(controller).setVisible(true);
        });
    }
}