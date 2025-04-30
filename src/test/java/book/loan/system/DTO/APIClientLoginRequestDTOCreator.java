package book.loan.system.DTO;

import book.loan.system.request.APIClientLoginRequestDTO;
import book.loan.system.util.APIClientCreator;

public class APIClientLoginRequestDTOCreator {
    public static APIClientLoginRequestDTO createAPIClientLoginRequestDTO(){
        return APIClientLoginRequestDTO.builder()
                .email(APIClientCreator.createValidAPIClient().getEmail())
                .password(APIClientCreator.createValidAPIClient().getPassword())
                .build();
    }
}
