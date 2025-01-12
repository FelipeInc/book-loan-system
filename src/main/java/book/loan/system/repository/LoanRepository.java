package book.loan.system.repository;

import book.loan.system.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    ;
}
