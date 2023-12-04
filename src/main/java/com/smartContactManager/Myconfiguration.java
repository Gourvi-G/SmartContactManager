package com.smartContactManager;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableWebSecurity
public class Myconfiguration{

	

    
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//    	auth.authenticationProvider(authenticationProvider());
//    }
    
    



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return daoAuthenticationProvider;
    }
    

    
//    @Bean
//    public AuthenticationManagerBuilder configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//        return auth;
//    }

    
    
    
    

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                        
                                 
                              .requestMatchers("/user/**").hasRole("USER")
                              .requestMatchers("/admin/**").hasRole("ADMIN")
                               .requestMatchers("/**").permitAll()
                               .anyRequest().authenticated()
                     
                                
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/signin") // Set your custom login page URL if needed
                                .loginProcessingUrl("/dologin")
                                .defaultSuccessUrl("/user/home2") // Set the default success URL after login
                                //.failureUrl("/Login_fail")
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable());
        http.authenticationProvider(authenticationProvider());
        DefaultSecurityFilterChain build=http.build();
       
        return build;
	 
	
}
}
