package book.loan.system.DTO;

import book.loan.system.request.BookPutRequestDTO;
import book.loan.system.util.BookCreator;

public class BookPutRequestDTOCreator {
    public static BookPutRequestDTO createBookPutrequestDto() {
        return BookPutRequestDTO.builder()
                .id(BookCreator.createValidBook().getId())
                .title(BookCreator.createValidBook().getTitle())
                .author(BookCreator.createValidBook().getAuthor())
                .isbn(BookCreator.createValidBook().getIsbn())
                .build();
    }
}
