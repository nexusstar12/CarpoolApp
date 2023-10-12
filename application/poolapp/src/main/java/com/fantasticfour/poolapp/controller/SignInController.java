package com.fantasticfour.poolapp.controller;

import com.fantasticfour.poolapp.domain.Account;
import com.fantasticfour.poolapp.domain.Password;
import com.fantasticfour.poolapp.domain.User;
import com.fantasticfour.poolapp.repository.AccountRepository;
import com.fantasticfour.poolapp.repository.PasswordRepository;
import com.fantasticfour.poolapp.repository.UserRepository;
import com.fantasticfour.poolapp.services.AccountService;
import com.fantasticfour.poolapp.services.PasswordService;
import com.fantasticfour.poolapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/signin")
public class SignInController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/")
    public ResponseEntity<List<Object>> signInUser (@RequestBody Map<String, String> jsonMap) {

        jsonMap.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
        List<Object> responseList = new ArrayList<>();

        //user to response list if email exists.
       Optional<User> optionalUser = userRepository.findByEmail(jsonMap.get("email"));

       //check if user exists by email
       User user;
       if (optionalUser.isPresent()){
           user = optionalUser.get();
       }else {
           responseList.add("Incorrect username or password");
           return new ResponseEntity<>(responseList, HttpStatus.NOT_FOUND);
       }

       Optional<Account> optionalAccount = accountRepository.findAccountByUserId(user.getUserId());

       Account account;
       if (optionalAccount.isPresent()) {
           account = optionalAccount.get();
       }else {
           responseList.add("Could not find account");
           return new ResponseEntity<>(responseList, HttpStatus.NOT_FOUND);
       }

       //check if password matches
       if (account.getPassword().getPassword().equals(jsonMap.get("password"))) {
           responseList.add(user);
           return new ResponseEntity<>(responseList, HttpStatus.FOUND);
        }

        responseList.add("Incorrect username or password");
       return new ResponseEntity<>(responseList, HttpStatus.NOT_FOUND);



    }
}
