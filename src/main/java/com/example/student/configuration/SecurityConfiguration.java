package com.example.student.configuration;

import com.example.student.service.DatabaseUserDetailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private DatabaseUserDetailService userDetailService;

    public SecurityConfiguration(DatabaseUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }
//
//    @Override
//    public void configure(HttpSecurity http)throws Exception{
//        http.authorizeRequests()
//                .antMatchers("/student/all").permitAll()
//                .anyRequest().authenticated();
//    }

    @Override
    public void configure(WebSecurity web)throws Exception{
        web.ignoring().antMatchers("/student/all");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
