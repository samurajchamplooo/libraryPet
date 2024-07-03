package itgenio.library.service.enums;

import java.util.Arrays;
import java.util.List;

public enum SubOperationBookFind {
    AUTHOR,
    TITLE,
    INVENTORY_NUMBER,
    GENRE,
    START_DATE,
    END_DATE;

    public static void listing() {
        List<String> subOperationBook = Arrays.stream(SubOperationBookFind.values())
                .map(SubOperationBookFind::name)
                .toList();
        System.out.println("Enter sub operation for book: " + String.join(", ", subOperationBook));
    }
}
