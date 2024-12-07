package com.ELOUSTA.ELOUSTA.backend.service.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.GoogleAuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.ResetPasswordRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class ClientEntityAuthenticationServiceTest {
    private ClientEntity clientEntity;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientAuthenticationService clientAuthenticationService;

    @Mock
    private PasswordEncoder encoder;

    @BeforeAll
    void setup(){
        MockitoAnnotations.openMocks(this);

        clientEntity = ClientEntity.builder()
                .username("test_user")
                .password("123456")
                .emailAddress("testEmailAddress")
                .roles("User_roles")
                .build();

    }

    @Test
    void loadUserByUsernameTest_userExist(){
        when(clientRepository.findByUsername("test_user")).thenReturn(Optional.of(clientEntity));

        UserDetails userDetails = clientAuthenticationService.loadUserByUsername("test_user");

        assertNotNull(userDetails);
    }

    @Test
    void loadUserByUsernameTest_userNotExist(){
        when(clientRepository.findByUsername("nonExistUserName")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> {
                    clientAuthenticationService.loadUserByUsername("nonExistUserName");});
    }

    @Test
    void loadUserByUsernameAsTechnician_userExist(){
        when(clientRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(clientEntity));

        assertEquals(clientEntity, clientAuthenticationService.loadUserByUsernameAsUserInfo("ExistUsername"));
    }
    @Test
    void loadUserByUsernameAsTechnician_userNotExist(){
        when(clientRepository.findByUsername("NotExistUsername")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> {
                    clientAuthenticationService.loadUserByUsernameAsUserInfo("NotExistUsername");});
    }

    @Test
    void addUser(){
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);
        when(encoder.encode(clientEntity.getPassword())).thenReturn("encodedPassword");
        String validationStatus = clientAuthenticationService.addUser(clientEntity);
        assertEquals(ValidationStatus.VALID.getMessage(), validationStatus);
    }

    @Test
    void resetPasswordTest_userNotExist(){
        when(clientRepository.findByUsername("NotExistUsername")).thenReturn(Optional.empty());
        ResetPasswordRequest request = new ResetPasswordRequest("NotExistUsername", "new password");

        assertEquals(ValidationStatus.FAIL.getMessage(), clientAuthenticationService.resetPassword(request));
    }
    @Test
    void resetPasswordTest_userExist(){
        when(clientRepository.findByUsername("testUser")).thenReturn(Optional.of(clientEntity));

        String encodedPassword = "encodedNewPassword";
        when(encoder.encode("new password")).thenReturn(encodedPassword);

        ResetPasswordRequest request = new ResetPasswordRequest("testUser", "new password");
        String result = clientAuthenticationService.resetPassword(request);
        
        assertEquals(ValidationStatus.VALID.getMessage(), result);
        assertEquals(encodedPassword, clientEntity.getPassword());
    }

    @Test
    void validateUniqueUsernameAndEmailTest_ValidSignUp(){
        when(clientRepository.findByUsername("notExistUsername")).thenReturn(Optional.empty());
        when(clientRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        ClientEntity newUser = ClientEntity.builder()
                .username("notExistUsername")
                .emailAddress("NotExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = clientAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.VALID.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_UsernameExist(){
        when(clientRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(clientEntity));
        when(clientRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        ClientEntity newUser = ClientEntity.builder()
                .username("ExistUsername")
                .emailAddress("NotExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = clientAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressExist(){
        when(clientRepository.findByUsername("NotExistUsername")).thenReturn(Optional.empty());
        when(clientRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(clientEntity));

        ClientEntity newUser = ClientEntity.builder()
                .username("NotExistUsername")
                .emailAddress("ExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = clientAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_EMAIL.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressAndUsernameExist(){
        when(clientRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(clientEntity));
        when(clientRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(clientEntity));

        ClientEntity newUser = ClientEntity.builder()
                .username("ExistUsername")
                .emailAddress("ExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = clientAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        // invalid username as we check uniqueness of username first
        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }


    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_UsernameIsNull(){
        when(clientRepository.findByEmailAddress("EmailAddress")).thenReturn(Optional.empty());


        ClientEntity newUser = ClientEntity.builder()
                .emailAddress("EmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = clientAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressIsNull(){
        when(clientRepository.findByUsername("username")).thenReturn(Optional.empty());

        ClientEntity newUser = ClientEntity.builder()
                .username("username")
                .build();

        String uniqueUsernameAndEmailAddress = clientAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_EMAIL.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressAndUserNameIsNull(){
        ClientEntity newUser = ClientEntity.builder()
                .build();

        String uniqueUsernameAndEmailAddress = clientAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        // As we check the uniqueness of username first
        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validAuthenticationWithGoogleExistUser(){
        when(clientRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(clientEntity));

        assertTrue(clientAuthenticationService.validAuthenticationWithGoogle(new GoogleAuthRequest("ExistEmailAddress")));
    }

    @Test
    void validAuthenticationWithGoogleNotExistUser(){
        when(clientRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        assertFalse(clientAuthenticationService.validAuthenticationWithGoogle(new GoogleAuthRequest("NotExistEmailAddress")));
    }

    @Test
    void loadUserByEmailAddressExistUser(){
        when(clientRepository.findByEmailAddress("testEmailAddress")).thenReturn(Optional.of(clientEntity));

        assertEquals(clientEntity, clientAuthenticationService.loadUserByEmailAddress("testEmailAddress"));
    }

    @Test
    void loadUserByEmailAddressNotExistUser(){
        when(clientRepository.findByEmailAddress("notExistEmailAddress")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> {
                    clientAuthenticationService.loadUserByEmailAddress("notExistEmailAddress");});
    }
}