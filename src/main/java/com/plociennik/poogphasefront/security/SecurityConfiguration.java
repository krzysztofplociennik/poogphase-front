package com.plociennik.poogphasefront.security;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApiClient apiClient;
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";
    private InMemoryUserDetailsManager memoryUserDetailsManager = new InMemoryUserDetailsManager();

    public InMemoryUserDetailsManager getMemoryUserDetailsManager() {
        return memoryUserDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and().authorizeRequests()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                .anyRequest().authenticated()

                .and().formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        for (UserDto userDto : apiClient.getUsers()) {
            if (userDto.getUsername().equals("admin")) {
                UserDetails admin = User.withDefaultPasswordEncoder()
                        .username(userDto.getUsername())
                        .password(userDto.getPassword())
                        .roles("ADMIN")
                        .build();
                memoryUserDetailsManager.createUser(admin);
            } else {
                UserDetails user = User.withDefaultPasswordEncoder()
                        .username(userDto.getUsername())
                        .password(userDto.getPassword())
                        .roles("USER")
                        .build();
                memoryUserDetailsManager.createUser(user);
            }
        }
        return memoryUserDetailsManager;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",
                "/icons/**",
                "/images/**",
                "/styles/**",
                "/h2-console/**");
    }
}
