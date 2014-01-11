package com.ap.sp;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ap.sp.SecResponse.Roles;


@Controller
public class SecurityController {

	private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public SecResponse handleCustomException(AccessDeniedException ex) {
 
		logger.error("eccezione: " + ex.getMessage());
		SecResponse resp = new SecResponse();
		resp.status = "ERROR";
		return resp;
 
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	@RequestMapping(value = "/sec/admin", method = RequestMethod.GET)
	public SecResponse secAdmin(HttpSession session) {
		
		SecResponse resp = new SecResponse();
		resp.role = Roles.ADMIN;
		
		return resp;
	}
	
	@PreAuthorize("hasRole('USER')")
	@ResponseBody
	@RequestMapping(value = "/sec/user", method = RequestMethod.GET)
	public SecResponse secUser(HttpSession session) {
		
		SecResponse resp = new SecResponse();
		resp.role = Roles.USER;
		
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sec/general", method = RequestMethod.GET)
	public SecResponse secGeneral(HttpSession session) {
		
		SecResponse resp = new SecResponse();
		resp.status = "va bene un ruolo qualsiasi";
		
		
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sec/logout", method = RequestMethod.GET)
	public SecResponse secLogout(HttpSession session) {
		
		session.invalidate();
		SecurityContextHolder.clearContext();
		
		SecResponse resp = new SecResponse();
		resp.status = "" + SecurityContextHolder.getContext().getAuthentication();
		
		
		return resp;
	}

	@Autowired
	SecurityConfig sc;
	
	@ResponseBody
	@RequestMapping(value = "/sec/login", method = RequestMethod.GET)
	public SecResponse secLogin(HttpSession session, @RequestParam("u") String username, @RequestParam("p") String password) {
		
		
		try {
			Authentication token = new UsernamePasswordAuthenticationToken(username, password);
			
            Authentication authentication = sc.authenticationManagerBean().authenticate(token);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        logger.info("loggato");
	        
	    } catch(AuthenticationException e) {
	        logger.error("Authentication failed: " + e.getMessage());
	        e.printStackTrace();
	        session.invalidate();
			SecurityContextHolder.clearContext();
	    } catch (Exception e) {
	    	logger.error("Authentication failed 2: " + e.getMessage());
	        e.printStackTrace();
	        session.invalidate();
			SecurityContextHolder.clearContext();
		}

		logger.info("Security context contains: " + SecurityContextHolder.getContext().getAuthentication());
		
		SecResponse resp = new SecResponse();
		resp.status = "" + SecurityContextHolder.getContext().getAuthentication();
		
		
		return resp;
	}
	
}
