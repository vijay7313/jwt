package com.jwt.configure.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.configure.security.filter.JWTAuthFilter;
import com.jwt.service.impl.UserDetailService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JWTAuthFilter authFilter;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		   http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(
	        		authorizeRequests -> 
	        		authorizeRequests
	        		.requestMatchers("/createUser","/authenticate").permitAll()
	        		.anyRequest().
	        		authenticated()
	        		)
	        .httpBasic(withDefaults())
	        .sessionManagement(
	        		sessionManagement -> 
	            sessionManagement.
	            sessionCreationPolicy(
	            		SessionCreationPolicy.STATELESS)
	            )
	        .authenticationProvider(
	        		authenticationProvider()
	        		)
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		   return http.build();
		
	}
	@Bean
	public UserDetailsService users() {
		
		return new UserDetailService();
		
	}
	
	
	 @Bean
	    public AuthenticationProvider authenticationProvider()
	 {
	        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(users());
	        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
	        
	        return authenticationProvider;
	    }
	  @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	  
	  
}
