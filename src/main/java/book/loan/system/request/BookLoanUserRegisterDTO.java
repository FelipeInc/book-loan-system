package book.loan.system.request;

import book.loan.system.domain.UserRoles;

public record BookLoanUserRegisterDTO(String name, String username, String password, UserRoles authorities) {

}
