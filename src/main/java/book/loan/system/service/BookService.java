package book.loan.system.service;

import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;
import book.loan.system.exception.BadRequestException;
import book.loan.system.exception.NotFoundException;
import book.loan.system.repository.BookRepository;
import book.loan.system.request.BookPostRequestDTO;
import book.loan.system.request.BookPutRequestDTO;
import jakarta.transaction.Transactional;
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

    @Transactional
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

    @Transactional
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

    @Transactional
    public void delete(Long id) {
        bookRepository.delete(findBookByIdOrThrow404(id));
    }

    @Transactional
    public void rentABook(Long id, Loan idLoan) {
        Book bookFounded = findBookByIdOrThrow404(id);
        if (bookFounded.getIdLoan() != null) {
            throw new BadRequestException("This book is already rented");
        }
        Book rentedBook = Book.builder()
                .id(bookFounded.getId())
                .title(bookFounded.getTitle())
                .author(bookFounded.getAuthor())
                .isbn(bookFounded.getIsbn())
                .idLoan(idLoan)
                .build();

        bookRepository.save(rentedBook);

    }

    @Transactional
    public void returnABook(Long id) {
        Book bookFounded = findBookByIdOrThrow404(id);
        if (bookFounded.getIdLoan() == null) {
            throw new BadRequestException("This book has not been rented");
        }
        Book returnedBook = Book.builder()
                .id(bookFounded.getId())
                .title(bookFounded.getTitle())
                .author(bookFounded.getAuthor())
                .isbn(bookFounded.getIsbn())
                .idLoan(null)
                .build();

        bookRepository.save(returnedBook);
    }
}
