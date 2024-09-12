package book.loan.system.request;

import book.loan.system.domain.BookLoanUserRoles;

public record BookLoanUserRegisterDTO(String name, String username, String password, BookLoanUserRoles authorities) {

}
