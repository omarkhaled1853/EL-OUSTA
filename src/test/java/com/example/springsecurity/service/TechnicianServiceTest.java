package com.example.springsecurity.service;

import com.example.springsecurity.Enums.ValidationStatus;
import com.example.springsecurity.entity.GoogleAuthRequest;
import com.example.springsecurity.entity.Technician;
import com.example.springsecurity.repository.TechnicianRepository;
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

class TechnicianServiceTest {
    private Technician technician;
    @Mock
    private TechnicianRepository technicianRepository;
    @InjectMocks
    private TechnicianService technicianService;

    @Mock
    private PasswordEncoder encoder;

    @BeforeAll
    void setup(){
        MockitoAnnotations.openMocks(this);

        technician = Technician.builder()
                .username("test_user")
                .password("123456")
                .emailAddress("testEmailAddress")
                .roles("User_roles")
                .build();

    }

    @Test
    void loadUserByUsernameTest_userExist(){
        when(technicianRepository.findByUsername("test_user")).thenReturn(Optional.of(technician));

        UserDetails userDetails = technicianService.loadUserByUsername("test_user");

        assertNotNull(userDetails);
    }

    @Test
    void loadUserByUsernameTest_userNotExist(){
        when(technicianRepository.findByUsername("nonExistUserName")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> {technicianService.loadUserByUsername("nonExistUserName");});
    }

    // this message a
    @Test
    void addTechnician(){
        when(technicianRepository.save(technician)).thenReturn(technician);
        when(encoder.encode(technician.getPassword())).thenReturn("encodedPassword");
        String validationStatus = technicianService.addTechnician(technician);
        assertEquals(ValidationStatus.VALID.getMessage(), validationStatus);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_ValidSignUp(){
        when(technicianRepository.findByUsername("notExistUsername")).thenReturn(Optional.empty());
        when(technicianRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        Technician newUser = Technician.builder()
                .username("notExistUsername")
                .emailAddress("NotExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.VALID.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_UsernameExist(){
        when(technicianRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(technician));
        when(technicianRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        Technician newUser = Technician.builder()
                .username("ExistUsername")
                .emailAddress("NotExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressExist(){
        when(technicianRepository.findByUsername("NotExistUsername")).thenReturn(Optional.empty());
        when(technicianRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(technician));

        Technician newUser = Technician.builder()
                .username("NotExistUsername")
                .emailAddress("ExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_EMAIL.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressAndUsernameExist(){
        when(technicianRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(technician));
        when(technicianRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(technician));

        Technician newUser = Technician.builder()
                .username("ExistUsername")
                .emailAddress("ExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianService.validateUniqueUsernameAndEmail(newUser);

        // invalid username as we check uniqueness of username first
        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }


    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_UsernameIsNull(){
        when(technicianRepository.findByEmailAddress("EmailAddress")).thenReturn(Optional.empty());


        Technician newUser = Technician.builder()
                .emailAddress("EmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = technicianService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressIsNull(){
        when(technicianRepository.findByUsername("username")).thenReturn(Optional.empty());

        Technician newUser = Technician.builder()
                .username("username")
                .build();

        String uniqueUsernameAndEmailAddress = technicianService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_EMAIL.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressAndUserNameIsNull(){
        Technician newUser = Technician.builder()
                .build();

        String uniqueUsernameAndEmailAddress = technicianService.validateUniqueUsernameAndEmail(newUser);

        // As we check the uniqueness of username first
        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validAuthenticationWithGoogleExistUser(){
        when(technicianRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(technician));

        assertTrue(technicianService.validAuthenticationWithGoogle(new GoogleAuthRequest("ExistEmailAddress")));
    }

    @Test
    void validAuthenticationWithGoogleNotExistUser(){
        when(technicianRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        assertFalse(technicianService.validAuthenticationWithGoogle(new GoogleAuthRequest("NotExistEmailAddress")));
    }

    @Test
    void loadUserByEmailAddressExistUser(){
        when(technicianRepository.findByEmailAddress("testEmailAddress")).thenReturn(Optional.of(technician));

        assertEquals(technician, technicianService.loadUserByEmailAddress("testEmailAddress"));
    }

    @Test
    void loadUserByEmailAddressNotExistUser(){
        when(technicianRepository.findByEmailAddress("notExistEmailAddress")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> {technicianService.loadUserByEmailAddress("notExistEmailAddress");});
    }
}