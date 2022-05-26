package com.example.testsystem.config;

import com.example.testsystem.component.JwtFilter;
import com.example.testsystem.entity.enums.AuthorityType;
import com.example.testsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthService authService;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/user/*","/api/test/*").hasAnyAuthority(AuthorityType.STUDENT.name(),AuthorityType.ADMIN.name(),AuthorityType.TEACHER.name())
//                .antMatchers(HttpMethod.POST,"/api/test/check").hasAnyAuthority(AuthorityType.STUDENT.name(),AuthorityType.ADMIN.name(),AuthorityType.TEACHER.name())
//                .antMatchers(HttpMethod.PUT,"/api/user/*").hasAnyAuthority(AuthorityType.STUDENT.name(),AuthorityType.ADMIN.name(),AuthorityType.TEACHER.name())
//                .antMatchers(HttpMethod.POST,"/api/**").hasAnyAuthority(AuthorityType.TEACHER.name(),AuthorityType.ADMIN.name())
//                .antMatchers(HttpMethod.DELETE,"/api/**").hasAuthority(AuthorityType.ADMIN.name())
                .anyRequest()
                .authenticated();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
