package book.loan.system.DTO;

import book.loan.system.request.LoanPostRequestDTO;
import book.loan.system.util.APIClientCreator;
import book.loan.system.util.BookCreator;

public class LoanPostRequestDTOCreator {
    public static LoanPostRequestDTO createLoanRequestDTO(){
        return LoanPostRequestDTO.builder()
                .email(APIClientCreator.createUserToBeSaved().getEmail())
                .id(BookCreator.createBookRented().getId())
                .build();
    }
}
