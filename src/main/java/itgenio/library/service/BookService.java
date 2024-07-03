package itgenio.library.service;

import itgenio.library.entity.Book;
import itgenio.library.entity.enums.Genre;
import itgenio.library.repository.BookRepository;
import itgenio.library.service.dto.BookDto;
import itgenio.library.service.enums.SubOperationBookFind;
import itgenio.library.utils.ParserUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void handleSubOperationFind(String input, SubOperationBookFind subOperationBookFind) {
        switch (subOperationBookFind) {
            case AUTHOR -> findBooksByAuthor(input);
            case TITLE -> findBooksByTitle(input);
            case INVENTORY_NUMBER -> findBookByInventoryNumber(input);
            case GENRE -> findBooksByGenre(input);
            case START_DATE -> findBooksByStartDate(input);
            case END_DATE -> findBooksByEndDate(input);
        }
    }

    public void handleAddNewBook(BookDto bookDto) {
        Book book = BookDto.convertBookDtoToBookEntity(bookDto);
        if (book == null) {
            return;
        }
        book = bookRepository.save(book);
        System.out.println("Successfully saved book: " + book);
    }

    public void handleDeleteByInventoryNumber(String inventoryNumberInput) {
        Long inventoryNumber = ParserUtil.parseLong(inventoryNumberInput);
        if (inventoryNumber == null) {
            System.out.println("Wrong inventory number:" + inventoryNumberInput);
            return;
        }
        bookRepository.deleteByInventoryNumber(inventoryNumber);
        System.out.println("Successfully deleted");
    }


    private void findBooksByAuthor(String author) {
        List<Book> bookByAuthor = bookRepository.findAllByAuthor(author);

        if (bookByAuthor.isEmpty()) {
            System.out.println("No books of this author: " + author);
        }

        System.out.println("Found: " + bookByAuthor.size() + " of " + bookByAuthor);
    }

    private void findBooksByTitle(String title) {
        List<Book> allByTitle = bookRepository.findAllByTitle(title);

        if (allByTitle.isEmpty()) {
            System.out.println("No books of this title: " + title);
        }

        System.out.println("Found: " + allByTitle.size() + " of " + allByTitle);
    }

    private void findBookByInventoryNumber(String inventory) {
        Long number = ParserUtil.parseLong(inventory);
        if (number == null) {
            System.out.println("Wrong inventory number:" + inventory);
            return;
        }

        Book bookByInventoryNumber = bookRepository.findByInventoryNumber(number);
        if (bookByInventoryNumber == null) {
            System.out.println("No such  book of this inventory number: " + number);
        }

        System.out.println("Found: " + bookByInventoryNumber);
    }

    private void findBooksByGenre(String genre) {
        Genre genreParsed = ParserUtil.parseGenre(genre);
        if (genreParsed == null) {
            System.out.println("Wrong genre:" + genre);
            return;
        }

        List<Book> allByGenre = bookRepository.findAllByGenre(genreParsed);
        if (allByGenre.isEmpty()) {
            System.out.println("No books of this genre: " + genre);
            return;
        }
        System.out.println("Found: " + allByGenre.size() + " of " + allByGenre);
    }

    private void findBooksByStartDate(String startDateInput) {
        LocalDate startDate = ParserUtil.parseLocalDate(startDateInput);
        if (startDate == null) {
            System.out.println("Wrong start date:" + startDateInput);
            return;
        }

        List<Book> allByStartDate = bookRepository.findAllByStartDate(startDate);
        if (allByStartDate.isEmpty()) {
            System.out.println("No books of this start date: " + startDate);
            return;
        }
        System.out.println("Found: " + allByStartDate.size() + " of " + allByStartDate);
    }

    private void findBooksByEndDate(String endDateInput) {
        LocalDate endDate = ParserUtil.parseLocalDate(endDateInput);

        if (endDate == null) {
            System.out.println("Wrong end date:" + endDateInput);
            return;
        }

        List<Book> allByEndDate = bookRepository.findAllByEndDate(endDate);

        if (allByEndDate.isEmpty()) {
            System.out.println("No books of this end date: " + endDate);
            return;
        }
        System.out.println("Found: " + allByEndDate.size() + " of " + allByEndDate);
    }

}
