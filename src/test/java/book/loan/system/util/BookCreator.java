package book.loan.system.util;

import book.loan.system.domain.Book;
import book.loan.system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BookCreator {
    @Autowired
    BookRepository bookRepository;

    public static Book createBookToBeSaved(){
        return Book.builder()
                .author("Antoine de Saint-Exupéry")
                .title("O Pequeno Principe")
                .isbn("9780152048044")
                .build();
    }

    public static Book createValidBook(){
        return Book.builder()
                .id(1L)
                .author("Antoine de Saint-Exupéry")
                .title("O Pequeno Principe")
                .isbn("9780152048044")
                .build();
    }
    public static Book createBookRented(){
        return Book.builder()
                .id(1L)
                .author("Antoine de Saint-Exupéry")
                .title("O Pequeno Principe")
                .isbn("9780152048044")
                .idLoan(LoanCreator.createLoanToBeRented())
                .build();
    }
    public static Book createBookReturned(){
        return Book.builder()
                .id(1L)
                .author("Antoine de Saint-Exupéry")
                .title("O Pequeno Principe")
                .isbn("9780152048044")
                .idLoan(null)
                .build();
    }

    public static Book createUpdatedBook(){
        return Book.builder()
                .id(1L)
                .author( "William Shakespeare")
                .title("O Romeu e a Juieta")
                .isbn("9780340153918")
                .build();
    }

    public static Book createBookWithTitleEmpty(){
        return Book.builder()
                .author("Antoine de Saint-Exupéry")
                .isbn("9780152048044")
                .build();
    }

    public static Book createBookWithAuthorNameEmpty(){
        return Book.builder()
                .title("O Pequeno Principe")
                .isbn("9780152048044")
                .build();
    }

    public static Book createBookWithISBNEmpty(){
        return Book.builder()
                .title("O Pequeno Principe")
                .author("Antoine de Saint-Exupéry")
                .build();
    }

    public static Book createBookWithISBNWithInvalidSize(){
        return Book.builder()
                .title("O Pequeno Principe")
                .author("Antoine de Saint-Exupéry")
                .isbn("97801520480")
                .build();
    }

    public static Book createBookWithISBNWithInvalidValue(){
        return Book.builder()
                .title("O Pequeno Principe")
                .author("Antoine de Saint-Exupéry")
                .isbn("97801520480d@")
                .build();
    }
}
