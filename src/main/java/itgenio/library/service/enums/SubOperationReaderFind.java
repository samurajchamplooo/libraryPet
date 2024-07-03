package itgenio.library.service.enums;

import java.util.Arrays;
import java.util.List;

public enum SubOperationReaderFind {
    NAME,
    LASTNAME,
    PHONE,
    LIST_BOOKS;

    public static void listing() {
        List<String> subOperationReader = Arrays.stream(SubOperationReaderFind.values())
                .map(SubOperationReaderFind::name)
                .toList();
        System.out.println("Enter sub operation reader: " + String.join(", ", subOperationReader));
    }
}
