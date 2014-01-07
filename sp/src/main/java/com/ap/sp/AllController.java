package com.ap.sp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */


@Controller
public class AllController {
	
	private static final Logger logger = LoggerFactory.getLogger(AllController.class);
	
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
	@RequestMapping(value="/all/uno", method = RequestMethod.GET)
	public String uno() {
		
		return "uno";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/all/due", method = RequestMethod.GET)
	public String due() {
		
		return "due";
	}
	
	
}
