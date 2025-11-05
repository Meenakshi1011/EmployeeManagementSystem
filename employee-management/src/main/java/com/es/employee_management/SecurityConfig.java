package com.es.employee_management;

import com.es.employee_management.repository.EmployeeRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http  .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.httpBasic(withDefaults());
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(EmployeeRepo employeeRepo) {
        return username -> employeeRepo.findByEmailId(username)
                .map(employee -> org.springframework.security.core.userdetails.User
                        .withUsername(employee.getEmailId())
                        .password(employee.getPassword())
                        .roles(employee.getRole()) // Example: "ADMIN" or "EMPLOYEE"
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
//    @Bean
//    CommandLineRunner testPassword(PasswordEncoder encoder) {
//        return args -> {
//            System.out.println("Password matches: " +
//                    encoder.matches("admin123", "$2a$10$GybGfBrL4bLDF8uPkHc/euhN4gMeXU85rD2LbfY6eD0oPHWnYyRSG"));
//        };
//    }
//the above to check the password matches

//bycrypt password generator
//    @Bean
//    CommandLineRunner run() {
//        return args -> {
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            String encoded = encoder.encode("admin123");
//            System.out.println("BCrypt hash: " + encoded);
//        };
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
