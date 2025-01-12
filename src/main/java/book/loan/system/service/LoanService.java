package book.loan.system.service;

import book.loan.system.domain.Loan;
import book.loan.system.repository.BookRepository;
import book.loan.system.repository.LoanRepository;
import book.loan.system.request.LoanPostReqestDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {
    BookService bookService;

    public Loan save(LoanPostReqestDTO loanPostReqestDTO){
        return Loan.builder()
                .loanDate(localDate())
                .bookRented(bookService.findBookByIdOrThrow404(loanPostReqestDTO.id()))
                //.userEmail(loanPostReqestDTO.email())
                .dateToGiveBack(dateToGiveBack())
                .build();
    }

    public LocalDate localDate(){
        return LocalDate.now();
    }

    public LocalDate dateToGiveBack(){
        return LocalDate.now().plusDays(30);
    }
}
