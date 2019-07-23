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
    @Value("${app.user.name}")
    private String userName;

    @Value("${app.user.password}")
    private String userPassword;

    @Value("${app.admin.name}")
    private String adminName;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        String[] allowedPaths = {"/check-quality"};

        http
                .requiresChannel().anyRequest().requiresSecure()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(allowedPaths).authenticated()
                .antMatchers("/actuator/**").hasRole("ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser(userName)
                .password("{noop}" + userPassword)
                .roles("USER")
                .and()
                .withUser(adminName)
                .password("{noop}" + adminPassword)
                .roles("ADMIN");
    }
}