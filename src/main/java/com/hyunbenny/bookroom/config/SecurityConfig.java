package com.hyunbenny.bookroom.config;


import com.hyunbenny.bookroom.auth.CustomLoginFailureHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLoginFailureHandler customLoginFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                .authorizeRequests()
                .antMatchers("/", "/login", "/join").permitAll()
                .antMatchers("/api/user/v1/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("userId")
                .successForwardUrl("/")
                .failureHandler(customLoginFailureHandler)

                .and()
                .logout()
                .logoutSuccessUrl("/")

                .and()
                .csrf().disable()

                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

//    CustomUserDetailsService 클래스를 따로 만들어서 주석 처리함.
//    @Bean
//    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
//        return userId -> {
//            log.info("userId: {}", userId);
//            UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
//            CustomUserDetails customUserDetails = new CustomUserDetails(UserAccountDto.from(userAccount));
//            return customUserDetails;
//        };
//    }
}
