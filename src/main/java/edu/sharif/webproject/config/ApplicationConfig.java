package edu.sharif.webproject.config;

import com.google.gson.Gson;
import edu.sharif.webproject.config.security.AdminProperties;
import edu.sharif.webproject.enduser.EndUserRepository;
import edu.sharif.webproject.security.UsersAuthManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    private final EndUserRepository endUserRepository;
    private final AdminProperties adminProperties;

    @Autowired
    public ApplicationConfig(EndUserRepository endUserRepository, AdminProperties adminProperties) {
        this.endUserRepository = endUserRepository;
        this.adminProperties = adminProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(endUserRepository, passwordEncoder));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfig)
            throws Exception {
        return authenticationConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(EndUserRepository endUserRepository, PasswordEncoder passwordEncoder) {
        UsersAuthManagementService userDetailsService = new UsersAuthManagementService(endUserRepository, passwordEncoder);
        userDetailsService.save(adminProperties.getEndUser());
        return userDetailsService;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

}
