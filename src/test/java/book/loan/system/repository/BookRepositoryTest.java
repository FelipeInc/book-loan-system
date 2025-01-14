package book.loan.system.repository;

import book.loan.system.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Book Repository")
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Saves persist book when Successful")
    void Save_PersistBook_WhenSuccessful(){
        Book bookToBeSaved = createBookToBeSaved();
        Book savedBook = this.bookRepository.save(bookToBeSaved);
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(bookToBeSaved.getTitle());
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(bookToBeSaved.getAuthor());
        Assertions.assertThat(savedBook.getIsbn()).isEqualTo(bookToBeSaved.getIsbn());
    }

    @Test
    @DisplayName("Save updates book when Successful")
    void Saves_UpdatesBook_WhenSuccessful(){
        Book bookToBeSaved = createBookToBeSaved();
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
    void Delete_RemovesBook_WhenSuccessful(){
        Book bookToBeSaved = createBookToBeSaved();
        Book savedBook = this.bookRepository.save(bookToBeSaved);

        this.bookRepository.delete(savedBook);
        Optional<Book> bookOptional = this.bookRepository.findById(savedBook.getId());
        Assertions.assertThat(bookOptional).isEmpty();
    }

    private Book createBookToBeSaved(){
        return Book.builder()
                .author("Antoine de Saint-Exupéry")
                .title("O Pequeno Principe")
                .isbn("9780152048044")
                .build();
    }
}

