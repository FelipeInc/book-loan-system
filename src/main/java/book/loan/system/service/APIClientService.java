package book.loan.system.service;

import book.loan.system.config.TokenService;
import book.loan.system.domain.APIClient;
import book.loan.system.exception.BadRequestException;
import book.loan.system.repository.APIClientRepository;
import book.loan.system.request.LoginResponseDTO;
import book.loan.system.request.UserLoginDTO;
import book.loan.system.request.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class APIClientService implements UserDetailsService {
    @Autowired
    private  APIClientRepository apiClientRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return apiClientRepository.findByEmailIgnoreCase(email);
    }


    public APIClient registerUser(UserRegisterDTO userRegister) {
        if (apiClientRepository.findByEmailIgnoreCase(userRegister.email()) != null) {
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
