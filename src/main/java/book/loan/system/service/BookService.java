package book.loan.system.service;

import book.loan.system.domain.Book;
import book.loan.system.exception.BadRequestException;
import book.loan.system.repository.BookRepository;
import book.loan.system.request.BookPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookService  {
    private final BookRepository bookRepository;

    public Page<Book> listAll(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    public Book save(BookPostRequestBody bookPostRequestBody){
        if(bookPostRequestBody.getIsbn().toString().isEmpty()){
            throw new BadRequestException("The ISBN cannot be Empty");
        }
        Book book = Book.builder()
                .author(bookPostRequestBody.getAuthor())
                .title(bookPostRequestBody.getTitle())
                .isbn(bookPostRequestBody.getIsbn())
                .build();
        return bookRepository.save(book);
    }

}
