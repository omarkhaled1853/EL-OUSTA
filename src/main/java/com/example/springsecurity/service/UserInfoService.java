package com.example.springsecurity.service;

import com.example.springsecurity.Enums.ValidationStatus;
import com.example.springsecurity.entity.GoogleAuthRequest;
import com.example.springsecurity.entity.UserInfo;
import com.example.springsecurity.repository.UserInfoRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);

        if(userInfo.isPresent()){
            return new UserInfoDetails(userInfo.get());
        }

        throw new UsernameNotFoundException("No user with name: " + username);
    }

    public String addUser(UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);

        return ValidationStatus.VALID.getMessage();
    }
    public String validateUniqueUsernameAndEmail(UserInfo userInfo){
        if(!validUsername(userInfo.getUsername())){
            return ValidationStatus.INVALID_USERNAME.getMessage();
        }
        if(!validEmailAddress(userInfo.getEmailAddress())){
            return ValidationStatus.INVALID_EMAIL.getMessage();
        }
        return ValidationStatus.VALID.getMessage();
    }
    private Boolean validUsername(String username){
        if(username == null){
            return false;
        }
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);
        return userInfo.isEmpty();
    }
    private Boolean validEmailAddress(String emailAddress){
        if (emailAddress == null){
            return false;
        }
        Optional<UserInfo> userInfo = userRepository.findByEmailAddress(emailAddress);
        return userInfo.isEmpty();
    }

    //In authentication with google we just check if email exist
    public Boolean validAuthenticationWithGoogle(GoogleAuthRequest googleAuthRequest){
        Optional<UserInfo> userInfo = userRepository.findByEmailAddress(googleAuthRequest.getEmailAddress());
        return userInfo.isPresent();
    }
    public UserInfo loadUserByEmailAddress(String emailAddress){
        Optional<UserInfo> userInfo = userRepository.findByEmailAddress(emailAddress);

        if(userInfo.isPresent()){
            return userInfo.get();
        }
        throw new IllegalArgumentException("Invalid emailAddress");
    }
}
