package book.loan.system.validator;

import book.loan.system.exception.BadRequestException;

public class CharValidator {

    public String alphanumericValidator(String input, String message){
        if (input.matches("^[a-zA-Z0-9 ]+$")) {
            return input;
        } else {
            throw new BadRequestException(message);
        }
    }
}
