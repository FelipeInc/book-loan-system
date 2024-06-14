package book.loan.system.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookPutRequestBody {
    private Long id;
    @NotEmpty(message = "The book title cannot be empty")
    @NotNull(message = "The book title cannot be null")
    private String title;
    @NotNull  (message = "The author name cannot be null")
    @NotEmpty(message = "The author name cannot be empty")
    private String author;
    @NotNull  (message = "The ISBN cannot be null")
    private Long isbn;
}
