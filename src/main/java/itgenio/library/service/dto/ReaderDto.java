package itgenio.library.service.dto;

import itgenio.library.entity.Book;
import itgenio.library.entity.Reader;
import itgenio.library.entity.enums.Genre;
import itgenio.library.utils.ParserUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReaderDto {
    private String name;
    private String lastname;
    private String phone;

    public static Reader convertReaderDtoToReaderEntity(ReaderDto readerDto) {
        return new Reader(readerDto.name, readerDto.lastname, readerDto.phone);
    }
}
