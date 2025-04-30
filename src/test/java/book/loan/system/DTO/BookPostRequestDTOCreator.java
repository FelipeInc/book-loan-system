package book.loan.system.DTO;

import book.loan.system.request.BookPostRequestDTO;
import book.loan.system.util.BookCreator;

public class BookPostRequestDTOCreator {
    public static BookPostRequestDTO createBookPostrequestDto() {
        return BookPostRequestDTO.builder()
                .title(BookCreator.createBookToBeSaved().getTitle())
                .author(BookCreator.createBookToBeSaved().getAuthor())
                .isbn(BookCreator.createBookToBeSaved().getIsbn())
                .build();
    }
}
