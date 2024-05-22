package book.loan.system.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BookPostRequestBody {
    private String title;
    private String author;
    private Long isbn;
}
