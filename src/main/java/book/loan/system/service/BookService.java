package book.loan.system.service;

import book.loan.system.domain.Book;
import book.loan.system.exception.BadRequestException;
import book.loan.system.repository.BookRepository;
import book.loan.system.request.BookPostRequestBody;
import book.loan.system.request.BookPutRequestBody;
import book.loan.system.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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

    public Book findBookByIdOrThrow404(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not Found"));
    }

    public void updateBook(BookPutRequestBody bookPutRequestBody){
        findBookByIdOrThrow404(bookPutRequestBody.getId());
        Book updatedBook = Book.builder()
                .id(bookPutRequestBody.getId())
                .title(bookPutRequestBody.getTitle())
                .author(bookPutRequestBody.getAuthor())
                .isbn(bookPutRequestBody.getIsbn())
                .build();
        bookRepository.save(updatedBook);
    }

    public void delete(Long id){
        bookRepository.delete(findBookByIdOrThrow404(id));
    }
}
