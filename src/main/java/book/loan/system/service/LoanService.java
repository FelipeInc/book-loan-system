package book.loan.system.service;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;
import book.loan.system.exception.NotFoundException;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.repository.LoanRepository;
import book.loan.system.request.LoanDeletePostDTO;
import book.loan.system.request.LoanPostRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {
    LoanRepository loanRepository;
    private final APIClientRepository userRepository;
    private final BookService bookService;

    public LoanService(APIClientRepository userRepository, BookService bookService, LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    public Loan findLoanByIDorThrows404(Long id){
        return loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loan not Found"));
    }

    @Transactional
    public Loan createLoan(LoanPostRequestDTO loanPostRequestDTO){
        Loan loan = new Loan();
        loan.setLoanDate(localDate());
        loan.setUserEmail((APIClient) userRepository.findByEmailIgnoreCase(loanPostRequestDTO.email()));
        loan.setDateToGiveBack(localDatePlus30Days());

        Book book = bookService.findBookByIdOrThrow404(loanPostRequestDTO.id());
        book.setIdLoan(loan);

        loan.setBookRented(book);
        return loanRepository.save(loan);
    }

    @Transactional
    public void deleteLoan(LoanDeletePostDTO loanDeletePostDTO){
        Loan loan = findLoanByIDorThrows404(loanDeletePostDTO.id());
        loan.setBookRented(null);
        Book book = bookService.findBookByIdOrThrow404(loanDeletePostDTO.idBook());
        book.setIdLoan(null);
        loanRepository.delete(loan);
    }

    public LocalDate localDate(){
        return LocalDate.now();
    }

    public LocalDate localDatePlus30Days(){
        return LocalDate.now().plusDays(30);

    }
}
