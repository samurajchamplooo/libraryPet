package itgenio.library.service.enums;

import java.util.Arrays;
import java.util.List;

public enum Target {
    BOOK,
    READER;

    public static void listing() {
        List<String> target = Arrays.stream(Target.values())
                .map(Target::name)
                .toList();
        System.out.println("Enter target: " + String.join(", ", target));
    }


}
