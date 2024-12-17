package com.ELOUSTA.ELOUSTA.backend.service.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.GoogleAuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.ResetPasswordRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.auth.TechnicianAuthenticationService;
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

class TechnicianEntityAuthenticationServiceTest {
    private TechnicianEntity technicianEntity;
    @Mock
    private TechnicianRepository technicianRepository;
    @InjectMocks
    private TechnicianAuthenticationService technicianAuthenticationService;
    @Mock
    private PasswordEncoder encoder;

    @BeforeAll
    void setup(){
        MockitoAnnotations.openMocks(this);

        technicianEntity = TechnicianEntity.builder()
                .username("test_user")
                .password("123456")
                .emailAddress("testEmailAddress")
                .roles("User_roles")
                .build();

    }

    @Test
    void loadUserByUsernameTest_userExist(){
        when(technicianRepository.findByUsername("test_user")).thenReturn(Optional.of(technicianEntity));

        UserDetails userDetails = technicianAuthenticationService.loadUserByUsername("test_user");

        assertNotNull(userDetails);
    }

    @Test
    void loadUserByUsernameTest_userNotExist(){
        when(technicianRepository.findByUsername("nonExistUserName")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> {
                    technicianAuthenticationService.loadUserByUsername("nonExistUserName");});
    }

    @Test
    void loadUserByUsernameAsTechnician_userExist(){
        when(technicianRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(technicianEntity));

        assertEquals(technicianEntity, technicianAuthenticationService.loadUserByUsernameAsTechnician("ExistUsername"));
    }
   @Test
    void loadUserByUsernameAsTechnician_userNotExist(){
        when(technicianRepository.findByUsername("NotExistUsername")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> {
                    technicianAuthenticationService.loadUserByUsernameAsTechnician("NotExistUsername");});
    }

    @Test
    void addTechnician(){
        when(technicianRepository.save(technicianEntity)).thenReturn(technicianEntity);
        when(encoder.encode(technicianEntity.getPassword())).thenReturn("encodedPassword");
        String validationStatus = technicianAuthenticationService.addTechnician(technicianEntity);
        assertEquals(ValidationStatus.VALID.getMessage(), validationStatus);
    }
    @Test
    void resetPasswordTest_userNotExist(){
        when(technicianRepository.findByUsername("NotExistUsername")).thenReturn(Optional.empty());
        ResetPasswordRequest request = new ResetPasswordRequest("NotExistUsername", "new password");

        assertEquals(ValidationStatus.FAIL.getMessage(), technicianAuthenticationService.resetPassword(request));
    }
    @Test
    void resetPasswordTest_userExist(){
        when(technicianRepository.findByUsername("testUser")).thenReturn(Optional.of(technicianEntity));

        String encodedPassword = "encodedNewPassword";
        when(encoder.encode("new password")).thenReturn(encodedPassword);

        ResetPasswordRequest request = new ResetPasswordRequest("testUser", "new password");
        String result = technicianAuthenticationService.resetPassword(request);

        assertEquals(ValidationStatus.VALID.getMessage(), result);
        assertEquals(encodedPassword, technicianEntity.getPassword());
    }
    @Test
    void validateUniqueUsernameAndEmailTest_ValidSignUp(){
        when(technicianRepository.findByUsername("notExistUsername")).thenReturn(Optional.empty());
        when(technicianRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        TechnicianEntity newUser = TechnicianEntity.builder()
                .username("notExistUsername")
                .emailAddress("NotExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.VALID.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_UsernameExist(){
        when(technicianRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(technicianEntity));
        when(technicianRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        TechnicianEntity newUser = TechnicianEntity.builder()
                .username("ExistUsername")
                .emailAddress("NotExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressExist(){
        when(technicianRepository.findByUsername("NotExistUsername")).thenReturn(Optional.empty());
        when(technicianRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(technicianEntity));

        TechnicianEntity newUser = TechnicianEntity.builder()
                .username("NotExistUsername")
                .emailAddress("ExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_EMAIL.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressAndUsernameExist(){
        when(technicianRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(technicianEntity));
        when(technicianRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(technicianEntity));

        TechnicianEntity newUser = TechnicianEntity.builder()
                .username("ExistUsername")
                .emailAddress("ExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        // invalid username as we check uniqueness of username first
        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }


    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_UsernameIsNull(){
        when(technicianRepository.findByEmailAddress("EmailAddress")).thenReturn(Optional.empty());


        TechnicianEntity newUser = TechnicianEntity.builder()
                .emailAddress("EmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressIsNull(){
        when(technicianRepository.findByUsername("username")).thenReturn(Optional.empty());

        TechnicianEntity newUser = TechnicianEntity.builder()
                .username("username")
                .build();

        String uniqueUsernameAndEmailAddress = technicianAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_EMAIL.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressAndUserNameIsNull(){
        TechnicianEntity newUser = TechnicianEntity.builder()
                .build();

        String uniqueUsernameAndEmailAddress = technicianAuthenticationService.validateUniqueUsernameAndEmail(newUser);

        // As we check the uniqueness of username first
        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validAuthenticationWithGoogleExistUser(){
        when(technicianRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(technicianEntity));

        assertTrue(technicianAuthenticationService.validAuthenticationWithGoogle(new GoogleAuthRequest("ExistEmailAddress")));
    }

    @Test
    void validAuthenticationWithGoogleNotExistUser(){
        when(technicianRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        assertFalse(technicianAuthenticationService.validAuthenticationWithGoogle(new GoogleAuthRequest("NotExistEmailAddress")));
    }

    @Test
    void loadUserByEmailAddressExistUser(){
        when(technicianRepository.findByEmailAddress("testEmailAddress")).thenReturn(Optional.of(technicianEntity));

        assertEquals(technicianEntity, technicianAuthenticationService.loadUserByEmailAddress("testEmailAddress"));
    }

    @Test
    void loadUserByEmailAddressNotExistUser(){
        when(technicianRepository.findByEmailAddress("notExistEmailAddress")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> {
                    technicianAuthenticationService.loadUserByEmailAddress("notExistEmailAddress");});
    }
}