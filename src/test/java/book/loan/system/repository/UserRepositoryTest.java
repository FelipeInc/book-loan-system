package book.loan.system.repository;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.UserRoles;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("User Repository Test")
@Log4j2
class UserRepositoryTest {
    @Autowired
    private APIClientRepository userRepository;

    @Test
    @DisplayName("Save Persist User When Successful")
    void save_PersistUser_WhenSuccessful(){
        APIClient userToBeSaved = createUserToBeSaved();
        APIClient savedUser = this.userRepository.save(userToBeSaved);
        log.info(savedUser);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getName()).isEqualTo(userToBeSaved.getName());
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(userToBeSaved.getEmail());
        Assertions.assertThat(savedUser.getUserPassword()).isEqualTo(userToBeSaved.getUserPassword());
        Assertions.assertThat(savedUser.getAuthorities()).isEqualTo(userToBeSaved.getAuthorities());
    }

    @Test
    @DisplayName("Delete removes user when Successful")
    void delete_RemovesUser_WhenSuccessful(){
        APIClient userToBeSaved = createUserToBeSaved();
        APIClient savedUser = this.userRepository.save(userToBeSaved);

        this.userRepository.delete(savedUser);
        Optional<APIClient> userOptional = this.userRepository.findById(savedUser.getId());
        Assertions.assertThat(userOptional).isEmpty();
    }


    private APIClient createUserToBeSaved(){
        return APIClient.builder()
                .name("Felipe Silva")
                .email("Felipe20Silva@gmail.com")
                .userPassword("123456")
                .authorities(UserRoles.ADMIN)
                .build();
    }
}