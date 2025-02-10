package com.final_project.ua_team_final_project.config;

import com.final_project.ua_team_final_project.services.CustomUserDetailsService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    private final DataSource dataSource;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(DataSource dataSource, CustomUserDetailsService customUserDetailsService) {
        this.dataSource = dataSource;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/about", "/js/**", "/css/**", "/images/**").permitAll()
                        .requestMatchers("/organization/**", "/selectedProducts").authenticated()
                        .requestMatchers("/organization/admin/**").hasRole("ADMIN")
                        .requestMatchers("/organization/userpage","/organization/**",  "/organization/editProducts", "/organization/confirmOrder").hasRole("USER")
                        .requestMatchers("/organization/pageofhead/**").hasRole("HEAD")
                        .requestMatchers("/organization/pageoffinco/**").hasRole("FINCO")
                        .requestMatchers("/organization/supply/**").hasRole("SUPPLIER")
                        .requestMatchers("/api/products/import").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("remember-me")
                        .key(Dotenv.load().get("REMEMBERME_KEY"))
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                        .tokenRepository(persistentTokenRepository())
                )
                .userDetailsService(customUserDetailsService);

        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/api/products/import"));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcTokenRepositoryImpl persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}