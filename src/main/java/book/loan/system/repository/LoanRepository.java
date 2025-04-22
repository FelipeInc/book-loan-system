package book.loan.system.repository;

import book.loan.system.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Repository
@Validated
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
