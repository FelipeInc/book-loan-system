package book.loan.system.repository;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;
import book.loan.system.exception.NotFoundException;
import book.loan.system.util.APIClientCreator;
import book.loan.system.util.BookCreator;
import book.loan.system.util.LocalDateCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@DisplayName("Tests For Loan Repository")
class LoanRepositoryTest {
    @Autowired
    APIClientRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LoanRepository loanRepository;

    @Test
    @DisplayName("Saves Loan When Successful")
    void saves_Loan_When_Successful() {
        Loan loanToBeSaved = createLoanToBeSaved();
        Loan savedLoan = this.loanRepository.save(loanToBeSaved);

        Assertions.assertThat(savedLoan).isNotNull();
        Assertions.assertThat(savedLoan.getId()).isNotNull();
        Assertions.assertThat(savedLoan.getLoanDate()).isAfterOrEqualTo(loanToBeSaved.getLoanDate());
        Assertions.assertThat(savedLoan.getUserEmail()).isEqualTo(loanToBeSaved.getUserEmail());
        Assertions.assertThat(savedLoan.getBookRented()).isEqualTo(loanToBeSaved.getBookRented());
        Assertions.assertThat(savedLoan.getDateToGiveBack()).isEqualTo(loanToBeSaved.getDateToGiveBack());

    }

    @Test
    @DisplayName("Delete removes Loan When Successful")
    void delete_Removes_Loan_When_Successful() {
        Loan loanToBeSaved = createLoanToBeSaved();
        Loan savedLoan = this.loanRepository.save(loanToBeSaved);

        this.loanRepository.delete(savedLoan);

        Optional<Loan> optionalLoan = this.loanRepository.findById(savedLoan.getId());
        Assertions.assertThat(optionalLoan).isEmpty();

    }

    private Loan createLoanToBeSaved() {
        APIClient saveUser = this.userRepository.save(APIClientCreator.createUserToBeSaved());

        Book savedBook = this.bookRepository.save(BookCreator.createBookToBeSaved());

        Loan loan = this.loanRepository.save(Loan.builder()
                .userEmail(saveUser)
                .bookRented(savedBook)
                .loanDate(LocalDateCreator.localDate())
                .dateToGiveBack(LocalDateCreator.localDatePlus30Days())
                .build());

        Book bookToBeLoan = findBookByIdOrThrow404(savedBook.getId());
        bookToBeLoan.setIdLoan(loan);

        loan.setBookRented(bookToBeLoan);
        return loan;
    }



    private Book findBookByIdOrThrow404(Long id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not Found"));
    }
}