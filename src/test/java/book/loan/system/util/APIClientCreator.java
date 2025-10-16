package book.loan.system.util;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.UserRoles;

public class APIClientCreator {
    public static APIClient createUserToBeSaved() {
    return APIClient.builder()
            .name("testForAuthenticationControllerIT")
            .email("testForAuthenticationControllerIT@gmail.com")
            .userPassword("123456")
            .authorities(UserRoles.ADMIN)
            .build();
}

public static APIClient createUserToBeSavedLoanControllerTestIT() {
    return APIClient.builder()
            .name("testForLoanControllerIT")
            .email("testForLoanControllerIT@gmail.com")
            .userPassword("123456")
            .authorities(UserRoles.ADMIN)
            .build();
}

public static APIClient createValidAPIClient() {
    return APIClient.builder()
            .id(1L)
            .name("testCreateValidClient")
            .email("testCreateValidClien@gmail.com")
            .userPassword("123456")
            .authorities(UserRoles.ADMIN)
            .build();
}

    public static APIClient createUserWithPasswordEmpty() {
        return APIClient.builder()
                .name("testClientWithPassWordEmpty")
                .email("testClientWithPassWordEmpty@gmail.com")
                .userPassword("")
                .authorities(UserRoles.ADMIN)
                .build();
    }

    public static APIClient createUserWithEmailEmpty() {
        return APIClient.builder()
                .name("testClientWithEmailEmpty")
                .email("")
                .userPassword("123456")
                .authorities(UserRoles.ADMIN)
                .build();
    }

    public static APIClient createUserWithNameEmpty() {
        return APIClient.builder()
                .name(null)
                .email("testClientWithNameEmpty@gmail.com")
                .userPassword("123456")
                .authorities(UserRoles.ADMIN)
                .build();
    }

}
