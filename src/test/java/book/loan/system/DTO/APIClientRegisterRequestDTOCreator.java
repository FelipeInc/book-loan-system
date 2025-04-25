package book.loan.system.DTO;

import book.loan.system.domain.UserRoles;
import book.loan.system.request.APIClientRegisterRequestDTO;
import book.loan.system.util.APIClientCreator;

public class APIClientRegisterRequestDTOCreator {
    public static APIClientRegisterRequestDTO createAPIClientRegisterRequestDTO(){
        return APIClientRegisterRequestDTO.builder()
                .name(APIClientCreator.createUserToBeSaved().getName())
                .email(APIClientCreator.createUserToBeSaved().getEmail())
                .password(APIClientCreator.createUserToBeSaved().getPassword())
                .authorities(UserRoles.ADMIN)
                .build();
    }
}
