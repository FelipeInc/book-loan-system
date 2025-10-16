package book.loan.system.integration;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.repository.BookRepository;
import book.loan.system.repository.LoanRepository;
import book.loan.system.request.LoanPostRequestDTO;
import book.loan.system.service.LoanService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class LoanControllerIT {
    @Autowired
    private  MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private APIClientRepository apiClientRepository;

    @Autowired
    private  LoanRepository loanRepository;

    @Autowired
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        apiClientRepository.deleteAll();
    }

    String loanPostRequestDTO = """
        {
            "bookTitle": "rentBookTitleTestIT",
            "email": "testForLoanControllerIT@gmail.com"
        }
        """;




    @Test
    @DisplayName("findAllLoans return a list of Loans inside a PageObject when successful")
    @WithMockUser(username = "testForLoanControllerIT@gmail.com", password = "123456")
    void findAllLoans_ReturnListOfLoansInsidePageObject_WhenSuccessful() throws Exception {
        Book testBook = Book.builder()
                .title("findAllLoansBookTitleTestIT")
                .author("findAllLoansBookTAuthorTestIT")
                .isbn("1111111111117")
                .build();
        bookRepository.save(testBook);

        APIClient testUser = APIClientCreator.createUserToBeSavedLoanControllerTestIT();
        apiClientRepository.save(testUser);

        Loan loan = loanService.createLoan(LoanPostRequestDTO.builder()
                .email(testUser.getEmail())
                .bookTitle(testBook.getTitle())
                .build());
        mockMvc.perform(get("/api/v1/books/loan/find")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].loanDate").value(loan.getLoanDate().toString()))
                .andExpect(jsonPath("$.content[0].bookRented.title").value(loan.getBookRented().getTitle()))
                .andExpect(jsonPath("$.content[0].bookRented.author").value(loan.getBookRented().getAuthor()))
                .andExpect(jsonPath("$.content[0].bookRented.isbn").value(loan.getBookRented().getIsbn()))
                .andExpect(jsonPath("$.content[0].userEmail.name").value(loan.getUserEmail().getName()))
                .andExpect(jsonPath("$.content[0].userEmail.email").value(loan.getUserEmail().getEmail()));
    }
    @Test
    @DisplayName("findLoanByID return a Loans when successful")
    @WithMockUser(username = "testForLoanControllerIT@gmail.com", password = "123456")
    void findLoanByID_ReturnLoansWhenSuccessful() throws Exception{
        Book testBook = Book.builder()
                .title("findLoanByIdBookTitleTestIT")
                .author("findLoanByIdBookTAuthorTestIT")
                .isbn("1111111111115")
                .build();
        bookRepository.save(testBook);

        APIClient testUser = APIClientCreator.createUserToBeSavedLoanControllerTestIT();
        apiClientRepository.save(testUser);

        Loan loan = loanService.createLoan(LoanPostRequestDTO.builder()
                .email(testUser.getEmail())
                .bookTitle(testBook.getTitle())
                .build());
        mockMvc.perform(get("/api/v1/books/loan/{id}", loan.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanDate").value(loan.getLoanDate().toString()))
                .andExpect(jsonPath("$.bookRented.title").value(loan.getBookRented().getTitle()))
                .andExpect(jsonPath("$.bookRented.author").value(loan.getBookRented().getAuthor()))
                .andExpect(jsonPath("$.bookRented.isbn").value(loan.getBookRented().getIsbn()))
                .andExpect(jsonPath("$.userEmail.name").value(loan.getUserEmail().getName()))
                .andExpect(jsonPath("$.userEmail.email").value(loan.getUserEmail().getEmail()));
    }

    @Test
    @DisplayName("rent return status created when successful")
    @WithMockUser(username = "testForLoanControllerIT@gmail.com", password = "123456")
    void rent_ReturnsStatusCreated_WhenSuccessful() throws Exception{
        Book book = Book.builder()
                .title("rentBookTitleTestIT")
                .author("rentBookTAuthorTestIT")
                .isbn("1111111111118")
                .build();
        Book saved = bookRepository.save(book);

        APIClient testUser = APIClientCreator.createUserToBeSavedLoanControllerTestIT();
        apiClientRepository.save(testUser);

        mockMvc.perform(post("/api/v1/books/loan/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loanPostRequestDTO))
                .andExpect(status().isCreated());

        bookRepository.delete(saved);
    }
    @Test
    @DisplayName("DeleteBook Delete Book When Successful")
    @WithMockUser(username = "testForLoanControllerIT@gmail.com", password = "123456")
    void deleteBook_UpdateBook_WhenSuccessful() throws Exception{


        Book testBook = BookCreator.createBookToBeSaved();
        bookRepository.save(testBook);

        APIClient testUser = APIClientCreator.createUserToBeSavedLoanControllerTestIT();
        apiClientRepository.save(testUser);

        loanService.createLoan(LoanPostRequestDTO.builder()
                .email(testUser.getEmail())
                .bookTitle(testBook.getTitle())
                .build());

        String loanDeleteRequestDTO = """
        {
            "idLoan": "1",
            "bookTitle": "titleBookTest"
        }
        """;

        mockMvc.perform(delete("/api/v1/books/loan/return/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loanDeleteRequestDTO))
                .andExpect(status().isNoContent());
    }
}

