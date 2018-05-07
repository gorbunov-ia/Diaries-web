package ru.gorbunov.diaries.config;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
public class WebSecurityConfig {

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
     * Config API end-points.
     */
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // todo: remember me
            http
                .httpBasic()
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/**", "/logout").authenticated()
                .and()
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        }
    }

    /**
     * Config another end-points.
     */
    @Configuration
    public static class WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .anyRequest().permitAll();
        }
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
     * Bean for get password hash.
     *
     * @return bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
