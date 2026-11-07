package view;

import Controller.MovieController;
import model.Movie;
import utils.MovieUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MovieView extends JFrame {

    private final MovieController controller;

    private JTextField idField;
    private JTextField titleField;
    private JTextField genreField;
    private JTextField yearField;
    private JTextField ratingField;
    private JTextField searchField;
    private DefaultTableModel tableModel;
    private JTable table;

    public MovieView(MovieController controller) {
        this.controller = controller;

        setTitle("Movie Collection");
        setSize(850, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // TITLE
        JLabel title = new JLabel("Movie Collection",
                SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        add(title, BorderLayout.NORTH);

        // INPUT FIELDS
        idField = new JTextField(6);
        idField.setEditable(false);   // ID is automatic
        titleField = new JTextField(12);
        genreField = new JTextField(8);
        yearField = new JTextField(5);
        ratingField = new JTextField(4);
        searchField = new JTextField(12);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Genre:"));
        inputPanel.add(genreField);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Rating:"));
        inputPanel.add(ratingField);

        // BUTTONS
        JButton addButton = new JButton("Add Movie");
        JButton editButton = new JButton("Edit Movie");
        JButton deleteButton = new JButton("Delete Movie");
        JButton clearButton = new JButton("Clear Input");
        JButton showAllButton = new JButton("Show All");
        JButton searchButton = new JButton("Search Title");
        JButton sortTitleButton = new JButton("Sort by Title");
        JButton sortYearButton = new JButton("Sort by Year");
        JButton sortRatingButton = new JButton("Sort by Rating");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(showAllButton);

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(sortTitleButton);
        searchPanel.add(sortYearButton);
        searchPanel.add(sortRatingButton);

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(inputPanel);
        topPanel.add(buttonPanel);
        topPanel.add(searchPanel);

        // TABLE
        tableModel = new DefaultTableModel(
                new String[]{"Movie ID", "Title", "Genre",
                    "Release Year", "Rating"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // LAYOUT
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // BUTTON ACTIONS
        addButton.addActionListener(e -> addMovie());
        editButton.addActionListener(e -> editMovie());
        deleteButton.addActionListener(e -> deleteMovie());
        clearButton.addActionListener(e -> clearInputs());
        showAllButton.addActionListener(e ->
                displayMovies(controller.getMovies()));
        searchButton.addActionListener(e -> searchMovies());
        searchField.addActionListener(e -> searchMovies());
        sortTitleButton.addActionListener(e ->
                displayMovies(controller.sortByTitle()));
        sortYearButton.addActionListener(e ->
                displayMovies(controller.sortByYear()));
        sortRatingButton.addActionListener(e ->
                displayMovies(controller.sortByRating()));

        // Fill the input fields when a row is selected.
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();

                if (row >= 0) {
                    idField.setText(tableModel.getValueAt(row, 0).toString());
                    titleField.setText(tableModel.getValueAt(row, 1).toString());
                    genreField.setText(tableModel.getValueAt(row, 2).toString());
                    yearField.setText(tableModel.getValueAt(row, 3).toString());
                    ratingField.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        displayMovies(controller.getMovies());
        clearInputs();
    }

    // Checks the fields. Returns true if everything is valid.
    private boolean inputIsValid() {
        if (MovieUtils.isBlank(titleField.getText())
                || MovieUtils.isBlank(genreField.getText())
                || MovieUtils.isBlank(yearField.getText())
                || MovieUtils.isBlank(ratingField.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.");
            return false;
        }

        if (!MovieUtils.isValidYear(yearField.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Release year must be a number (1888 - 2100).");
            return false;
        }

        if (!MovieUtils.isValidRating(ratingField.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Rating must be a number from 0 to 10.");
            return false;
        }

        return true;
    }

    // ADD
    private void addMovie() {
        if (!inputIsValid()) {
            return;
        }

        controller.addMovie(
                titleField.getText().trim(),
                genreField.getText().trim(),
                Integer.parseInt(yearField.getText().trim()),
                Double.parseDouble(ratingField.getText().trim()));

        clearInputs();
        displayMovies(controller.getMovies());
        JOptionPane.showMessageDialog(this, "Movie added successfully.");
    }

    // EDIT
    private void editMovie() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a movie to edit.");
            return;
        }

        if (!inputIsValid()) {
            return;
        }

        // Use the ID from the table row (it stays correct after sorting).
        String id = tableModel.getValueAt(row, 0).toString();

        controller.updateMovie(
                id,
                titleField.getText().trim(),
                genreField.getText().trim(),
                Integer.parseInt(yearField.getText().trim()),
                Double.parseDouble(ratingField.getText().trim()));

        clearInputs();
        displayMovies(controller.getMovies());
        JOptionPane.showMessageDialog(this,
                "Movie updated successfully.");
    }

    // DELETE
    private void deleteMovie() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a movie to delete.");
            return;
        }

        String id = tableModel.getValueAt(row, 0).toString();
        String movieTitle = tableModel.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + movieTitle + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteMovie(id);
            clearInputs();
            displayMovies(controller.getMovies());
            JOptionPane.showMessageDialog(this,
                    "Movie deleted successfully.");
        }
    }

    // SEARCH
    private void searchMovies() {
        String query = searchField.getText();

        if (MovieUtils.isBlank(query)) {
            displayMovies(controller.getMovies());
            return;
        }

        List<Movie> results = controller.searchByTitle(query);
        displayMovies(results);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No movies found.");
        }
    }

    private void clearInputs() {
        table.clearSelection();
        idField.setText(controller.getNextID());   // shows the next ID
        titleField.setText("");
        genreField.setText("");
        yearField.setText("");
        ratingField.setText("");
        searchField.setText("");
    }

    // DISPLAY TABLE
    private void displayMovies(List<Movie> movies) {
        tableModel.setRowCount(0);

        for (Movie movie : movies) {
            tableModel.addRow(new Object[]{
                movie.getMovieID(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getReleaseYear(),
                MovieUtils.formatRating(movie.getRating())
            });
        }
    }
}