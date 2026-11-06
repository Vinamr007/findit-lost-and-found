
package com.findit.findit.service;

import com.findit.findit.dto.UserRegistrationRequest;
import com.findit.findit.entity.User;
import com.findit.findit.exception.EmailAlreadyExistsException;
import com.findit.findit.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    public void registerUser(UserRegistrationRequest request){

        if(emailExists(request.getEmail())){
            throw new EmailAlreadyExistsException("email already exists . please login insted with correct password");

        }

       User user = new User();

        user.setName(request.getName());
user.setEmail(request.getEmail());
user.setPassword(passwordEncoder.encode(request.getPassword()));
user.setRole("USER");

userRepository.save(user);


    }

}
