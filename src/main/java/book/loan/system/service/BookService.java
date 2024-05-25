package book.loan.system.service;

import book.loan.system.domain.Book;
import book.loan.system.exception.BadRequestException;
import book.loan.system.repository.BookRepository;
import book.loan.system.request.BookPostRequestBody;
import book.loan.system.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookService  {
    private final Validator validator;
    private final BookRepository bookRepository;

    public Page<Book> listAll(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    public Book save(BookPostRequestBody bookPostRequestBody){
        if(bookPostRequestBody.getIsbn().toString().isEmpty()){
            throw new BadRequestException("The ISBN cannot be Empty");
        }

        Book book = Book.builder()
                .author(validator.authorNameValidator(bookPostRequestBody.getAuthor()))
                .title(validator.bookTitleValidator(bookPostRequestBody.getTitle()))
                .isbn(validator.isbnValidator(bookPostRequestBody.getIsbn()))
                .build();
        return bookRepository.save(book);
    }

}
