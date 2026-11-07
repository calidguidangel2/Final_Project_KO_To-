package utils;

public final class MovieUtils {

    private MovieUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Year must be a whole number between 1888 and 2100.
    public static boolean isValidYear(String text) {
        try {
            int year = Integer.parseInt(text.trim());
            return year >= 1888 && year <= 2100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Rating must be a number from 0 to 10.
    public static boolean isValidRating(String text) {
        try {
            double rating = Double.parseDouble(text.trim());
            return rating >= 0 && rating <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String formatRating(double rating) {
        return String.format("%.1f", rating);
    }
}