package book.loan.system.integration;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.Book;
import book.loan.system.domain.UserRoles;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.repository.BookRepository;
import book.loan.system.util.BookCreator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Log4j2
public class BookControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private APIClientRepository apiClientRepository;

    @BeforeEach
    void setUp(){

    }

    String bookPostRequestBody = """
        {
            "title": "testPostRequestBodyTitle",
                    "author": "testPostRequestBodyAuthor",
                    "isbn": "1111111111111"
        }
        """;

    String bookPostRequestBodyWithISBNLengthInvalid = """
        {
            "title": "testPostRequestBodyTitleISBNLengthInvalid",
                    "author": "testPostRequestBodyAuthorISBNLengthInvalid",
                    "isbn": "1111"
        }
        """;

    String bookPostRequestBodyWithISBNInvalid = """
        {
            "title": "testPostRequestBodyTitleISBNInvalid",
                    "author": "testPostRequestBodyAuthorISBNInvalid",
                    "isbn": "11111111111@d"
        }
        """;

    String bookPostRequestBodyWithTitleEmpty = """
        {
            "title": "",
                    "author": "testPostRequestBodyAuthorTitleEmpty",
                    "isbn": "9780152048044"
        }
        """;

    String bookPostRequestBodyWithAuthorsNameEmpty = """
        {
            "title": "testPostRequestBodyTitleAuthorEmpty",
                    "author": "",
                    "isbn": "9780152048044"
        }
        """;

    String bookPostRequestBodyWithISBNEmpty = """
        {
            "title": "testPostRequestBodyTitleISBNEmpty",
                    "author": "testPostRequestBodyAuthorISBNEmpty",
                    "isbn": ""
        }
        """;

    String bookPutRequestBody = """
        {
            "id": "1",
            "title": "testPostRequestBodyTitle",
                    "author": "testPostRequestBodyAuthor",
                    "isbn": "1111111111114"
        }
        """;

    @Test
    @DisplayName("updateBook updates book when successful")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void updateBook_UpdateBook_WhenSuccessful() throws Exception{
        Book updateBookTestIT = bookRepository.save(Book.builder()
                .title("updateBookToBeUpdatedTitleTestIT")
                .author("updateBookToBeUpdatedAuthorTestIT")
                .isbn("1111111111111")
                .build());

        String bookPutRequestBody = String.format("""
        {
            "id": "%d",
            "title": "updateUpdatedBookTitle",
            "author": "updateUpdatedBookAuthor",
            "isbn": "1111111111116"
        }
        """, updateBookTestIT.getId());

        mockMvc.perform(put("/api/v1/books/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookPutRequestBody))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("List returns list of book inside page object when successful")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456")
    void list_ReturnsListOfBookInsidePageObjects_WhenSuccessful() throws Exception{
        bookRepository.deleteAll();
        Book savedBookTestIT = bookRepository.save(Book.builder()
                .title("saveBookTitleTestIT")
                .author("saveBookAuthorTestIT")
                .isbn("1111111111112")
                .build());
        bookRepository.save(savedBookTestIT);


        mockMvc.perform(get("/api/v1/books")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value(savedBookTestIT.getTitle()))
                .andExpect(jsonPath("$.content[0].author").value(savedBookTestIT.getAuthor()))
                .andExpect(jsonPath("$.content[0].isbn").value(savedBookTestIT.getIsbn()));
    }

    @Test
    @DisplayName("List returns empty list when successful")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456")
    void list_ReturnsEmptyList_WhenSuccessful() throws Exception{
        bookRepository.deleteAll();
        mockMvc.perform(get("/api/v1/books")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    @Test
    @DisplayName("List throws 403 when client is not logged")
    void list_Throw403_WhenClientIsNotLogged() throws Exception{
        mockMvc.perform(get("/api/v1/books")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("FindBookByIdOrThrow404 returns book when successful")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456")
    void findBookByIdOrThrow404_ReturnsBook_WhenSuccessful() throws Exception{
        Book bookToBeSavedTestFindByID = bookRepository.save(Book.builder()
                .title("findByIdBookTitleTestIT")
                .author("findByIdBookAuthorTestIT")
                .isbn("1111111111113")
                .build());
        Book savedBookTestIT = bookRepository.save(bookToBeSavedTestFindByID);

        mockMvc.perform(get("/api/v1/books/{id}", savedBookTestIT.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.title").value(bookToBeSavedTestFindByID.getTitle()))
                .andExpect(jsonPath("$.author").value(bookToBeSavedTestFindByID.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(bookToBeSavedTestFindByID.getIsbn()));
    }

    @Test
    @DisplayName("FindBookByIdOrThrow404 throw 404 when book is not found")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456")
    void findBookByIdOrThrow404_Throw404_WhenBookIsNotFound() throws Exception{
        mockMvc.perform(get("/api/v1/books/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("FindBookByIdOrThrow404 throw 403 when client is not logged")
    void findBookByIdOrThrow404_Throw403_WhenClientIsNotLogged() throws Exception{
        mockMvc.perform(get("/api/v1/books/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Save returns book when successful")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void save_ReturnsBook_WhenSuccessful() throws Exception{
       mockMvc.perform(post("/api/v1/books/save")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(bookPostRequestBody))
               .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Save throw BadRequestException when title is empty")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void save_ThrowBadRequestException_WhenTitleIsEmpty() throws Exception{
       mockMvc.perform(post("/api/v1/books/save")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(bookPostRequestBodyWithTitleEmpty))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Save throw BadRequestException when author's name is empty")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void save_ThrowBadRequestException_WhenAuthorsNameIsEmpty() throws Exception{
       mockMvc.perform(post("/api/v1/books/save")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(bookPostRequestBodyWithAuthorsNameEmpty))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Save throw BadRequestException when ISBN is empty")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void save_ThrowBadRequestException_WhenTISBNIsEmpty() throws Exception{
       mockMvc.perform(post("/api/v1/books/save")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(bookPostRequestBodyWithISBNEmpty))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Save throw BadRequestException when ISBN is invalid")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void save_ThrowBadRequestException_WhenTISBNIsInvalid() throws Exception{
       mockMvc.perform(post("/api/v1/books/save")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(bookPostRequestBodyWithISBNInvalid))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Save throw BadRequestException when ISBN length is invalid")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void save_ThrowBadRequestException_WhenTISBNLengthIsInvalid() throws Exception{
       mockMvc.perform(post("/api/v1/books/save")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(bookPostRequestBodyWithISBNLengthInvalid))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Save throw 403 when client is not logged")
    void save_ReturnsBook_WhenClientIsNotLogged() throws Exception{
       mockMvc.perform(post("/api/v1/books/save")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(bookPostRequestBody))
               .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("updateBook throw 404 when book is not found")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void updateBook_Throw404_WhenBookIsNotFound() throws Exception{
        mockMvc.perform(put("/api/v1/books/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookPutRequestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updateBook throw 403 book when client is not logged")
    void updateBook_Throw403_WhenClientIsNotLogged() throws Exception{

        mockMvc.perform(put("/api/v1/books/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookPutRequestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("deleteBook deletes book when successful")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void deleteBook_DeletesBook_WhenSuccessful() throws Exception{
        Book deleteBookSavedBookTestIt = bookRepository.save(Book.builder()
                .title("updateBookToBeUpdatedTitleTestIT")
                .author("updateBookToBeUpdatedAuthorTestIT")
                .isbn("1111111111119")
                .build());

        mockMvc.perform(delete("/api/v1/books/delete/{id}", deleteBookSavedBookTestIt.getId()))
                .andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("deleteBook throw 403 when book is not found")
    @WithMockUser(username = "Felipe20@gmail.com", password = "123456", roles = "ADMIN")
    void deleteBook_Throw404_WhenBookIsNotFound() throws Exception{
        mockMvc.perform(delete("/api/v1/books/delete/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("deleteBook throw 403 when client is not logged")
    void deleteBook_Throw403_WhenSuccessful() throws Exception{

        mockMvc.perform(delete("/api/v1/books/delete/{id}", 1))
                .andExpect(status().isForbidden());
    }


}