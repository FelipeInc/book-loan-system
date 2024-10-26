package book.loan.system.service;

import book.loan.system.repository.BookLoanUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookLoanUserService implements UserDetailsService {

    @Autowired
    BookLoanUserRepository bookLoanUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return bookLoanUserRepository.findByUsername(username);
    }
}
