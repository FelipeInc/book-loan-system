package book.loan.system.repository;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.UserRoles;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

@DataJpaTest
@DisplayName("User Repository Test")
@Log4j2
class APIClientRepositoryTest {

    @Autowired
    private APIClientRepository apiClientRepository;

    @Test
    @DisplayName("Save Persist User When Successful")
    void save_PersistUser_WhenSuccessful() {
        APIClient userToBeSaved = createUserToBeSaved();
        APIClient savedUser = this.apiClientRepository.save(userToBeSaved);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getName()).isEqualTo(userToBeSaved.getName());
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(userToBeSaved.getEmail());
        Assertions.assertThat(savedUser.getUserPassword()).isEqualTo(userToBeSaved.getUserPassword());
        Assertions.assertThat(savedUser.getAuthorities()).isEqualTo(userToBeSaved.getAuthorities());
    }

    @Test
    @DisplayName("Delete removes user when Successful")
    void delete_RemovesUser_WhenSuccessful() {
        APIClient userToBeSaved = createUserToBeSaved();
        APIClient savedUser = this.apiClientRepository.save(userToBeSaved);
        this.apiClientRepository.delete(savedUser);
        Optional<APIClient> userOptional = this.apiClientRepository.findById(savedUser.getId());
        Assertions.assertThat(userOptional).isEmpty();
    }

    @Test
    @DisplayName("Save Throw ConstraintViolationException When Name is Empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty() {
        APIClient userWithNameEmpty = createUserWithNameEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiClientRepository.save(userWithNameEmpty))
                .withMessageContaining("This Field can't be empty");
    }

    @Test
    @DisplayName("Save Throw ConstraintViolationException When Email is Empty")
    void save_ThrowConstraintViolationException_WhenEmailIsEmpty() {
        APIClient userWithEmailEmpty = createUserWithEmailEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiClientRepository.save(userWithEmailEmpty))
                .withMessageContaining("This Field can't be empty");
    }

    @Test
    @DisplayName("Save Throw ConstraintViolationException When Password is Empty")
    void save_ThrowConstraintViolationException_WhenPasswordIsEmpty() {
        APIClient userWithPasswordEmpty = createUserWithPasswordEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiClientRepository.save(userWithPasswordEmpty))
                .withMessageContaining("This Field can't be empty");
    }

    private APIClient createUserToBeSaved() {
        return APIClient.builder()
                .name("Felipe Silva")
                .email("Felipe20Silva@gmail.com")
                .userPassword("123456")
                .authorities(UserRoles.ADMIN)
                .build();
    }

    private APIClient createUserWithPasswordEmpty() {
        return APIClient.builder()
                .name("Felipe Silva")
                .email("Felipe20Silva@gmail.com")
                .userPassword("")
                .authorities(UserRoles.ADMIN)
                .build();
    }

    private APIClient createUserWithEmailEmpty() {
        return APIClient.builder()
                .name("Felipe Silva")
                .email("")
                .userPassword("123456")
                .authorities(UserRoles.ADMIN)
                .build();
    }

    private APIClient createUserWithNameEmpty() {
        return APIClient.builder()
                .name("")
                .email("Felipe20Silva@gmail.com")
                .userPassword("123456")
                .authorities(UserRoles.ADMIN)
                .build();
    }
}
