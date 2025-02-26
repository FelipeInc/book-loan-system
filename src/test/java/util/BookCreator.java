package util;

import book.loan.system.domain.Book;

public class BookCreator {

    public Book createBookToBeSaved(){
        Book book = Book.builder()
                .author("Antoine de Saint-Exupéry")
                .title("O Pequeno Principe")
                .isbn("9780152048044")
                .build();
        return book;
    }

    public Book createValidBook(){
        return Book.builder()
                .id(1L)
                .author("Antoine de Saint-Exupéry")
                .title("O Pequeno Principe")
                .isbn("9780152048044")
                .build();
    }

    public Book createUpdatedBook() {
        return Book.builder()
                .id(1L)
                .author("Joanne Rowling")
                .title("Harry Potter e a Pedra Filosofal")
                .isbn("9780545069670")
                .build();
    }
        //public Book createVBook(){
    //    return Book.builder()
    //            .author("William Shakespeare")
    //            .title("Romeu e Julieta")
    //            .isbn("9780340153918")
    //            .build();
    //}


}
