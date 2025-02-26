package book.loan.system.repository;

import book.loan.system.domain.APIClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;


public interface APIClientRepository extends JpaRepository<APIClient, Long> {

   @Query("SELECT u FROM APIClient u WHERE LOWER(u.email) = LOWER(:email)")
   UserDetails findByEmailIgnoreCase(String email);
}
