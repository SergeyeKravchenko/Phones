package com.example.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository tokenRepository;

    @Configuration
    @Profile({"Mysql", "Test"})
    @Order(1)
    @NoArgsConstructor
    private class Database extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/registration").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()  //login configuration
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("app_username")
                    .passwordParameter("app_password")
                    .defaultSuccessUrl("/book").and()
                    .rememberMe().rememberMeParameter("remember-me")
                    .tokenRepository(tokenRepository)
                    .tokenValiditySeconds(86400)
                    .and().logout()    //logout configuration
                    .logoutUrl("/app-logout")
                    .logoutSuccessUrl("/login")
                    .and().exceptionHandling() //exception handling configuration
//                .accessDeniedHandler(accessDeniedHandler)
                    .accessDeniedPage("/error");
        }
    }

    @Configuration
    @Order(2)
    @NoArgsConstructor
    @Profile("file")
    private class File extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/registration").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()  //login configuration
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("app_username")
                    .passwordParameter("app_password")
                    .defaultSuccessUrl("/book")
                    .and().logout()    //logout configuration
                    .logoutUrl("/app-logout")
                    .logoutSuccessUrl("/login")
                    .and().exceptionHandling()
                    .accessDeniedPage("/error");
        }

    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
        return new PersistentTokenBasedRememberMeServices("remember-me", userDetailsService, tokenRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**");
    }
}
