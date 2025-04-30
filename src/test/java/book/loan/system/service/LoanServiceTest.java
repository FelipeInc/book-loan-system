package book.loan.system.service;

import book.loan.system.DTO.LoanDeleteRequestDTOCreator;
import book.loan.system.DTO.LoanPostRequestDTOCreator;
import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;
import book.loan.system.exception.NotFoundException;
import book.loan.system.repository.LoanRepository;
import book.loan.system.util.APIClientCreator;
import book.loan.system.util.BookCreator;
import book.loan.system.util.LoanCreator;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Loan Service Test")
class LoanServiceTest {
    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepositoryMock;
    @Mock
    private APIClientService apiClientService;
    @Mock
    private BookService bookServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Loan> loanPage = new PageImpl<>(List.of(LoanCreator.createValidLoan()));
        BDDMockito.when(loanRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(loanPage);

        BDDMockito.when(loanRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(LoanCreator.createValidLoan()));

        BDDMockito.when(loanRepositoryMock.save(ArgumentMatchers.any(Loan.class)))
                .thenReturn(LoanCreator.createValidLoan());

        BDDMockito.when(bookServiceMock.findBookByIdOrThrow404(ArgumentMatchers.anyLong()))
                        .thenReturn(BookCreator.createValidBook());

        BDDMockito.when(apiClientService.findUserByEmailOrThrowNotFoundException(ArgumentMatchers.anyString()))
                        .thenReturn(APIClientCreator.createValidAPIClient());

        BDDMockito.doNothing().when(bookServiceMock).returnABook(ArgumentMatchers.anyString());

        BDDMockito.doNothing().when(bookServiceMock).rentABook(ArgumentMatchers.anyString(),ArgumentMatchers.any(Loan.class));

        BDDMockito.doNothing().when(loanRepositoryMock).delete(ArgumentMatchers.any(Loan.class));

    }

    @Test
    @DisplayName("findAllLoans return a list of Loans inside a PageObject when successful")
    void findAllLoans_ReturnListOfLoansInsidePageObject_WhenSuccessful(){
        Long expectedId = LoanCreator.createValidLoan().getId();
        LocalDate expectedLoanDate = LoanCreator.createValidLoan().getLoanDate();
        LocalDate expectedReturnBookDate = LoanCreator.createValidLoan().getDateToGiveBack();
        Book expectedBook = LoanCreator.createValidLoan().getBookRented();
        book.loan.system.domain.APIClient expectedAPIClient = LoanCreator.createValidLoan().getUserEmail();

        Page<Loan> loanPage = loanService.findAllLoans(PageRequest.of(1,1));

        Assertions.assertThat(loanPage).isNotNull();
        Assertions.assertThat(loanPage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(loanPage.toList().getFirst().getId()).isEqualTo(expectedId);
        Assertions.assertThat(loanPage.toList().getFirst().getLoanDate()).isEqualTo(expectedLoanDate);
        Assertions.assertThat(loanPage.toList().getFirst().getDateToGiveBack()).isEqualTo(expectedReturnBookDate);
        Assertions.assertThat(loanPage.toList().getFirst().getBookRented()).isEqualTo(expectedBook);
        Assertions.assertThat(loanPage.toList().getFirst().getUserEmail()).isEqualTo(expectedAPIClient);

    }
    @Test
    @DisplayName("findLoanByIDorThrows404 return a Loan when successful")
    void findLoanByIDorThrows404_ReturnALoanWhenSuccessful(){
        Long expectedId = LoanCreator.createValidLoan().getId();
        LocalDate expectedLoanDate = LoanCreator.createValidLoan().getLoanDate();
        LocalDate expectedReturnBookDate = LoanCreator.createValidLoan().getDateToGiveBack();
        Book expectedBook = LoanCreator.createValidLoan().getBookRented();
        book.loan.system.domain.APIClient expectedAPIClient = LoanCreator.createValidLoan().getUserEmail();

        Loan loan = loanService.findLoanByIDorThrows404(1L);

        Assertions.assertThat(loan).isNotNull();
        Assertions.assertThat(loan.getId()).isEqualTo(expectedId);
        Assertions.assertThat(loan.getLoanDate()).isEqualTo(expectedLoanDate);
        Assertions.assertThat(loan.getDateToGiveBack()).isEqualTo(expectedReturnBookDate);
        Assertions.assertThat(loan.getBookRented()).isEqualTo(expectedBook);
        Assertions.assertThat(loan.getUserEmail()).isEqualTo(expectedAPIClient);
    }

    @Test
    @DisplayName("FindBookByIdOrThrow404 throw404 When book is not found")
    void findBookByIdOrThrow404_Throws404_WhenLoanIsNotFound() {
        BDDMockito.when(loanRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(()->loanService.findLoanByIDorThrows404(1L))
                .withMessageContaining("Loan not Found");
    }

    @Test
    @DisplayName("createLoan creates a loan when successful")
    void createLoan_CreatesALoans_WhenSuccessful(){
        Loan loan = loanService.createLoan(LoanPostRequestDTOCreator.createLoanRequestDTO());
        Loan expectedLoan = LoanCreator.createValidLoan();

        Assertions.assertThat(loan).isNotNull();
        Assertions.assertThat(loan.getId()).isEqualTo(expectedLoan.getId());
        Assertions.assertThat(loan.getUserEmail()).isEqualTo(expectedLoan.getUserEmail());
        Assertions.assertThat(loan.getLoanDate()).isEqualTo(expectedLoan.getLoanDate());
        Assertions.assertThat(loan.getDateToGiveBack()).isEqualTo(expectedLoan.getDateToGiveBack());
        Assertions.assertThat(loan.getBookRented()).isEqualTo(expectedLoan.getBookRented());

        Assertions.assertThatCode(()-> loanService.createLoan(LoanPostRequestDTOCreator.createLoanRequestDTO()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("deleteLoan delete a loan when successful")
    void deleteLoan_DeleteALoans_WhenSuccessful(){
        Assertions.assertThatCode(() ->loanService.deleteLoan(LoanDeleteRequestDTOCreator.createLoanDeleteRequestDTO()))
                .doesNotThrowAnyException();
    }
}