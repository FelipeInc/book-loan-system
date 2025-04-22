package book.loan.system.util;

import book.loan.system.domain.APIClient;
import book.loan.system.domain.UserRoles;

public class APIClientCreator {
    public static APIClient createUserToBeSaved() {
    return APIClient.builder()
            .name("Felipe Silva")
            .email("Felipe20@gmail.com")
            .userPassword("123456")
            .authorities(UserRoles.ADMIN)
            .build();
}

public static APIClient createValidAPIClient() {
    return APIClient.builder()
            .id(1L)
            .name("Felipe Silva")
            .email("Felipe20Silva@gmail.com")
            .userPassword("123456")
            .authorities(UserRoles.ADMIN)
            .build();
}

    public static APIClient createUserWithPasswordEmpty() {
        return APIClient.builder()
                .name("Felipe Silva")
                .email("Felipe20Silva@gmail.com")
                .userPassword("")
                .authorities(UserRoles.ADMIN)
                .build();
    }

    public static APIClient createUserWithEmailEmpty() {
        return APIClient.builder()
                .name("Felipe Silva")
                .email("")
                .userPassword("123456")
                .authorities(UserRoles.ADMIN)
                .build();
    }

    public static APIClient createUserWithNameEmpty() {
        return APIClient.builder()
                .name(null)
                .email("Felipe20Silva@gmail.com")
                .userPassword("123456")
                .authorities(UserRoles.ADMIN)
                .build();
    }

}
