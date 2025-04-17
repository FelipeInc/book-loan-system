package book.loan.system.service;

import book.loan.system.domain.Book;
import book.loan.system.exception.NotFoundException;
import book.loan.system.repository.BookRepository;
import book.loan.system.request.BookPostRequestDTO;
import book.loan.system.request.BookPutRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@RequiredArgsConstructor
@Validated
public class BookService {
    private final BookRepository bookRepository;

    public Page<Book> listAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book saveBook(@Valid BookPostRequestDTO bookPostRequestDTO) {
        Book book = Book.builder()
                .author(bookPostRequestDTO.author())
                .title(bookPostRequestDTO.title())
                .isbn(bookPostRequestDTO.isbn())
                .build();
        return bookRepository.save(book);
    }

    public Book findBookByIdOrThrow404(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not Found"));
    }

    public void updateBook(BookPutRequestDTO bookPutRequestBody) {
        findBookByIdOrThrow404(bookPutRequestBody.id());
        Book updatedBook = Book.builder()
                .id(bookPutRequestBody.id())
                .title(bookPutRequestBody.title())
                .author(bookPutRequestBody.author())
                .isbn(bookPutRequestBody.isbn())
                .build();
        bookRepository.save(updatedBook);
    }

    public void delete(Long id) {
        bookRepository.delete(findBookByIdOrThrow404(id));
    }
}
