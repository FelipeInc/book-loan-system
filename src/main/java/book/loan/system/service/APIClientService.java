package book.loan.system.service;

import book.loan.system.domain.APIClient;
import book.loan.system.exception.BadRequestException;
import book.loan.system.exception.NotFoundException;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.request.UserRegisterDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class APIClientService implements UserDetailsService {

    private final APIClientRepository apiClientRepository;

    public Page<APIClient> findAllUsers(Pageable pageable){
        return apiClientRepository.findAll(pageable);
    }

    public APIClient findUserByIdOrThrowNotFoundException(String email){
        return apiClientRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("The user is not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return findUserByIdOrThrowNotFoundException(email);
    }

    @Transactional
    public APIClient registerUser(UserRegisterDTO userRegister) {
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
