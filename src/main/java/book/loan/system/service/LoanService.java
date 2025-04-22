package book.loan.system.service;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;
import book.loan.system.exception.NotFoundException;
import book.loan.system.repository.LoanRepository;
import book.loan.system.request.LoanDeleteRequestDTO;
import book.loan.system.request.LoanPostRequestDTO;
import book.loan.system.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
@RequiredArgsConstructor
@Service
public class LoanService {
    private final LoanRepository loanRepository;

    private final APIClientService apiClientService;
    private final BookService bookService;

    public Page<Loan> findAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable);
    }

    public Loan findLoanByIDorThrows404(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loan not Found"));
    }

    @Transactional
    public Loan createLoan(LoanPostRequestDTO loanPostRequestDTO) {
        APIClient clientFound = apiClientService.findUserByIdOrThrowNotFoundException(loanPostRequestDTO.email());

        Book book = bookService.findBookByIdOrThrow404(loanPostRequestDTO.id());

        Loan loan = Loan.builder()
                .userEmail(clientFound)
                .loanDate(DateUtil.localDate())
                .dateToGiveBack(DateUtil.localDatePlus30Days())
                .bookRented(book)
                .build();
        Loan loanSaved = loanRepository.save(loan);

        bookService.rentABook(loanPostRequestDTO.id(), loan);

        return loanSaved;
    }

    @Transactional
    public void deleteLoan(LoanDeleteRequestDTO loanDeletePostDTO) {
        bookService.returnABook(loanDeletePostDTO.idBook());

        Loan loan = findLoanByIDorThrows404(loanDeletePostDTO.idLoan());
        loan.setBookRented(null);

        loanRepository.delete(loan);
    }


}
