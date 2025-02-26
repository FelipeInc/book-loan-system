package book.loan.system.service;

import book.loan.system.repository.APIClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class APIClientService implements UserDetailsService {

    @Autowired
    APIClientRepository bookLoanUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return bookLoanUserRepository.findByEmailIgnoreCase(email);
    }
}
