package book.loan.system.DTO;

import book.loan.system.request.BookPutRequestDTO;
import book.loan.system.util.BookCreator;

public class BookPutRequestDTOCreator {
    public static BookPutRequestDTO createBookPutrequestDto() {
        return BookPutRequestDTO.builder()
                .title(BookCreator.createBookToBeSaved().getTitle())
                .author(BookCreator.createBookToBeSaved().getAuthor())
                .isbn(BookCreator.createBookToBeSaved().getIsbn())
                .build();
    }
}
