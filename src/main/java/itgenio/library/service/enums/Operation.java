package itgenio.library.service.enums;

import java.util.Arrays;
import java.util.List;

public enum Operation {
    DELETE,
    FIND,
    ADD,
    PIN,
    UNPIN;

    public static void listing() {
        List<String> operation = Arrays.stream(Operation.values())
                .map(Operation::name)
                .toList();
        System.out.println("Enter operation: " + String.join(", ", operation));
    }
}
