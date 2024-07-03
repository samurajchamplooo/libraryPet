package itgenio.library.service;

import itgenio.library.service.dto.BookDto;
import itgenio.library.service.dto.ReaderDto;
import itgenio.library.service.enums.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Scanner;


@Service
public class ConsoleService implements CommandLineRunner {
    private final BookService bookService;
    private final ReaderService readerService;

    public ConsoleService(BookService bookService, ReaderService readerService) {
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        Level level = Level.TARGET;
        Target target = null;
        Operation operation = null;

        while (true) {
            inputLevel(level, target);
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            if (level == Level.TARGET) {
                target = inputTarget(input);
                if (target == null) continue;
                level = Level.OPERATION;

            }

            if (level == Level.OPERATION) {
                Operation.listing();

                input = scanner.nextLine();
                operation = inputOperation(input);

                if (operation == null) continue;
                level = Level.SUB_OPERATION;

            }

            if (level == Level.SUB_OPERATION) {
                if (Operation.FIND == operation) {
                    listingByTarget(target);
                    input = scanner.nextLine();
                    Object subOperation = inputSubOperationFind(input, target);

                    if (subOperation != null) {
                        handFindSubOperation(target, subOperation, scanner);
                    }
                } else if (Operation.ADD == operation) {
                    handAddOperation(target, scanner);
                } else if (Operation.DELETE == operation){
                    handDeleteOperation(target, scanner);
                } else if (Operation.PIN == operation) {
                    handlePinOperation(scanner);
                } else if (Operation.UNPIN == operation) {
                    handleUnPinOperation(scanner);
                }

                level = Level.TARGET;
            }

            System.out.println("Successfully completed task");
        }

        scanner.close();
    }

    private void handlePinOperation(Scanner scanner) {
        String input;
        System.out.print("Enter the inventory number: ");
        input = scanner.nextLine();
        String inventoryNumber = input;
        System.out.println("Enter the phone: ");
        input = scanner.nextLine();
        String phone = input;
        readerService.handlePinOperation(inventoryNumber, phone);
    }

    private void handleUnPinOperation(Scanner scanner) {
        String input;
        System.out.print("Enter the inventory number: ");
        input = scanner.nextLine();
        String inventoryNumber = input;
        System.out.println("Enter the phone: ");
        input = scanner.nextLine();
        String phone = input;
        readerService.handleUnPinOperation(inventoryNumber, phone);
    }

    private static void listingByTarget(Target target) {
        if (Target.READER == target) {
            SubOperationReaderFind.listing();
        } else {
            SubOperationBookFind.listing();
        }
    }

    private void inputLevel(Level level, Target target) {
        switch (level) {
            case TARGET -> Target.listing();
            case OPERATION -> Operation.listing();
            case SUB_OPERATION -> printSubOperationByTarget(target);
        }
    }

    private void printSubOperationByTarget(Target target) {
        switch (target) {
            case BOOK -> SubOperationBookFind.listing();
            case READER -> SubOperationReaderFind.listing();
        }
    }

    private Target inputTarget(String input) {
        try {
            return Target.valueOf(input.toUpperCase());
        } catch (Exception e) {
            System.out.println("Illegal target: " + input);
            return null;
        }
    }

    private Operation inputOperation(String operation) {
        try {
            return Operation.valueOf(operation.toUpperCase());
        } catch (Exception e) {
            System.out.println("Illegal operation: " + operation);
            return null;
        }
    }

    private Object inputSubOperationFind(String subOperation, Target target) {
        try {
            if (Target.READER == target) {
                return SubOperationReaderFind.valueOf(subOperation.toUpperCase());
            } else {
                return SubOperationBookFind.valueOf(subOperation.toUpperCase());
            }
        } catch (Exception e) {
            System.out.println("Illegal subOperation: " + subOperation);
            return null;
        }
    }

    private void handFindSubOperation(Target target, Object subOperation, Scanner scanner) {
        String input;
        switch (target) {
            case READER -> {
                SubOperationReaderFind subOperationReaderFind = (SubOperationReaderFind) subOperation;
                System.out.print("Enter " + subOperationReaderFind + ": ");
                input = scanner.nextLine();
                readerService.handleSubOperationFind(input, subOperationReaderFind);
            }
            case BOOK -> {
                SubOperationBookFind subOperationBookFind = (SubOperationBookFind) subOperation;
                System.out.print("Enter " + subOperationBookFind + ": ");
                input = scanner.nextLine();
                bookService.handleSubOperationFind(input, subOperationBookFind);
            }
        }
    }

    private void handAddOperation(Target target, Scanner scanner) {
        if (Target.READER == target) {
            readerService.handleAddOperation(getReaderDto(scanner));
        } else {
            bookService.handleAddNewBook(getBookDto(scanner));
        }
    }

    private void handDeleteOperation(Target target, Scanner scanner) {
        if (Target.READER == target) {
            System.out.println("Enter phoneNumber reader for delete: ");
            String input = scanner.nextLine();
            readerService.handleDeleteByPhoneNumber(input);
        } else {
            System.out.println("Enter inventory number book for delete: ");
            String input = scanner.nextLine();
            bookService.handleDeleteByInventoryNumber(input);
        }
    }

    private ReaderDto getReaderDto(Scanner scanner) {
        String input;
        System.out.println("Enter name of new reader: ");
        input = scanner.nextLine();
        String name = input;
        System.out.println("Enter lastname of  new reader: ");
        input = scanner.nextLine();
        String lastname = input;
        System.out.println("Enter phone of new reader: ");
        input = scanner.nextLine();
        String phone = input;
        return new ReaderDto(name, lastname, phone);
    }


    private BookDto getBookDto(Scanner scanner) {
        String input;
        System.out.println("Enter title new book: ");
        input = scanner.nextLine();
        String title = input;
        System.out.println("Enter author new book: ");
        input = scanner.nextLine();
        String author = input;
        System.out.println("Enter genre new book: ");
        input = scanner.nextLine();
        String genre = input;
        System.out.println("Enter inventory new book (numbers of: 0,1,2,3,4,5,6,7,8,9): ");
        input = scanner.nextLine();
        String inventoryNumber = input;
        return new BookDto(title, author, genre, inventoryNumber);
    }

}
