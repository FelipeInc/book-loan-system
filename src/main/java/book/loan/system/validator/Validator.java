package book.loan.system.validator;

import book.loan.system.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    CharValidator charValidator = new CharValidator();
    private final int maxChar = 200;

    public Long isbnValidator(Long str) {
        if (str.toString().length() < 13) {
            throw new BadRequestException("The ISBN must be at least 13 characters long");

        } else if (str.toString().length() > 13) {
            throw new BadRequestException("The ISBN must have a maximum of 13 characters");
        }else{
            return str;
        }
    }

    public String bookTitleValidator(String str) {
        charValidator.alphanumericValidator(str,
                "The book title must be written with alphanumeric characters");
        if (str.length() > maxChar) {
            throw new BadRequestException("The book title must have a maximum of 200 characters");
        }else {
            return str;
        }
    }

    public String authorNameValidator(String str){
        charValidator.alphanumericValidator(str,
                "The author name must be written with alphanumeric characters");
        if (str.length() > maxChar) {
            throw new BadRequestException("The author name must have a maximum of 200 characters" );
        }else {
            return str;
        }
    }

    public int idValidator(Integer id){
        if (id == null){
            throw new BadRequestException("The book id cannot be null");
        } else if (id.toString().isEmpty()) {
            throw new BadRequestException("The book id cannot be empty");
        }
        return id;
    }
}