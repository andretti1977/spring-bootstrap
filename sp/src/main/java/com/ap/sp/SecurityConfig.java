package com.ap.sp;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.AbstractSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true) //serve per utilizzare i preauth
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/*
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
    	logger.info("AUTHENTICATION");
    	
        auth
            .inMemoryAuthentication()
                .withUser("user").password("user").roles("USER")
                .and()
        		.withUser("admin").password("admin").roles("ADMIN");
    }

    */
    
	
	@Autowired
	private DataSource dataSource;
	
	/*
	 * Questo va messo come autowired perchè non è vero che fa override
	 */
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth
	        .jdbcAuthentication()
	            .dataSource(dataSource)
	            .usersByUsernameQuery("SELECT username, password, enabled FROM auth_users WHERE username = ?")
	            .authoritiesByUsernameQuery("SELECT username, authority FROM auth_authorities WHERE username = ?");
	}
	
	/*
	
	 @Autowired
	 public void registerGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth
	        .jdbcAuthentication()
	            .dataSource(dataSource)
	            .usersByUsernameQuery("SELECT username, password, enabled FROM auth_users WHERE username = ?")
	            .authoritiesByUsernameQuery("SELECT username, authority FROM auth_authorities WHERE username = ?");
	}
	*/
	/*
	 * Questo fa override e configura l'http. Se metot questo alla fine tutte le request sono accettate e conta solo il login
	 * fatto col metodo di login
	 */
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
	    http
	    	.authorizeRequests()
	    	.anyRequest()
	    	.permitAll();
	}
	
	 
}