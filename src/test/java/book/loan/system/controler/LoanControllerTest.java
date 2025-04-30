package book.loan.system.controler;

import book.loan.system.DTO.LoanDeleteRequestDTOCreator;
import book.loan.system.DTO.LoanPostRequestDTOCreator;
import book.loan.system.domain.APIClient;
import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;
import book.loan.system.request.LoanDeleteRequestDTO;
import book.loan.system.request.LoanPostRequestDTO;
import book.loan.system.service.LoanService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Loan Controller Test")
public class LoanControllerTest {
    @InjectMocks
    private LoanController loanController;

    @Mock
    private LoanService loanServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Loan> loanPage = new PageImpl<>(List.of(LoanCreator.createValidLoan()));
        BDDMockito.when(loanServiceMock.findAllLoans(ArgumentMatchers.any()))
                .thenReturn(loanPage);

        BDDMockito.when(loanServiceMock.findLoanByIDorThrows404(ArgumentMatchers.anyLong()))
                .thenReturn(LoanCreator.createValidLoan());

        BDDMockito.when(loanServiceMock.createLoan(ArgumentMatchers.any(LoanPostRequestDTO.class)))
                .thenReturn(LoanCreator.createValidLoan());

        BDDMockito.doNothing().when(loanServiceMock).deleteLoan(ArgumentMatchers.any(LoanDeleteRequestDTO.class));
    }

    @Test
    @DisplayName("findAllLoans return a list of Loans inside a PageObject when successful")
    void findAllLoans_ReturnListOfLoansInsidePageObject_WhenSuccessful(){
        Long expectedId = LoanCreator.createValidLoan().getId();
        LocalDate expectedLoanDate = LoanCreator.createValidLoan().getLoanDate();
        LocalDate expectedReturnBookDate = LoanCreator.createValidLoan().getDateToGiveBack();
        Book expectedBook = LoanCreator.createValidLoan().getBookRented();
        APIClient expectedAPIClient = LoanCreator.createValidLoan().getUserEmail();

        Page<Loan> loanPage = loanController.findAllLoans(null).getBody();

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
    @DisplayName("findLoanByIDorThrows404 return a Loans when successful")
    void findLoanByIDorThrows404_ReturnLoansWhenSuccessful(){
        Long expectedId = LoanCreator.createValidLoan().getId();
        LocalDate expectedLoanDate = LoanCreator.createValidLoan().getLoanDate();
        LocalDate expectedReturnBookDate = LoanCreator.createValidLoan().getDateToGiveBack();
        Book expectedBook = LoanCreator.createValidLoan().getBookRented();
        APIClient expectedAPIClient = LoanCreator.createValidLoan().getUserEmail();

        Loan loan = loanController.findLoanById(1L).getBody();

        Assertions.assertThat(loan).isNotNull();
        Assertions.assertThat(loan.getId()).isEqualTo(expectedId);
        Assertions.assertThat(loan.getLoanDate()).isEqualTo(expectedLoanDate);
        Assertions.assertThat(loan.getDateToGiveBack()).isEqualTo(expectedReturnBookDate);
        Assertions.assertThat(loan.getBookRented()).isEqualTo(expectedBook);
        Assertions.assertThat(loan.getUserEmail()).isEqualTo(expectedAPIClient);
    }

    @Test
    @DisplayName("Save Returns Book When Successful")
    void save_ReturnsBook_WhenSuccessful(){
        Loan loan = loanController.rent(LoanPostRequestDTOCreator.createLoanRequestDTO()).getBody();

        Assertions.assertThat(loan).isNotNull()
                .isEqualTo(LoanCreator.createValidLoan());

    }

    @Test
    @DisplayName("DeleteBook Delete Book When Successful")
    void deleteBook_UpdateBook_WhenSuccessful(){
        Assertions.assertThatCode(() -> loanController.deleteLoan(LoanDeleteRequestDTOCreator.createLoanDeleteRequestDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<Loan> entity = loanController.deleteLoan(LoanDeleteRequestDTOCreator.createLoanDeleteRequestDTO());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }


}
