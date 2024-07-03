package itgenio.library.repository;


import itgenio.library.entity.Book;
import itgenio.library.entity.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthor(String author);

    List<Book> findAllByTitle(String title);

    @Query(value = "SELECT * FROM library.book where inventory_number =:inventoryNumber", nativeQuery = true)
    Book findByInventoryNumber(Long inventoryNumber);

    List<Book> findAllByGenre(Genre genre);

    List<Book> findAllByStartDate(LocalDate startDate);

    List<Book> findAllByEndDate(LocalDate endDate);


    void deleteByInventoryNumber(Long inventoryNumber);
}
