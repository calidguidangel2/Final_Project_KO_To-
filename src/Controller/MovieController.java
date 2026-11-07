package Controller;

import model.Movie;
import utils.MergeSort;

import java.util.ArrayList;
import java.util.List;

public class MovieController {

    private final List<Movie> movies;

    public MovieController() {
        movies = new ArrayList<>();
        loadSampleMovies();
    }

    private void loadSampleMovies() {
        movies.add(new Movie("M001", "Inception", "Sci-Fi", 2010, 8.8));
        movies.add(new Movie("M002", "Avatar", "Sci-Fi", 2009, 7.9));
        movies.add(new Movie("M003", "Titanic", "Romance", 1997, 7.9));
        movies.add(new Movie("M004", "The Godfather", "Crime", 1972, 9.2));
        movies.add(new Movie("M005", "Up", "Animation", 2009, 8.3));
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }

    public Movie findMovie(String movieID) {
        for (Movie movie : movies) {
            if (movie.getMovieID().equalsIgnoreCase(movieID.trim())) {
                return movie;
            }
        }
        return null;
    }

    // AUTOMATIC ID: finds the highest number in the list, then adds 1.
    // Example: M001, M002, M005 -> next ID is M006.
    public String getNextID() {
        int highest = 0;

        for (Movie movie : movies) {
            int number = Integer.parseInt(
                    movie.getMovieID().substring(1));
            if (number > highest) {
                highest = number;
            }
        }

        return String.format("M%03d", highest + 1);
    }

    // ADD (the ID is created automatically)
    public void addMovie(String title, String genre,
                         int year, double rating) {
        movies.add(new Movie(getNextID(), title, genre, year, rating));
    }

    // EDIT (the ID never changes)
    public void updateMovie(String movieID, String title, String genre,
                            int year, double rating) {
        Movie movie = findMovie(movieID);

        if (movie == null) {
            throw new IllegalArgumentException("Movie not found.");
        }

        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setReleaseYear(year);
        movie.setRating(rating);
    }

    // DELETE
    public void deleteMovie(String movieID) {
        Movie movie = findMovie(movieID);

        if (movie == null) {
            throw new IllegalArgumentException("Movie not found.");
        }

        movies.remove(movie);
    }

    // Search by title (partial match, ignores upper/lower case).
    public List<Movie> searchByTitle(String query) {
        List<Movie> result = new ArrayList<>();
        String text = query.trim().toLowerCase();

        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(text)) {
                result.add(movie);
            }
        }
        return result;
    }

    // SORTING METHODS (all use Merge Sort)

    public List<Movie> sortByTitle() {
        return MergeSort.sort(movies, MergeSort.BY_TITLE);
    }

    public List<Movie> sortByYear() {
        return MergeSort.sort(movies, MergeSort.BY_YEAR);
    }

    public List<Movie> sortByRating() {
        return MergeSort.sort(movies, MergeSort.BY_RATING);
    }
}