package com.maxcheung.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration()
@Profile("testing")
@Order(99)
public class TestWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Set up a valid user for REST requests.
        // We do this as we do not want to authenticate as an LDAP user when performing integration tests.
        auth.inMemoryAuthentication()
            .withUser("user")
            .password("test")
            .authorities("Treasury(Clearing)");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/resources/**").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/img/**").permitAll()
            .anyRequest().hasAuthority("Treasury(Clearing)")
            .and().formLogin().loginPage("/login.html").permitAll()
            .and().logout().permitAll()
            .and().httpBasic()
            .and().csrf().disable();
    }
}