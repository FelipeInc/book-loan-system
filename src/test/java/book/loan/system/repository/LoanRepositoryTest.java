package book.loan.system.repository;

import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;
import book.loan.system.domain.APIClient;
import book.loan.system.domain.UserRoles;
import book.loan.system.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
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
    void saves_Loan_when_successful() {
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
    void delete_Removes_Loan_When_Successful(){
        Loan loanToBeSaved = createLoanToBeSaved();
        Loan savedLoan = this.loanRepository.save(loanToBeSaved);

        this.loanRepository.delete(savedLoan);

        Optional<Loan> optionalLoan = this.loanRepository.findById(savedLoan.getId());
        Assertions.assertThat(optionalLoan).isEmpty();

    }

    private Loan createLoanToBeSaved(){
        APIClient userToBeSaved = createUserToBeSaved();
        APIClient savedUser = this.userRepository.save(userToBeSaved);

        Book bookToBeSaved = createBookToBeSaved();
        Book savedBook = this.bookRepository.save(bookToBeSaved);


        Loan loan = new Loan();
        loan.setLoanDate(localDate());
        loan.setUserEmail(savedUser);
        loan.setDateToGiveBack(localDatePlus30Days());

        Book bookToBeLoan = findBookByIdOrThrow404(savedBook.getId());
        bookToBeLoan.setIdLoan(loan);

        loan.setBookRented(bookToBeLoan);
        return loan;
    }

    private Book findBookByIdOrThrow404(Long id){
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not Found"));
    }

    private APIClient createUserToBeSaved(){
        return APIClient.builder()
                .name("Felipe Silva")
                .email("Felipe20Silva@gmail.com")
                .userPassword("123456")
                .authorities(UserRoles.ADMIN)
                .build();
    }
    private Book createBookToBeSaved(){
        return Book.builder()
                .author("Antoine de Saint-Exupéry")
                .title("O Pequeno Principe")
                .isbn("9780152048044")
                .build();
    }

    public LocalDate localDate(){
        return LocalDate.now();
    }

    public LocalDate localDatePlus30Days(){
        return LocalDate.now().plusDays(30);

    }
}