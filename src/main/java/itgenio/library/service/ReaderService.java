package itgenio.library.service;

import itgenio.library.entity.Book;
import itgenio.library.entity.Reader;
import itgenio.library.repository.BookRepository;
import itgenio.library.repository.ReaderRepository;
import itgenio.library.service.dto.ReaderDto;
import itgenio.library.service.enums.SubOperationReaderFind;
import itgenio.library.utils.ParserUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    public ReaderService(ReaderRepository readerRepository, BookRepository bookRepository) {
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }


    public void handleSubOperationFind(String input, SubOperationReaderFind subOperationReaderFind) {
        switch (subOperationReaderFind) {
            case NAME -> findAllByName(input);
            case LASTNAME -> findAllByLastName(input);
            case PHONE -> findAllByPhone(input);
            case LIST_BOOKS -> findAllBooksByPhone(input);
        }
    }


    public void handleDeleteByPhoneNumber(String input) {
        readerRepository.deleteByPhone(input);
        System.out.println("Successfully delete reader by phone: " + input);
    }

    public void handleAddOperation(ReaderDto readerDto) {
        Reader reader = ReaderDto.convertReaderDtoToReaderEntity(readerDto);
        reader = readerRepository.save(reader);
        System.out.println("Successfully saved reader: " + reader);
    }

    public void handlePinOperation(String inventoryNumber, String phone) {
        pinBookByInventoryNumber(inventoryNumber, phone);
    }

    public void handleUnPinOperation(String inventoryNumber, String phone) {
        unPinBookByInventoryNumber(inventoryNumber, phone);
    }

    private void findAllByName(String name) {
        List<Reader> allByName = readerRepository.findAllByName(name);
        if (allByName.isEmpty()) {
            System.out.println("Can't find readers by name: " + name);
            return;
        }
        System.out.println("Successfully find readers: " + allByName);
    }

    private void findAllByLastName(String lastname) {
        List<Reader> allByLastname = readerRepository.findAllByLastname(lastname);
        if (allByLastname.isEmpty()) {
            System.out.println("Can't find readers by lastname: " + lastname);
            return;
        }
        System.out.println("Successfully find readers: " + allByLastname);
    }

    private void findAllByPhone(String phone) {
        Reader byPhone = readerRepository.findByPhone(phone);
        if (byPhone == null) {
            System.out.println("Can't find reader by phone: " + phone);
            return;
        }
        System.out.println("Successfully found reader: " + byPhone);
    }

    private void findAllBooksByPhone(String phone) {
        Reader byPhone = readerRepository.findByPhone(phone);
        if (byPhone == null) {
            System.out.println("Can't find reader by phone: " + phone);
            return;
        }

        Set<Book> books = byPhone.getBooks();

        if (books.isEmpty()) {
            System.out.println("Can't find books  by phone: " + phone);
        }
        System.out.println("Successfully find books for reader with phone: " + byPhone + " books: " + books);
    }

    private void pinBookByInventoryNumber(String inventoryNumber, String phone) {
        Reader byPhone = readerRepository.findByPhone(phone);
        if (byPhone == null) {
            System.out.println("Can't find reader by phone: " + phone);
            return;
        }
        Long inventoryNumberLong = ParserUtil.parseLong(inventoryNumber);
        if (inventoryNumberLong == null) {
            System.out.println("Can't find inventory number: " + inventoryNumberLong);
            return;
        }
        Book bookSearched = bookRepository.findByInventoryNumber(inventoryNumberLong);
        if (bookSearched == null) {
            System.out.println("Can't find book by inventory number: " + inventoryNumberLong);
            return;
        }

        Set<Book> books = byPhone.getBooks();
        bookSearched.setReader(byPhone);
        books.add(bookSearched);
        Reader reader = readerRepository.save(byPhone);
        System.out.println("Successfully pinned books for reader with phone: " + reader.getBooks());
    }

    private void unPinBookByInventoryNumber(String inventoryNumber, String phone) {
        Reader byPhone = readerRepository.findByPhone(phone);
        if (byPhone == null) {
            System.out.println("Can't find reader by phone: " + phone);
            return;
        }
        Long inventoryNumberLong = ParserUtil.parseLong(inventoryNumber);
        if (inventoryNumberLong == null) {
            System.out.println("Can't find inventory number: " + inventoryNumberLong);
            return;
        }
        Book bookSearched = bookRepository.findByInventoryNumber(inventoryNumberLong);
        if (bookSearched == null) {
            System.out.println("Can't find book by inventory number: " + inventoryNumberLong);
            return;
        }

        Set<Book> books = byPhone.getBooks();

       Set<Book> finalSet = books.stream()
               .filter(e-> !Objects.equals(e.getInventoryNumber(), bookSearched.getInventoryNumber()))
               .collect(Collectors.toSet());
        byPhone.setBooks(finalSet);
        Reader reader = readerRepository.save(byPhone);
        System.out.println("Successfully unpinned books for reader with phone: " + reader.getBooks());
    }
}
