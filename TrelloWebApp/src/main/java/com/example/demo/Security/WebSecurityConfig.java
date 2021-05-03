package com.example.demo.Security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.Security.OAuth.CustomOAuth2User;
import com.example.demo.Security.OAuth.CustomOAuth2UserService;
import com.example.demo.User.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private	CustomOAuth2UserService customOAuth2UserService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
//	public WebSecurityConfig(CustomOAuth2UserService customOAuth2UserService, UserService userService) {
//		super();
//		this.customOAuth2UserService = customOAuth2UserService;
//		this.userService = userService;
//	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new CustomUserDetailService();
//		return new UserService();
//	}
	
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService());
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return authProvider;
//	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.authenticationProvider(authenticationProvider());
//	}
	
//	@Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService)
//                .passwordEncoder(passwordEncoder());
//    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth
                    .userDetailsService(userService)
                    .passwordEncoder(bCryptPasswordEncoder);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().and().csrf().disable()
			.authorizeRequests()
			.antMatchers("/sign-up/*", "/sign-in").permitAll()
			.antMatchers("/oauth2/*").permitAll()
			.antMatchers("/boards/*").authenticated()
			.antMatchers("/login_sucess").authenticated()
			.and()
			.formLogin()
				.loginPage("/sign-in")
				.usernameParameter("user")
				.passwordParameter("password")
				.defaultSuccessUrl("/login_sucess")
				.permitAll()
			.and()
			.logout()
			.logoutSuccessUrl("/sign-in")
			.and()
			.oauth2Login()
				.loginPage("/sign-in")
//				.userInfoEndpoint()
//				.userService(customOAuth2UserService)
					
			;
	}
}























