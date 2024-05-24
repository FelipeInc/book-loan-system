package book.loan.system.validator;

import book.loan.system.exception.BadRequestException;
import org.springframework.stereotype.Component;

import javax.swing.text.PlainDocument;

@Component
public class Validator extends PlainDocument {
    private final int minISBN = 13;
    private final int maxChar = 200;

    public Long isbnValidator(Long str) {
        if (str.toString().length() < minISBN) {
            throw new BadRequestException("The ISBN must be at least 13 characters long");

        } else if (str.toString().length() > minISBN) {
            throw new BadRequestException("The ISBN must have a maximum of 13 characters");
        }else{
            return str;
        }
    }

    public String bookTitleValidator(String str) {
        if (str.length() > maxChar) {
            throw new BadRequestException("The book title must have a maximum of 200 characters");
        }else {
            return str;
        }
    }

    public String authorNameValidator(String str) {
        if (str.length() > maxChar) {
            throw new BadRequestException("The author name must have a maximum of 200 characters" );
        }else {
            return str;
        }
    }
}