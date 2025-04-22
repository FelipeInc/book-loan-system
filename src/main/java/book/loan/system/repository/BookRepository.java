package book.loan.system.repository;

import book.loan.system.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Repository
@Validated
public interface BookRepository extends JpaRepository<Book, Long> {

}
