package book.loan.system.util;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.Book;
import book.loan.system.domain.Loan;

public class LoanCreator {

    public static Loan createLoanToBeSaved() {
        APIClient apiClientValid = APIClientCreator.createValidAPIClient();

        Book bookRented = BookCreator.createBookRented();

        return Loan.builder()
                .userEmail(apiClientValid)
                .bookRented(bookRented)
                .loanDate(LocalDateCreator.localDate())
                .dateToGiveBack(LocalDateCreator.localDatePlus30Days())
                .build();
    }
    public static Loan createLoanToBeRented() {
        APIClient apiClientValid = APIClientCreator.createValidAPIClient();

        return Loan.builder()
                .id(1L)
                .userEmail(apiClientValid)
                .loanDate(LocalDateCreator.localDate())
                .dateToGiveBack(LocalDateCreator.localDatePlus30Days())
                .build();
    }

    public static Loan createValidLoan() {
        APIClient apiClientValid = APIClientCreator.createValidAPIClient();

        Book bookRented = BookCreator.createBookRented();

        return Loan.builder()
                .id(1L)
                .userEmail(apiClientValid)
                .bookRented(bookRented)
                .loanDate(LocalDateCreator.localDate())
                .dateToGiveBack(LocalDateCreator.localDatePlus30Days())
                .build();
    }
}
