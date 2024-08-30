package book.loan.system.repository;

import book.loan.system.domain.BookLoanUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLoanUserRepository extends JpaRepository<BookLoanUser, Long> {

    BookLoanUser findByUsername(String name);
}
