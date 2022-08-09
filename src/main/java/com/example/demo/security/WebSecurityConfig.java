package com.example.demo.security;

import com.example.demo.security.OAuth.CustomOAuth2UserService;
import com.example.demo.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/sign-up/*", "/sign-in").permitAll()
                .antMatchers("/oauth2/*").permitAll()
                .antMatchers("/board/*").authenticated()
                .antMatchers("/home").authenticated()
                .antMatchers("/login_sucess").authenticated()
                .antMatchers("/profile").authenticated()
                .and()
                .formLogin()
                .loginPage("/sign-in")
                .usernameParameter("user")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/sign-in")
                .and()
                .oauth2Login()
                .loginPage("/sign-in")

        ;
    }
}























