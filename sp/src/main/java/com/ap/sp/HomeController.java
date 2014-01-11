package com.ap.sp;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public User test(Locale locale, Model model) {
		logger.info("showing user");
		
		User u = new User();
		u.username = "Alberto";
		u.password = "Plebani";
		
		return u;
	}
	
	@RequestMapping(value = "/livello/albi", method = RequestMethod.GET)
	public String albi() {
		
		return "albi";
	}
	
	@RequestMapping(value = "/livello/albiconhtml.html", method = RequestMethod.GET)
	public String albiconhtml() {
		
		return "albi";
	}
	
	@ResponseBody
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public ArrayList<User> userlist(Locale locale, Model model) {
		logger.info("showing user");
		
		ArrayList<User> list = new ArrayList<User>();
		
		for (int index = 0; index < 100; index++) {
			User u = new User();
			u.username = "Alberto_" + index;
			u.password = "Plebani_" + index;
			list.add(u);
		}
		
		
		return list;
	}
	
	@ResponseBody
    @RequestMapping(value="/crea", method=RequestMethod.GET)
    public Iterable<User> crea() {
    	
    	// save a couple of customers
        userRepository.save(new User("Jack", "Bauer"));
        userRepository.save(new User("Chloe", "O'Brian"));
        userRepository.save(new User("Kim", "Bauer"));
        userRepository.save(new User("David", "Palmer"));
        userRepository.save(new User("Michelle", "Dessler"));
    	
        Iterable<User> users = userRepository.findAll();
        
        return users;
    }
	
	@ResponseBody
    @RequestMapping(value="/getUser/{username}", method=RequestMethod.GET)
    public User getUser(@PathVariable String username) {
    	
    	List<User> list = userRepository.findByUsername(username);
        
        return list.get(0);
    }
	
	@ResponseBody
    @RequestMapping(value="/getUser", method=RequestMethod.GET)
    public User getUserWithReqParam(@RequestParam(value="username") String username) {
    	
    	List<User> list = userRepository.findByUsername(username);
        
        return list.get(0);
    }
	
	@ResponseBody
    @RequestMapping(value="/getAllUsers", method=RequestMethod.GET)
    public List<User> getAllUsers(@RequestParam(value="iter") boolean iter) {
    	
		List<User> list = new ArrayList<User>();
		
		if (iter) {
			
			Iterator<User> iterator = (userRepository.findAll()).iterator();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
			
		} else {
			
			logger.info("Ottengo lista con findAll modificato");
			list = userRepository.findAll();
			
		}
		
		
        
        return list;
    }
	
	@ResponseBody
    @RequestMapping(value="/modifyUser/{username}", method=RequestMethod.GET)
    public User modifyUser(@PathVariable String username) {
    	
    	List<User> list = userRepository.findByUsername(username);
        
    	User user = list.get(0);
    	
    	user.username = "VecchioJack";
    	
    	userRepository.save(user);
    	
        return list.get(0);
    }
	
	@ResponseBody
    @RequestMapping(value="/user29", method=RequestMethod.GET)
    public User user29() {
    	
    	User user = userRepository.findOne(29);
        
    	user.username = "VecchioDavid";
    	
    	userRepository.save(user);
    	
        return user;
    }
	
	@ResponseBody
    @RequestMapping(value="/newuser29", method=RequestMethod.GET)
    public User newuser29() {
    	
    	User user = new User();
        
    	user.username = "un29generatoacaso";
    	user.password ="acaso";
    	user.setId(29);
    	
    	userRepository.save(user);
    	
        return user;
    }
	
	@ResponseBody
    @RequestMapping(value="/getuser10", method=RequestMethod.GET)
    public User getuser10() {
    	
    	User user = userService.getUserWithId10();
    	
        return user;
    }
	
    @RequestMapping(value="/page", method=RequestMethod.GET)
    public String page() {
    	
        return "altro/index";
        
    }
	
}
