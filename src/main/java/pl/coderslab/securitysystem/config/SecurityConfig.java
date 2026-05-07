package pl.coderslab.securitysystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pl.coderslab.securitysystem.jwt.JwtAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] ALLOWED_LIST_URL = {"/api/v1/auth/**", "/about"};


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(ALLOWED_LIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }
}


//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password(encoder.encode("password"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("password"))
//                .roles("USER", "ADMIN")
//                .build();
//
//        UserDetails moderator = User.builder()
//                .username("moderator")
//                .password(encoder.encode("password"))
//                .roles("MODERATOR", "USER")
//                .build();
//
//        UserDetails superAdmin = User.builder()
//                .username("superadmin")
//                .password(encoder.encode("password"))
//                .roles("SUPERADMIN")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(user, moderator, admin, superAdmin);
//    }

//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/user/admins").hasRole("SUPERADMIN")
//                        .requestMatchers("/user/**").hasRole("USER")
//                        .requestMatchers("/moderator/**").hasRole("MODERATOR")
//                        .requestMatchers("/info/**").authenticated()
//                        .requestMatchers("/all").permitAll()
//                        .requestMatchers("/restricted").authenticated()
//                        .requestMatchers("/post/view").permitAll()
//                        .requestMatchers("/post/update").hasRole("USER")
//                        .requestMatchers("/post/remove").hasAnyRole("MODERATOR", "ADMIN")
//                        .anyRequest().permitAll()
//
//                );
//        http.formLogin(Customizer.withDefaults());
//        return http.build();
//    }


