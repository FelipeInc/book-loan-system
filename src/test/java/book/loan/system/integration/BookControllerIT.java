package book.loan.system.integration;

import book.loan.system.domain.Book;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.repository.BookRepository;
import book.loan.system.util.APIClientCreator;
import book.loan.system.util.BookCreator;
import book.loan.system.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class BookControllerIT {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private APIClientRepository apiClientRepository;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplate;


    @TestConfiguration
    @Lazy
    static class config{
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port){
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("felipe20@gmail.com", "123456");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("List Returns List Of Book Inside Page Object When Successful")
    void list_ReturnsListOfBookInsidePageObjects_WhenSuccessful(){
        apiClientRepository.save(APIClientCreator.createUserToBeSaved());

        Book savedBook = bookRepository.save(BookCreator.createBookToBeSaved());

        Long expectedId = savedBook.getId();
        String expectedTitle = savedBook.getTitle();
        String expectedAuthor = savedBook.getAuthor();
        String expectedISBN = savedBook.getIsbn();

        PageableResponse<Book> bookPage = testRestTemplate.exchange("/api/v1/books", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Book>>() {
                }).getBody();

        Assertions.assertThat(bookPage).isNotNull();
        Assertions.assertThat(bookPage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(bookPage.toList().getFirst().getTitle()).isEqualTo(expectedTitle);
        Assertions.assertThat(bookPage.toList().getFirst().getAuthor()).isEqualTo(expectedAuthor);
        Assertions.assertThat(bookPage.toList().getFirst().getId()).isEqualTo(expectedId);
        Assertions.assertThat(bookPage.toList().getFirst().getIsbn()).isEqualTo(expectedISBN);

    }
}
