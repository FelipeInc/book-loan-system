package book.loan.system.DTO;

import book.loan.system.request.LoanDeleteRequestDTO;
import book.loan.system.util.BookCreator;
import book.loan.system.util.LoanCreator;

public class LoanDeleteRequestDTOCreator {
    public static LoanDeleteRequestDTO createLoanDeleteRequestDTO(){
        return LoanDeleteRequestDTO.builder()
                .bookTitle(BookCreator.createBookRented().getTitle())
                .idLoan(LoanCreator.createValidLoan().getId())
                .build();

    }
}
