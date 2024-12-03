package com.example.springsecurity.service;

import com.example.springsecurity.Enums.ValidationStatus;
import com.example.springsecurity.entity.GoogleAuthRequest;
import com.example.springsecurity.entity.UserInfo;
import com.example.springsecurity.repository.UserInfoRepository;
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

class UserInfoServiceTest {
    private UserInfo userInfo;
    @Mock
    private UserInfoRepository userInfoRepository;
    @InjectMocks
    private UserInfoService userInfoService;

    @Mock
    private PasswordEncoder encoder;

    @BeforeAll
    void setup(){
        MockitoAnnotations.openMocks(this);

        userInfo = UserInfo.builder()
                .username("test_user")
                .password("123456")
                .emailAddress("testEmailAddress")
                .roles("User_roles")
                .build();

    }

    @Test
    void loadUserByUsernameTest_userExist(){
        when(userInfoRepository.findByUsername("test_user")).thenReturn(Optional.of(userInfo));

        UserDetails userDetails = userInfoService.loadUserByUsername("test_user");

        assertNotNull(userDetails);
    }

    @Test
    void loadUserByUsernameTest_userNotExist(){
        when(userInfoRepository.findByUsername("nonExistUserName")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> {userInfoService.loadUserByUsername("nonExistUserName");});
    }

    @Test
    void loadUserByUsernameAsTechnician_userExist(){
        when(userInfoRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(userInfo));

        assertEquals(userInfo, userInfoService.loadUserByUsernameAsUserInfo("ExistUsername"));
    }
    @Test
    void loadUserByUsernameAsTechnician_userNotExist(){
        when(userInfoRepository.findByUsername("NotExistUsername")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> {userInfoService.loadUserByUsernameAsUserInfo("NotExistUsername");});
    }

    @Test
    void addUser(){
        when(userInfoRepository.save(userInfo)).thenReturn(userInfo);
        when(encoder.encode(userInfo.getPassword())).thenReturn("encodedPassword");
        String validationStatus = userInfoService.addUser(userInfo);
        assertEquals(ValidationStatus.VALID.getMessage(), validationStatus);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_ValidSignUp(){
        when(userInfoRepository.findByUsername("notExistUsername")).thenReturn(Optional.empty());
        when(userInfoRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        UserInfo newUser = UserInfo.builder()
                .username("notExistUsername")
                .emailAddress("NotExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = userInfoService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.VALID.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_UsernameExist(){
        when(userInfoRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(userInfo));
        when(userInfoRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        UserInfo newUser = UserInfo.builder()
                .username("ExistUsername")
                .emailAddress("NotExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = userInfoService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressExist(){
        when(userInfoRepository.findByUsername("NotExistUsername")).thenReturn(Optional.empty());
        when(userInfoRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(userInfo));

        UserInfo newUser = UserInfo.builder()
                .username("NotExistUsername")
                .emailAddress("ExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = userInfoService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_EMAIL.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressAndUsernameExist(){
        when(userInfoRepository.findByUsername("ExistUsername")).thenReturn(Optional.of(userInfo));
        when(userInfoRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(userInfo));

        UserInfo newUser = UserInfo.builder()
                .username("ExistUsername")
                .emailAddress("ExistEmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = userInfoService.validateUniqueUsernameAndEmail(newUser);

        // invalid username as we check uniqueness of username first
        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }


    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_UsernameIsNull(){
        when(userInfoRepository.findByEmailAddress("EmailAddress")).thenReturn(Optional.empty());


        UserInfo newUser = UserInfo.builder()
                .emailAddress("EmailAddress")
                .build();

        String uniqueUsernameAndEmailAddress = userInfoService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressIsNull(){
        when(userInfoRepository.findByUsername("username")).thenReturn(Optional.empty());

        UserInfo newUser = UserInfo.builder()
                .username("username")
                .build();

        String uniqueUsernameAndEmailAddress = userInfoService.validateUniqueUsernameAndEmail(newUser);

        assertEquals(ValidationStatus.INVALID_EMAIL.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validateUniqueUsernameAndEmailTest_InValidSignUp_EmailAddressAndUserNameIsNull(){
        UserInfo newUser = UserInfo.builder()
                .build();

        String uniqueUsernameAndEmailAddress = userInfoService.validateUniqueUsernameAndEmail(newUser);

        // As we check the uniqueness of username first
        assertEquals(ValidationStatus.INVALID_USERNAME.getMessage(), uniqueUsernameAndEmailAddress);
    }

    @Test
    void validAuthenticationWithGoogleExistUser(){
        when(userInfoRepository.findByEmailAddress("ExistEmailAddress")).thenReturn(Optional.of(userInfo));

        assertTrue(userInfoService.validAuthenticationWithGoogle(new GoogleAuthRequest("ExistEmailAddress")));
    }

    @Test
    void validAuthenticationWithGoogleNotExistUser(){
        when(userInfoRepository.findByEmailAddress("NotExistEmailAddress")).thenReturn(Optional.empty());

        assertFalse(userInfoService.validAuthenticationWithGoogle(new GoogleAuthRequest("NotExistEmailAddress")));
    }

    @Test
    void loadUserByEmailAddressExistUser(){
        when(userInfoRepository.findByEmailAddress("testEmailAddress")).thenReturn(Optional.of(userInfo));

        assertEquals(userInfo, userInfoService.loadUserByEmailAddress("testEmailAddress"));
    }

    @Test
    void loadUserByEmailAddressNotExistUser(){
        when(userInfoRepository.findByEmailAddress("notExistEmailAddress")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> {userInfoService.loadUserByEmailAddress("notExistEmailAddress");});
    }
}