package com.ELOUSTA.ELOUSTA.backend.config;


import com.ELOUSTA.ELOUSTA.backend.service.auth.AdminAuthenticationService;

import com.ELOUSTA.ELOUSTA.backend.service.auth.filter.JwtAuthFilter;
import com.ELOUSTA.ELOUSTA.backend.service.auth.ClientAuthenticationService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.TechnicianAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;

    @Bean

    public UserDetailsService clientDetailsService(){

        return new ClientAuthenticationService();
    }
    @Bean
    public UserDetailsService technicianDetailsService(){
        return new TechnicianAuthenticationService();
    }

    @Bean
    public UserDetailsService adminDetailsService(){
        return new AdminAuthenticationService();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(

                                "/client/signUp", "/client/signIn",
                                "/tech/signUp", "/tech/signIn",
                                "/admin/signIn",
                                "/admin/signUp",
                                "/client/signIn/google", "/tech/signIn/google",
                                "/client/resetPassword", "/tech/resetPassword",
                                "/client/fetchUser", "/tech/fetchTch",
                                "/elousta-websocket/**", "/mail/**", "/twilio-otp/**",
                                "/client/home/",
                                "/client/request/addRequest",
                                "/admin/profession/addprofession/**",
                                "/elousta-websocket/**","/admin/**").permitAll()
                        .requestMatchers("/auth/client/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/auth/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Make the session stateless as we make per request token
                .authenticationManager(customAuthenticationManager(http))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // password encoding
    }

    @Bean
    public AuthenticationProvider userAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(clientDetailsService());


        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationProvider technicianAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(technicianDetailsService());

        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean

    public AuthenticationProvider adminAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(adminDetailsService());

        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean

    public AuthenticationManager customAuthenticationManager(HttpSecurity http) throws Exception {
        return new AuthenticationManager() {
            // Override the authentication method to use the correct authentication provider.
            // We differentiate between two types of services: one for users and one for technicians,
            // so we ensure the appropriate provider is used based on the service type.

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String requestURI = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest().getRequestURI();

                // If the URL is related to a user, use UserService AuthenticationProvider
                if (requestURI.startsWith("/client")) {
                    return userAuthenticationProvider().authenticate(authentication);
                }

                // If the URL is related to technician, use TechnicianService AuthenticationProvider
                if (requestURI.startsWith("/tech")) {
                    return technicianAuthenticationProvider().authenticate(authentication);
                }


                // If the URL is related to admin, use AdminService AuthenticationProvider
                if(requestURI.startsWith("/admin")){
                    return adminAuthenticationProvider().authenticate(authentication);
                }

                // Default: Use UserInfo if no match
                return userAuthenticationProvider().authenticate(authentication);
            }
        };
    }
}
