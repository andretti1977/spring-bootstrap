package com.ap.sp;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


//qui posso definire una lista di oggetti da "sessione", se solo uno basta "" in caso di più serve {})
@SessionAttributes({"stored", "user"})
@Controller
public class SessionController {

	//Attenzione che forse i SessionAttributes non valgono tra controller diversi
	
	
	private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
	
	//questo lo prepopola solo se l'oggetto è vuoto
	@ModelAttribute("stored") 
	public String initSessionObject() {
		logger.info("Setting session object");
		return "";
	}
	@ModelAttribute("user") 
	public User initUserObject() {
		logger.info("Setting user session object");
		return new User();
	}

	
	/*
	 * Stringa
	 */
	
	
	@ResponseBody
	@RequestMapping(value = "/session/read", method = RequestMethod.GET)
	public String sessionRead(@ModelAttribute("stored") String inSessionString) {
		logger.info("reading session: #" + inSessionString + "#");
		
		return inSessionString;
	}
	
	//IMPORTANTE: devo passare anche il riferimento a Model perchè quando lo istanzion con ModelAttribute, ottengo solo un riferimento
	//all'oggetto: nel caso di una stringa ad esempio se gli cambio valore ma poi non lo risetto in ram è come aver chiesto oggetto
	//a session.getAttr... ma non averne rifatto il set dopo. Se è un oggetto "custom" e ne modifico alcune proprietà allora no probs
	@ResponseBody
	@RequestMapping(value = "/session/write", method = RequestMethod.GET)
	public String sessionWrite(@RequestParam String msg, @ModelAttribute("stored") String stored, Model model) {
		logger.info("writing session and stored was: #" + stored + "#");
		
		stored = msg;
		model.addAttribute("stored", stored);
		
		return "Impostato " + msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "/session/clear", method = RequestMethod.GET)
	public String sessionClear(SessionStatus status, @ModelAttribute("stored") String msg) {
		logger.info("writing session");
		
		//avendo specificato l'oggetto stored, rimuovo solo quello
		status.setComplete();
		
		return "Rimosso";
	}
	
	/*
	 * Utente
	 */
	
	
	@ResponseBody
	@RequestMapping(value = "/session/read_user", method = RequestMethod.GET)
	public User sessionReadUSer(@ModelAttribute("user") User user) {
		logger.info("ollw");
		return user;
	}
	
	@ResponseBody
	@RequestMapping(value = "/session/write_user", method = RequestMethod.GET)
	public User sessionWriteUser(@ModelAttribute("user") User user) {
		user.username +="+";
		return user;
	}
	
	@ResponseBody
	@RequestMapping(value = "/session/clear_user", method = RequestMethod.GET)
	public String sessionClearUser(SessionStatus status, @ModelAttribute("user") User user) {
		
		//avendo specificato l'oggetto stored, rimuovo solo quello
		status.setComplete();
		
		return "Rimosso";
	}
	
	@RequestMapping(value = "/session/jsp", method = RequestMethod.GET)
	public String sessionJsp() {
		
		return "session";
	}
	
	@ResponseBody
	@RequestMapping(value = "/session/write_classic", method = RequestMethod.GET)
	public String sessionWriteClassic(HttpSession session, @RequestParam("msg") String msg) {
		
		session.setAttribute("classic", msg);
		
		return "Rimosso";
	}
	
	@ResponseBody
	@RequestMapping(value = "/session/read_classic", method = RequestMethod.GET)
	public String sessionReadClassic(HttpSession session) {
		
		String inSession = (String)session.getAttribute("classic");
		
		return "in sessione: " + inSession;
	}
	
	@ResponseBody
	@RequestMapping(value = "/session/clear_classic", method = RequestMethod.GET)
	public String sessionClearClassic(HttpSession session) {
		
		session.removeAttribute("classic");
		
		return "Rimosso";
	}
	
}
