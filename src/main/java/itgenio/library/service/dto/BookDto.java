package itgenio.library.service.dto;

import itgenio.library.entity.Book;
import itgenio.library.entity.enums.Genre;
import itgenio.library.utils.ParserUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private String title;
    private String author;
    private String genre;
    private String inventoryNumber;

    public static Book convertBookDtoToBookEntity(BookDto bookDto) {
        try {
            Genre genre = ParserUtil.parseGenre(bookDto.genre.toUpperCase());
            Long inventoryNumber = ParserUtil.parseLong(bookDto.inventoryNumber);
            if (genre == null) {
                System.out.println("Wrong genre!");
                return null;
            }

            if (inventoryNumber == null) {
                System.out.println("Wrong inventory number!");
                return null;
            }

            return new Book(bookDto.title, bookDto.author, genre, inventoryNumber);
        } catch (Exception e) {
            return null;
        }
    }
}
