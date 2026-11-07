package utils;

import model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple console test for the Merge Sort algorithm.
 * Run this file by itself (Shift + F6 in NetBeans).
 */
public class MergeSort_test {

    public static void main(String[] args) {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("M001", "Inception", "Sci-Fi", 2010, 8.8));
        movies.add(new Movie("M002", "Avatar", "Sci-Fi", 2009, 7.9));
        movies.add(new Movie("M003", "Titanic", "Romance", 1997, 7.9));
        movies.add(new Movie("M004", "The Godfather", "Crime", 1972, 9.2));
        movies.add(new Movie("M005", "Up", "Animation", 2009, 8.3));
        movies.add(new Movie("M006", "Jaws", "Thriller", 1975, 8.1));

        System.out.println("ORIGINAL RECORDS");
        printMovies(movies);

        boolean allPassed = true;

        allPassed &= runTest("Title", movies, MergeSort.BY_TITLE);
        allPassed &= runTest("Release Year", movies, MergeSort.BY_YEAR);
        allPassed &= runTest("Rating", movies, MergeSort.BY_RATING);

        // Edge cases: empty list and single item.
        allPassed &= check("Empty list",
                MergeSort.sort(new ArrayList<Movie>(),
                        MergeSort.BY_TITLE));

        List<Movie> single = new ArrayList<>();
        single.add(movies.get(0));
        allPassed &= check("Single item",
                MergeSort.sort(single, MergeSort.BY_TITLE));

        System.out.println();
        System.out.println(allPassed
                ? "ALL TESTS PASSED"
                : "SOME TESTS FAILED");
    }

    private static boolean runTest(String name, List<Movie> movies,
                                   int sortType) {
        List<Movie> sorted = MergeSort.sort(movies, sortType);

        System.out.println();
        System.out.println("SORTED BY " + name.toUpperCase());
        printMovies(sorted);

        boolean ok = isSorted(sorted, sortType)
                && sorted.size() == movies.size();
        System.out.println("Result: " + (ok ? "PASS" : "FAIL"));
        return ok;
    }

    private static boolean check(String name, List<Movie> sorted) {
        boolean ok = isSorted(sorted, MergeSort.BY_TITLE);
        System.out.println(name + ": " + (ok ? "PASS" : "FAIL"));
        return ok;
    }

    // A list is sorted if no item is bigger than the one after it.
    private static boolean isSorted(List<Movie> list, int sortType) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (MergeSort.compare(list.get(i), list.get(i + 1),
                    sortType) > 0) {
                return false;
            }
        }
        return true;
    }

    private static void printMovies(List<Movie> movies) {
        for (Movie m : movies) {
            System.out.printf("%-6s %-15s %-10s %-6d %.1f%n",
                    m.getMovieID(), m.getTitle(), m.getGenre(),
                    m.getReleaseYear(), m.getRating());
        }
    }
}