package com.widget.quality.control.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Value("${app.user}")
    private String user;

    @Value("${app.password}")
    private String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        String[] allowedPaths = {"/check-quality"};

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(allowedPaths).authenticated()
                .antMatchers("/**").denyAll()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser(user)
                .password("{noop}" + password)
                .roles("USER");
    }
}