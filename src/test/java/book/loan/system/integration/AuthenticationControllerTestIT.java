package book.loan.system.integration;

import book.loan.system.domain.APIClient;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.util.APIClientCreator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Log4j2
public class AuthenticationControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private APIClientRepository apiClientRepository;

    String apiClientRegisterRequestBody = """
        {
            "name": "test",
                    "email": "felipe20@gmail.com",
                    "password": "123456",
                    "authorities": "ADMIN"
        }
        """;

    String apiClientLoginRequestBody = """
        {
            "email": "felipe20@gmail.com",
            "password": "123456"
        }
        """;

    String apiClientLoginRequestBodyNotExistent = """
        {
            "email": "inexistent@gmail.com",
                    "password": "123456"
        }
        """;

    @Test
    @DisplayName("Register returns 201 when successful")
    void register_Returns201_WhenSuccessful() throws Exception {
            mockMvc.perform(post("/api/v1/books/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(apiClientRegisterRequestBody))
                    .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Login returns token when successful")
    void login_ReturnsToken_WhenSuccessful() throws Exception {
            mockMvc.perform(post("/api/v1/books/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(apiClientLoginRequestBody))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("Login returns 403 when user isn't registered")
    void login_Returns403_WhenUserIsnTRegistered() throws Exception {
            mockMvc.perform(post("/api/v1/books/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(apiClientLoginRequestBodyNotExistent))
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.token").doesNotExist());
    }
}
