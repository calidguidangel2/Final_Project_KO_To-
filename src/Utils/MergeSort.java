package utils;

import model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Merge Sort (Divide and Conquer) for Movie records.
 * Sorts in ascending order by title, release year, or rating.
 */
public class MergeSort {

    public static final int BY_TITLE = 1;
    public static final int BY_YEAR = 2;
    public static final int BY_RATING = 3;

    // Public method: returns a NEW sorted list (original is unchanged).
    public static List<Movie> sort(List<Movie> movies, int sortType) {
        List<Movie> copy = new ArrayList<>(movies);
        return mergeSort(copy, sortType);
    }

    // DIVIDE: split the list in half until each part has 0 or 1 item.
    private static List<Movie> mergeSort(List<Movie> list, int sortType) {
        if (list.size() <= 1) {
            return list;
        }

        int middle = list.size() / 2;

        List<Movie> left = new ArrayList<>(list.subList(0, middle));
        List<Movie> right = new ArrayList<>(
                list.subList(middle, list.size()));

        left = mergeSort(left, sortType);
        right = mergeSort(right, sortType);

        return merge(left, right, sortType);
    }

    // CONQUER: merge two sorted lists into one sorted list.
    private static List<Movie> merge(List<Movie> left,
                                     List<Movie> right,
                                     int sortType) {
        List<Movie> merged = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (compare(left.get(i), right.get(j), sortType) <= 0) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

        // Add whatever is left over.
        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }

        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }

        return merged;
    }

    // Negative: a comes first, 0: equal, positive: b comes first.
    public static int compare(Movie a, Movie b, int sortType) {
        switch (sortType) {
            case BY_YEAR:
                return Integer.compare(a.getReleaseYear(),
                                       b.getReleaseYear());
            case BY_RATING:
                return Double.compare(a.getRating(), b.getRating());
            case BY_TITLE:
            default:
                return a.getTitle().compareToIgnoreCase(b.getTitle());
        }
    }
}