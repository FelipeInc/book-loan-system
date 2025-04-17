package book.loan.system.repository;

import book.loan.system.domain.Book;
import book.loan.system.util.BookCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Book Repository")

class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Saves persist book when Successful")
    void save_PersistBook_WhenSuccessful(){
        Book bookToBeSaved = BookCreator.createBookToBeSaved();
        Book savedBook = this.bookRepository.save(bookToBeSaved);

        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(bookToBeSaved.getTitle());
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(bookToBeSaved.getAuthor());
        Assertions.assertThat(savedBook.getIsbn()).isEqualTo(bookToBeSaved.getIsbn());
    }

    @Test
    @DisplayName("Save updates book when Successful")
    void saves_UpdatesBook_WhenSuccessful(){
        Book bookToBeSaved = BookCreator.createBookToBeSaved();
        Book savedBook = this.bookRepository.save(bookToBeSaved);

        savedBook.setTitle("O Pequeno Principe");
        savedBook.setAuthor( "William Shakespeare");
        savedBook.setIsbn("9780340153918");

        Book updatedBook = this.bookRepository.save(savedBook);

        Assertions.assertThat(updatedBook).isNotNull();
        Assertions.assertThat(updatedBook.getId()).isNotNull();
        Assertions.assertThat(updatedBook.getTitle()).isEqualTo(savedBook.getTitle());
        Assertions.assertThat(updatedBook.getAuthor()).isEqualTo(savedBook.getAuthor());
        Assertions.assertThat(updatedBook.getIsbn()).isEqualTo(savedBook.getIsbn());
    }

    @Test
    @DisplayName("Delete removes book when Successful")
    void delete_RemovesBook_WhenSuccessful(){
        Book bookToBeSaved = BookCreator.createBookToBeSaved();
        Book savedBook = this.bookRepository.save(bookToBeSaved);

        this.bookRepository.delete(savedBook);

        Optional<Book> bookOptional = this.bookRepository.findById(savedBook.getId());

        Assertions.assertThat(bookOptional).isEmpty();
    }

    @Test
    @DisplayName("Find Book By Id Returns a Book When Successful")
    void findById_ReturnABook_WhenSuccessful(){
        Book bookToBeSaved = BookCreator.createBookToBeSaved();
        Book savedBook = this.bookRepository.save(bookToBeSaved);

        Long bookId = savedBook.getId();

        Optional<Book> bookFind = this.bookRepository.findById(bookId);

        Assertions.assertThat(bookFind).isNotEmpty();
        Assertions.assertThat(bookFind).contains(savedBook);
    }

    @Test
    @DisplayName("Find Book By Id Returns Empty When Id is Not Found")
    void findById_ReturnEmpty_WhenIdIsNotFound(){
        Optional<Book> bookFind = this.bookRepository.findById(1L);

        Assertions.assertThat(bookFind).isEmpty();
    }

    @Test
    @DisplayName("Saves Throw ConstraintViolationException When Title is Empty")
    void save_ThrowConstraintViolationException_WhenTitleIsEmpty(){
        Book bookWithTitleEmpty = BookCreator.createBookWithTitleEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.bookRepository.save(bookWithTitleEmpty))
                .withMessageContaining("The book title can't be Empty");

    }

    @Test
    @DisplayName("Saves Throw ConstraintViolationException When Author Name is Empty")
    void save_ThrowConstraintViolationException_WhenAuthorNameIsEmpty(){
        Book bookWithAuthorNameEmpty = BookCreator.createBookWithAuthorNameEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.bookRepository.save(bookWithAuthorNameEmpty))
                .withMessageContaining("The author name can't be Empty");
    }

    @Test
    @DisplayName("Saves Throw ConstraintViolationException When ISBN is Empty")
    void save_ThrowConstraintViolationException_WhenISBNIsEmpty(){
        Book bookWithISBNEmpty = BookCreator.createBookWithISBNEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.bookRepository.save(bookWithISBNEmpty))
                .withMessageContaining("The ISBN can't be empty");
    }

    @Test
    @DisplayName("Saves Throw ConstraintViolationException When ISBN Size Is Invalid")
    void save_ThrowConstraintViolationException_WhenISBNSizeIsInvalid() {
        Book bookToBeSaved = BookCreator.createBookWithISBNWithInvalidSize();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.bookRepository.save(bookToBeSaved))
                .withMessageContaining("The ISBN must have 13 numbers");
    }

    @Test
    @DisplayName("Saves Throw ConstraintViolationException When ISBN Value Is Invalid")
    void save_ThrowConstraintViolationException_WhenISBNValueIsInvalid(){
        Book bookToBeSaved = BookCreator.createBookWithISBNWithInvalidValue();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.bookRepository.save(bookToBeSaved))
                .withMessageContaining("This field must contain only numbers");
    }

}

