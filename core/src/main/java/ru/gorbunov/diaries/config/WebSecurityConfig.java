package ru.gorbunov.diaries.config;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.annotation.PostConstruct;

/**
 * Configuration for Spring Security.
 *
 * @author Gorbunov.ia
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * {@link org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder}.
     */
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * Implementation of service for interaction with User Details.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Base constructor.
     *
     * @param userDetailsService           service for interaction with user details
     * @param authenticationManagerBuilder spring authentication manager builder
     */
    public WebSecurityConfig(final UserDetailsService userDetailsService,
                             final AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userDetailsService = userDetailsService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and()
                .authorizeRequests()
                .antMatchers("/index.html", "/", "/home", "/login", "/*.bundle.*").permitAll()
                .anyRequest().authenticated()
            .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    /**
     * Bean for get password hash.
     *
     * @return bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
