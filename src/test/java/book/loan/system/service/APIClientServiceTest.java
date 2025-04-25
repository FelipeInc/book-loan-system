package book.loan.system.service;

import book.loan.system.DTO.APIClientRegisterRequestDTOCreator;
import book.loan.system.domain.APIClient;
import book.loan.system.exception.BadRequestException;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.util.APIClientCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class APIClientServiceTest {
    @InjectMocks
    private APIClientService apiClientService;

    @Mock
    private APIClientRepository apiClientRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<book.loan.system.domain.APIClient> bookPage = new PageImpl<>(List.of(APIClientCreator.createValidAPIClient()));
        BDDMockito.when(apiClientRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(bookPage);

        BDDMockito.when(apiClientRepositoryMock.save(ArgumentMatchers.any(APIClient.class)))
                .thenReturn(APIClientCreator.createValidAPIClient());

    }

    @Test
    @DisplayName("Save Returns APIClient When Successful")
    void save_ReturnsAPIClient_WhenSuccessful() {
        APIClient apiClient = apiClientService.registerUser(APIClientRegisterRequestDTOCreator.createAPIClientRegisterRequestDTO());

        Assertions.assertThat(apiClient).isNotNull()
                .isEqualTo(APIClientCreator.createValidAPIClient());
    }

    @Test
    @DisplayName("Save throws BadRequestException when APIClient email is already registered")
    void save_ThrowsBadRequestException_WhenAPIClientEmailIsAlreadyRegistered() {
        BDDMockito.when(apiClientRepositoryMock.findByEmailIgnoreCase(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(APIClientCreator.createValidAPIClient()));

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() ->apiClientService.registerUser(APIClientRegisterRequestDTOCreator.createAPIClientRegisterRequestDTO()))
                .withMessageContaining("This email is already registered");
    }

    @Test
    @DisplayName("FindBookByIdOrThrow404 Returns Book When Successful")
    void findBookByIdOrThrow404_ReturnsBook_WhenSuccessful() {
        BDDMockito.when(apiClientRepositoryMock.findByEmailIgnoreCase(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(APIClientCreator.createValidAPIClient()));


        Long expectedId = APIClientCreator.createValidAPIClient().getId();
        String expectedEmail = APIClientCreator.createValidAPIClient().getEmail();
        String expectedName = APIClientCreator.createValidAPIClient().getName();
        String expectedPassword = APIClientCreator.createValidAPIClient().getPassword();
        Collection<? extends GrantedAuthority> expectedAuthorities = APIClientCreator.createValidAPIClient().getAuthorities();

        APIClient apiClient = apiClientService.findUserByEmailOrThrowNotFoundException("email");

        Assertions.assertThat(apiClient).isNotNull();
        Assertions.assertThat(apiClient.getId()).isEqualTo(expectedId);
        Assertions.assertThat(apiClient.getEmail()).isEqualTo(expectedEmail);
        Assertions.assertThat(apiClient.getName()).isEqualTo(expectedName);
        Assertions.assertThat(apiClient.getPassword()).isEqualTo(expectedPassword);
        Assertions.assertThat(apiClient.getAuthorities()).isEqualTo(expectedAuthorities);
    }
}