package itgenio.library.utils;

import itgenio.library.entity.enums.Genre;

import java.time.LocalDate;

public class ParserUtil {
    public static Long parseLong(String inputString) {
        try {
            return Long.parseLong(inputString);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate parseLocalDate(String localDate) {
        try {
            return LocalDate.parse(localDate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Genre parseGenre(String inputString) {
        try {
            return Genre.valueOf(inputString);
        } catch (Exception e) {
            return null;
        }
    }
}
