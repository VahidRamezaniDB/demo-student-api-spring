package com.example.student.configuration;

import com.example.student.service.DatabaseUserDetailService;
import org.hibernate.dialect.Database;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private DatabaseUserDetailService userDetailService;

    public SecurityConfiguration(DatabaseUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    protected void
}
