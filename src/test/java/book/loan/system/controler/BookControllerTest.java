package book.loan.system.controler;

import book.loan.system.DTO.BookPostRequestDTOCreator;
import book.loan.system.DTO.BookPutRequestDTOCreator;
import book.loan.system.domain.Book;
import book.loan.system.request.BookPostRequestDTO;
import book.loan.system.request.BookPutRequestDTO;
import book.loan.system.service.BookService;
import book.loan.system.util.BookCreator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class BookControllerTest {
    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.createValidBook()));
        BDDMockito.when(bookServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(bookPage);

        BDDMockito.when(bookServiceMock.findBookByIdOrThrow404(ArgumentMatchers.anyLong()))
                .thenReturn(BookCreator.createValidBook());

        BDDMockito.when(bookServiceMock.saveBook(ArgumentMatchers.any(BookPostRequestDTO.class)))
                .thenReturn(BookCreator.createValidBook());


        BDDMockito.doNothing().when(bookServiceMock).updateBook(ArgumentMatchers.any(BookPutRequestDTO.class));

        BDDMockito.doNothing().when(bookServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("List returns list of book inside page object when successful")
    void list_ReturnsListOfBookInsidePageObjects_WhenSuccessful(){
        Long expectedId = BookCreator.createValidBook().getId();
        String expectedTitle = BookCreator.createValidBook().getTitle();
        String expectedAuthor = BookCreator.createValidBook().getAuthor();
        String expectedISBN = BookCreator.createValidBook().getIsbn();

        Page<Book> bookPage = bookController.list(null).getBody();

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
    @DisplayName("List Returns a empty list when has not book")
    void list_ReturnsEmptyList_WhenSuccessful(){
        PageImpl<Book> emptyList = new PageImpl<>(Collections.emptyList());
        BDDMockito.when(bookServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(emptyList);


        Page<Book> books = bookController.list(null).getBody();
        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books.toList())
                .isEmpty();

    }

    @Test
    @DisplayName("FindBookByIdOrThrow404 Returns Book When Successful")
    void findBookByIdOrThrow404_ReturnsBook_WhenSuccessful(){
        Long expectedId = BookCreator.createValidBook().getId();
        String expectedTitle = BookCreator.createValidBook().getTitle();
        String expectedAuthor = BookCreator.createValidBook().getAuthor();
        String expectedISBN = BookCreator.createValidBook().getIsbn();

        Book book = bookController.findBookDetails(1L).getBody();

        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getId()).isNotNull().isEqualTo(expectedId);
        Assertions.assertThat(book.getAuthor()).isEqualTo(expectedAuthor);
        Assertions.assertThat(book.getTitle()).isEqualTo(expectedTitle);
        Assertions.assertThat(book.getIsbn()).isEqualTo(expectedISBN);
    }

    @Test
    @DisplayName("Save Returns Book When Successful")
    void save_ReturnsBook_WhenSuccessful(){
        Book book = bookController.save(BookPostRequestDTOCreator.createBookPostrequestDto()).getBody();

        Assertions.assertThat(book).isNotNull()
                .isEqualTo(BookCreator.createValidBook());

    }

    @Test
    @DisplayName("UpdateBook Updates Book When Successful")
    void updateBook_UpdateBook_WhenSuccessful(){
        Assertions.assertThatCode(() -> bookController.updateBook(BookPutRequestDTOCreator.createBookPutrequestDto()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = bookController.updateBook(BookPutRequestDTOCreator.createBookPutrequestDto());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("DeleteBook Delete Book When Successful")
    void deleteBook_UpdateBook_WhenSuccessful(){
        Assertions.assertThatCode(() -> bookController.deleteBook(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = bookController.deleteBook(1L);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}