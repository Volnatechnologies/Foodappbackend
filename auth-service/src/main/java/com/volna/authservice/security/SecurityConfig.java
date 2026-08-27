package com.volna.authservice.security;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
  http.csrf(c->c.disable())
          .authorizeHttpRequests(a
                  ->a.requestMatchers("/api/v1/auth/**",
                  "/actuator/health","/actuator/info").permitAll()
                  .anyRequest().authenticated());
  return http.build();
 }
}