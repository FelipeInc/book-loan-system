package book.loan.system.repository;

import book.loan.system.domain.APIClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated
public interface APIClientRepository extends JpaRepository<APIClient, Long> {

   @Query("SELECT u FROM APIClient u WHERE LOWER(u.email) = LOWER(:email)")
   Optional<APIClient> findByEmailIgnoreCase(String email);

   @Query("SELECT u FROM APIClient u WHERE LOWER(u.email) = LOWER(:email)")
   UserDetails findByEmailUserDetailsIgnoreCase(String email);
}
