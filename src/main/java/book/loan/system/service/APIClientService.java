package book.loan.system.service;

import book.loan.system.domain.APIClient;
import book.loan.system.exception.BadRequestException;
import book.loan.system.exception.NotFoundException;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.request.APIClientRegisterRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class APIClientService{

    private final APIClientRepository apiClientRepository;

    public APIClient findUserByEmailOrThrowNotFoundException(String email){
        return apiClientRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("The user is not found"));
    }

    @Transactional
    public APIClient registerUser(APIClientRegisterRequestDTO userRegister) {
        if (apiClientRepository.findByEmailIgnoreCase(userRegister.email()).isPresent()) {
            throw new BadRequestException("This email is already registered");

        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegister.password());
            APIClient user = APIClient.builder()
                    .name(userRegister.name())
                    .email(userRegister.email())
                    .userPassword(encryptedPassword)
                    .authorities(userRegister.authorities())
                    .build();

            return apiClientRepository.save(user);
        }
}
