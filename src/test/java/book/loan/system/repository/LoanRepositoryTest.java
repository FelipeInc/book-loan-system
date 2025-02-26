package book.loan.system.repository;

import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;
import book.loan.system.domain.APIClient;
import book.loan.system.domain.UserRoles;
import book.loan.system.exception.NotFoundException;
import book.loan.system.util.GetDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import util.GetDateTest;

import java.time.LocalDate;


@DataJpaTest
@DisplayName("Tests For Loan Repository")
class LoanRepositoryTest {
    @Autowired
    APIClientRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LoanRepository loanRepository;

    GetDateTest getDate;


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
    @DisplayName("Saves persist book when Successful")
    void save_PersistBook_WhenSuccessful(){
        Book bookToBeSaved = createBookToBeSaved();
        Book savedBook = this.bookRepository.save(bookToBeSaved);
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(bookToBeSaved.getTitle());
        Assertions.assertThat(savedBook.getAuthor()).isEqualTo(bookToBeSaved.getAuthor());
        Assertions.assertThat(savedBook.getIsbn()).isEqualTo(bookToBeSaved.getIsbn());
    }

    @Test
    @DisplayName("Save Persist User When Successful")
    void save_PersistUser_WhenSuccessful(){
        APIClient userToBeSaved = createUserToBeSaved();
        APIClient savedUser = this.userRepository.save(userToBeSaved);


        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getName()).isEqualTo(userToBeSaved.getName());
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(userToBeSaved.getEmail());
        Assertions.assertThat(savedUser.getUserPassword()).isEqualTo(userToBeSaved.getUserPassword());
        Assertions.assertThat(savedUser.getAuthorities()).isEqualTo(userToBeSaved.getAuthorities());
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