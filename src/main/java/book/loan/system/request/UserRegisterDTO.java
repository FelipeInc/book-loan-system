package book.loan.system.request;

import book.loan.system.domain.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(String name, @NotBlank(message = "Email is required") @Email(message = "Enter a valid email address") String email, String password, UserRoles authorities) {

}
