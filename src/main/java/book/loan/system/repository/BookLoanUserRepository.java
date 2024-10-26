package book.loan.system.repository;

import book.loan.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface BookLoanUserRepository extends JpaRepository<User, Long> {

   UserDetails findByUsername(String name);
}
