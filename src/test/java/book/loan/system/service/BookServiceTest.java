package book.loan.system.service;

import book.loan.system.DTO.BookPostRequestDTOCreator;
import book.loan.system.DTO.BookPutRequestDTOCreator;
import book.loan.system.domain.Book;
import book.loan.system.exception.BadRequestException;
import book.loan.system.exception.NotFoundException;
import book.loan.system.repository.BookRepository;
import book.loan.system.util.BookCreator;
import book.loan.system.util.LoanCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.createValidBook()));
        BDDMockito.when(bookRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(bookPage);

        BDDMockito.when(bookRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.findBookByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(BookCreator.createValidBook());

        BDDMockito.doNothing().when(bookRepositoryMock).delete(ArgumentMatchers.any(Book.class));
    }

    @Test
    @DisplayName("List Returns List Of Book Inside Page Object When Successful")
    void list_ReturnsListOfBookInsidePageObjects_WhenSuccessful() {
        Long expectedId = BookCreator.createValidBook().getId();
        String expectedTitle = BookCreator.createValidBook().getTitle();
        String expectedAuthor = BookCreator.createValidBook().getAuthor();
        String expectedISBN = BookCreator.createValidBook().getIsbn();

        Page<Book> bookPage = bookService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(bookPage).isNotNull();
        Assertions.assertThat(bookPage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(bookPage.toList().getFirst().getTitle()).isEqualTo(expectedTitle);
        Assertions.assertThat(bookPage.toList().getFirst().getAuthor()).isEqualTo(expectedAuthor);
        Assertions.assertThat(bookPage.toList().getFirst().getId()).isEqualTo(expectedId);
        Assertions.assertThat(bookPage.toList().getFirst().getIsbn()).isEqualTo(expectedISBN);

    }

    @Test
    @DisplayName("FindBookByIdOrThrow404 Returns Book When Successful")
    void findBookByIdOrThrow404_ReturnsBook_WhenSuccessful() {
        Long expectedId = BookCreator.createValidBook().getId();
        String expectedTitle = BookCreator.createValidBook().getTitle();
        String expectedAuthor = BookCreator.createValidBook().getAuthor();
        String expectedISBN = BookCreator.createValidBook().getIsbn();

        Book book = bookService.findBookByIdOrThrow404(1L);

        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getId()).isNotNull().isEqualTo(expectedId);
        Assertions.assertThat(book.getAuthor()).isEqualTo(expectedAuthor);
        Assertions.assertThat(book.getTitle()).isEqualTo(expectedTitle);
        Assertions.assertThat(book.getIsbn()).isEqualTo(expectedISBN);
    }

    @Test
    @DisplayName("FindBookByIdOrThrow404 throw404 When book is not found")
    void findBookByIdOrThrow404_Throws404_WhenBookIsNotFound() {
        BDDMockito.when(bookRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(()->bookService.findBookByIdOrThrow404(1L))
                .withMessageContaining("Book not found");
    }

    @Test
    @DisplayName("Save Returns Book When Successful")
    void save_ReturnsBook_WhenSuccessful() {
        Book book = bookService.saveBook(BookPostRequestDTOCreator.createBookPostrequestDto());

        Assertions.assertThat(book).isNotNull()
                .isEqualTo(BookCreator.createValidBook());

    }

    @Test
    @DisplayName("UpdateBook Updates Book When Successful")
    void updateBook_UpdateBook_WhenSuccessful() {
        Assertions.assertThatCode(() -> bookService.updateBook(BookPutRequestDTOCreator.createBookPutrequestDto()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("DeleteBook Delete Book When Successful")
    void deleteBook_UpdateBook_WhenSuccessful() {
        Assertions.assertThatCode(() -> bookService.delete(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("rentABook update idLoan when successful")
    void rentABook_UpdateIdLoan_WhenSuccessful() {
        BDDMockito.when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(BookCreator.createValidBook());

        Assertions.assertThatCode(() -> bookService.rentABook("title", LoanCreator.createValidLoan()))
                .doesNotThrowAnyException();
    }
    @Test
    @DisplayName("rentABook Throw BadRequestException when book is already rented")
    void rentABook_ThrowBadRequestException_WhenBookIsAlreadyRented() {
        BDDMockito.when(bookRepositoryMock.findBookByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(BookCreator.createBookRented()));

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(()-> bookService.rentABook("title", LoanCreator.createValidLoan()))
                .withMessageContaining("This book is already rented");
    }

    @Test
    @DisplayName("returnABook update idLoan when successful")
    void returnABook_UpdateIdLoan_WhenSuccessful() {
        BDDMockito.when(bookRepositoryMock.findBookByTitle(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(BookCreator.createBookRented()));

        BDDMockito.when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(BookCreator.createBookRented());

        Assertions.assertThatCode(() -> bookService.returnABook("title"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("rentABook Throw BadRequestException when book has not been rented")
    void returnABook_ThrowBadRequestException_WhenBookHasNotBeenRented() {
        BDDMockito.when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(BookCreator.createBookRented());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> bookService.returnABook("title"))
                .withMessageContaining("This book has not been rented");
    }


}