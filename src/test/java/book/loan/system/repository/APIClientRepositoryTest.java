package book.loan.system.repository;

import book.loan.system.domain.APIClient;
import book.loan.system.util.APIClientCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("User Repository Test")
class APIClientRepositoryTest {

    @Autowired
    private APIClientRepository apiClientRepository;

    @Test
    @DisplayName("Save Persist User When Successful")
    void save_PersistUser_WhenSuccessful() {
        APIClient userToBeSaved = APIClientCreator.createUserToBeSaved();
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
        APIClient userToBeSaved = APIClientCreator.createUserToBeSaved();
        APIClient savedUser = this.apiClientRepository.save(userToBeSaved);
        this.apiClientRepository.delete(savedUser);
        Optional<APIClient> userOptional = this.apiClientRepository.findById(savedUser.getId());
        Assertions.assertThat(userOptional).isEmpty();
    }

    @Test
    @DisplayName("Save Throw ConstraintViolationException When Name is Empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty() {
        APIClient userWithNameEmpty = APIClientCreator.createUserWithNameEmpty();

        Assertions.assertThat(userWithNameEmpty.getName()).isNull();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiClientRepository.saveAndFlush(userWithNameEmpty))
                .withMessageContaining("This field can't be empty");
    }

    @Test
    @DisplayName("Save Throw ConstraintViolationException When Email is Empty")
    void save_ThrowConstraintViolationException_WhenEmailIsEmpty() {
        APIClient userWithEmailEmpty = APIClientCreator.createUserWithEmailEmpty();

        Assertions.assertThat(userWithEmailEmpty.getEmail()).isEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiClientRepository.saveAndFlush(userWithEmailEmpty))
                .withMessageContaining("Email is required");
    }

    @Test
    @DisplayName("Save Throw ConstraintViolationException When Password is Empty")
    void save_ThrowConstraintViolationException_WhenPasswordIsEmpty() {
        APIClient userWithPasswordEmpty = APIClientCreator.createUserWithPasswordEmpty();

        Assertions.assertThat(userWithPasswordEmpty.getPassword()).isEmpty();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.apiClientRepository.saveAndFlush(userWithPasswordEmpty))
                .withMessageContaining("This field can't be empty");
    }

}
