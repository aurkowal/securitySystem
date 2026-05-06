package pl.coderslab.securitysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("password"))
                .roles("USER", "ADMIN")
                .build();

        UserDetails moderator = User.builder()
                .username("moderator")
                .password(encoder.encode("password"))
                .roles("MODERATOR", "USER")
                .build();

        UserDetails superAdmin = User.builder()
                .username("superadmin")
                .password(encoder.encode("password"))
                .roles("SUPERADMIN")
                .build();


        return new InMemoryUserDetailsManager(user, moderator, admin, superAdmin);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/admins").hasRole("SUPERADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/moderator/**").hasRole("MODERATOR")
                        .requestMatchers("/info/**").authenticated()
                        .requestMatchers("/all").permitAll()
                        .requestMatchers("/restricted").authenticated()
                        .requestMatchers("/post/view").permitAll()
                        .requestMatchers("/post/update").hasRole("USER")
                        .requestMatchers("/post/remove").hasAnyRole("MODERATOR", "ADMIN")
                        .anyRequest().permitAll()

                );
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }



}
