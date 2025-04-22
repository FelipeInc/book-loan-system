package book.loan.system.service;

import book.loan.system.exception.ForbiddenException;
import book.loan.system.util.APIClientCreator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
@Log4j2
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secret", "${api.security.token.secret}");
    }

    @Test
    @DisplayName("generateToken generate a token when successful")
    void generateToken_GenerateAToken_WhenSuccessful() {
        String generatedToken = tokenService.generateToken(APIClientCreator.createValidAPIClient());

        DecodedJWT decodedJWT = JWT.decode(generatedToken);

        Assertions.assertThat(generatedToken)
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(decodedJWT.getIssuer()).isEqualTo("auth-api");
        Assertions.assertThat(decodedJWT.getSubject()).isEqualTo(APIClientCreator.createValidAPIClient().getEmail());
        Assertions.assertThat(generatedToken.split("\\.")).hasSize(3);

        Assertions.assertThatCode(() -> tokenService.generateToken(APIClientCreator.createValidAPIClient()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("generateToken should throw exception when secret is invalid")
    void generateToken_ShouldThrowException_WhenSecretInvalid() {
        ReflectionTestUtils.setField(tokenService, "secret", null);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> tokenService.generateToken(APIClientCreator.createValidAPIClient()))
                .withMessageContaining("The Secret cannot be null");
    }

    @Test
    @DisplayName("validateToken check if a token is valid when successful")
    void validateToken_CheckIfATokenIsValid_WhenSuccessful() {
        String generatedToken = tokenService.generateToken(APIClientCreator.createValidAPIClient());
        String validatedToken = tokenService.validateToken(generatedToken);

        Assertions.assertThatCode(() -> tokenService.validateToken(generatedToken))
                .doesNotThrowAnyException();
        Assertions.assertThat(validatedToken).isEqualTo(APIClientCreator.createValidAPIClient().getEmail());
    }



}