package book.loan.system.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(@NotBlank(message = "Email is required") @Email(message = "Enter a valid email address") String email, String password) {
}
