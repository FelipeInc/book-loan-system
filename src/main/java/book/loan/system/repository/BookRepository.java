package book.loan.system.repository;

import book.loan.system.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT l FROM Book l WHERE l.title LIKE %:title%")
    Optional<Book> findBookByTitle(@Param("title") String title);
}
