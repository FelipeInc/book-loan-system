package book.loan.system.integration;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.Book;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.repository.BookRepository;
import book.loan.system.util.APIClientCreator;
import book.loan.system.util.BookCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class BookControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private APIClientRepository apiClientRepository;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();
        apiClientRepository.deleteAll();

        APIClient testUser = APIClientCreator.createValidAPIClient();
        apiClientRepository.save(testUser);
    }

    @Test
    @DisplayName("List returns list of book inside page object when successful")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456")
    void list_ReturnsListOfBookInsidePageObjects_WhenSuccessful() throws Exception{
        Book validBook = BookCreator.createBookToBeSaved();
        bookRepository.save(validBook);


        mockMvc.perform(get("/api/v1/books")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value(BookCreator.createValidBook().getTitle()))
                .andExpect(jsonPath("$.content[0].author").value(BookCreator.createValidBook().getAuthor()))
                .andExpect(jsonPath("$.content[0].isbn").value(BookCreator.createValidBook().getIsbn()));
    }

    @Test
    @DisplayName("List returns empty list when successful")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456")
    void list_ReturnsEmptyList_WhenSuccessful() throws Exception{
        Book validBook = BookCreator.createBookToBeSaved();
        bookRepository.save(validBook);


        mockMvc.perform(get("/api/v1/books")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}