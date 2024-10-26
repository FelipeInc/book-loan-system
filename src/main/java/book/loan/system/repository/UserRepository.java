package book.loan.system.repository;

import book.loan.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

   @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
   UserDetails findByEmailIgnoreCase(String email);
}
