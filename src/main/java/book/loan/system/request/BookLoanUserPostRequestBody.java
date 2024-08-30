package book.loan.system.request;

import book.loan.system.domain.BookLoanUser;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BookLoanUserPostRequestBody extends BookLoanUser {
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String password;
    //@NotNull
    //private String authorities;
}//
