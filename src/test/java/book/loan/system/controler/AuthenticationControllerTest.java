package book.loan.system.controler;

import book.loan.system.DTO.APIClientLoginRequestDTOCreator;
import book.loan.system.DTO.APIClientRegisterRequestDTOCreator;
import book.loan.system.domain.APIClient;
import book.loan.system.request.APIClientLoginRequestDTO;
import book.loan.system.request.APIClientRegisterRequestDTO;
import book.loan.system.request.LoginResponseDTO;
import book.loan.system.service.APIClientService;
import book.loan.system.service.TokenService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {
    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private APIClientService apiClientServiceMock;
    @Mock
    private TokenService tokenServiceMock;
    @Mock
    private AuthenticationManager managerMock;


    @BeforeEach
    void setUp() {
        BDDMockito.when(apiClientServiceMock.registerUser(ArgumentMatchers.any(APIClientRegisterRequestDTO.class)))
                .thenReturn(APIClientCreator.createValidAPIClient());
        BDDMockito.when(managerMock.authenticate(ArgumentMatchers.any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(APIClientCreator.createValidAPIClient(),
                        null,
                        APIClientCreator.createValidAPIClient().getAuthorities()));
        BDDMockito.when(tokenServiceMock.generateToken(APIClientCreator.createValidAPIClient())).thenReturn("mockToken123");
    }
    @Test
    @DisplayName("Save returns APIClient when successful")
    void save_ReturnsAPIClient_WhenSuccessful() {
        APIClient apiClient = authenticationController.register(
                APIClientRegisterRequestDTOCreator.createAPIClientRegisterRequestDTO()).getBody();

        Assertions.assertThat(apiClient).isNotNull()
                .isEqualTo(APIClientCreator.createValidAPIClient());
    }

    @Test
    @DisplayName("Login return token when successful")
    void login_ShouldReturnToken_WhenSuccessful() {
        APIClientLoginRequestDTO request = APIClientLoginRequestDTOCreator.createAPIClientLoginRequestDTO();
        ResponseEntity<LoginResponseDTO> response = authenticationController.login(request);


        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).token())
                .isEqualTo("mockToken123");
    }
}