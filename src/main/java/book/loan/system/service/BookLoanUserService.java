package book.loan.system.service;

import book.loan.system.repository.BookLoanUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookLoanUserService implements UserDetailsService {
    private final BookLoanUserRepository bookLoanUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(bookLoanUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}
