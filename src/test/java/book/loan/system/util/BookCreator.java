package book.loan.system.util;

import book.loan.system.domain.Book;
import book.loan.system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BookCreator {
    @Autowired
    BookRepository bookRepository;

    public static Book createBookToBeSaved(){
        return Book.builder()
                .author("authorNameBookTest")
                .title("titleBookTest")
                .isbn("1111111111111")
                .build();
    }

    public static Book createValidBook(){
        return Book.builder()
                .id(1L)
                .author("authorNameBookTest")
                .title("titleBookTest")
                .isbn("1111111111111")
                .build();
    }
    public static Book createBookRented(){
        return Book.builder()
                .id(1L)
                .author("authorNameBookRentedTest")
                .title("titleBookRentedTest")
                .isbn("1111111111111")
                .idLoan(LoanCreator.createLoanToBeRented())
                .build();
    }

    public static Book createBookWithTitleEmpty(){
        return Book.builder()
                .author("authorNameBookTitleEmptyTest")
                .isbn("1111111111111")
                .build();
    }

    public static Book createBookWithAuthorNameEmpty(){
        return Book.builder()
                .title("titleBookAuthorNameEmptyTest")
                .isbn("1111111111111")
                .build();
    }

    public static Book createBookWithISBNEmpty(){
        return Book.builder()
                .author("authorNameBookISBNEmptyTest")
                .title("titleBookISBNEmptyTest")
                .build();
    }

    public static Book createBookWithISBNWithInvalidSize(){
        return Book.builder()
                .author("authorNameBookISBNInvalidSizeTest")
                .title("titleBookISBNInvalidSizeTest")
                .isbn("111111")
                .build();
    }

    public static Book createBookWithISBNWithInvalidValue(){
        return Book.builder()
                .author("authorNameBookISBNInvalidValueTest")
                .title("titleBookISBNInvalidValueTest")
                .isbn("11111111ag@hs")
                .build();
    }
}
