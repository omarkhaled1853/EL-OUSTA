package com.ELOUSTA.ELOUSTA.backend.service.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.GoogleAuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.ResetPasswordRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ClientAuthenticationService implements UserDetailsService {

    @Autowired
    private ClientRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientEntity> userInfo = userRepository.findByUsername(username);

        if(userInfo.isPresent()){
            return new ClientAuthenticationDetails(userInfo.get());
        }

        throw new UsernameNotFoundException("No user with name: " + username);
    }
    public ClientEntity loadUserByUsernameAsUserInfo(String username){
        Optional<ClientEntity> userInfo = userRepository.findByUsername(username);

        if(userInfo.isPresent()){
            return userInfo.get();
        }
        throw new IllegalArgumentException("Invalid username");
    }

    public String addUser(ClientEntity clientEntity){
        clientEntity.setPassword(encoder.encode(clientEntity.getPassword()));
        return userRepository.save(clientEntity).getId().toString();
    }
    public String resetPassword(ResetPasswordRequest request){
        Optional<ClientEntity> userInfo = userRepository.findByUsername(request.getUsername());

        if(userInfo.isEmpty()){
            return ValidationStatus.FAIL.getMessage();
        }
        userInfo.get().setPassword(request.getPassword());
        return addUser(userInfo.get());
    }
    // Check the uniqueness of the user (there is no user in DB with the same email or username)
    public String validateUniqueUsernameAndEmail(ClientEntity clientEntity){
        if(!validUsername(clientEntity.getUsername())){
            return ValidationStatus.INVALID_USERNAME.getMessage();
        }
        if(!validEmailAddress(clientEntity.getEmailAddress())){
            return ValidationStatus.INVALID_EMAIL.getMessage();
        }
        return ValidationStatus.VALID.getMessage();
    }
    // Check that username set the value and the uniqueness of username
    private Boolean validUsername(String username){
        if(username == null){
            return false;
        }
        Optional<ClientEntity> userInfo = userRepository.findByUsername(username);
        return userInfo.isEmpty();
    }
    // Check that username set the value and the uniqueness of username
    private Boolean validEmailAddress(String emailAddress){
        if (emailAddress == null){
            return false;
        }
        Optional<ClientEntity> userInfo = userRepository.findByEmailAddress(emailAddress);
        return userInfo.isEmpty();
    }

    //In authentication with Google we just check if email exist
    public Boolean validAuthenticationWithGoogle(GoogleAuthRequest googleAuthRequest){
        Optional<ClientEntity> userInfo = userRepository.findByEmailAddress(googleAuthRequest.getEmailAddress());
        return userInfo.isPresent();
    }
    public ClientEntity loadUserByEmailAddress(String emailAddress){
        Optional<ClientEntity> userInfo = userRepository.findByEmailAddress(emailAddress);

        if(userInfo.isPresent()){
            return userInfo.get();
        }
        throw new IllegalArgumentException("Invalid emailAddress");
    }
}