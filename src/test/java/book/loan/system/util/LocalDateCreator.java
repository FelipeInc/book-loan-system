package book.loan.system.util;

import java.time.LocalDate;

public class LocalDateCreator {
    public static LocalDate localDate(){
        return LocalDate.now();
    }

    public static LocalDate localDatePlus30Days(){
        return LocalDate.now().plusDays(30);

    }
}
