package com.fantasticfour.poolapp.services;

import com.fantasticfour.poolapp.domain.Account;
import com.fantasticfour.poolapp.domain.Password;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.AccountRepository;
import com.fantasticfour.poolapp.repository.PasswordRepository;
import com.fantasticfour.poolapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordRepository passwordRepository;

    //adds a  new user to the user table of the database
    public User addUser(User user) {
        return userRepository.save(user);
    }

    //to implement UserDetailsService

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Fetch the associated Account
        Account account = accountRepository.findAccountByUserId(user.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("Account not found for user"));

        // Fetch the Password from the Account
        Password password = passwordRepository.findById(account.getPassword().getPasswordId())
                .orElseThrow(() -> new UsernameNotFoundException("Password not found for account"));

        return new CustomUserDetails(user, password.getPassword());
    }
}
