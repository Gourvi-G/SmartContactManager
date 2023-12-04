package com.smartContactManager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;


@Controller
public class SmartContactController {
	@Autowired
	private BCryptPasswordEncoder BcryptPasswordEncoder;
	
     @Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home() {
		
		return "home";
	}
	
	
	@RequestMapping("/about")
	public String about(Model m) {
		m.addAttribute("title","About-Smart Contact Manager");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String SignUp(Model m) {
		m.addAttribute("title","Register-Smart Contact Manager");
		m.addAttribute("user",new User());
		return "signup";
	}
	
	//This handler for registering user
	@RequestMapping(value="/do_register" , method= RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult bresult,
			@RequestParam(value="agreement", defaultValue="false") boolean agreement, Model m, HttpSession session) {
		
		try {
		
			if(!agreement) {
				System.out.println("You Have not agreed the terms and condition");
				throw new Exception("You Have not agreed the terms and condition");
			}
			
			if(bresult.hasErrors()) {
				System.out.println("ERROR"+ bresult.toString());
				m.addAttribute("user",user);
				return "signup";
			}
			
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("/image/phone-message.png");
			user.setPassword(BcryptPasswordEncoder.encode(user.getPassword()));
			System.out.println(agreement);
			System.out.println(user);
			User result=this.userRepository.save(user);	
			
			m.addAttribute("user",new User());
			m.addAttribute("session",session);
			session.setAttribute("message",new Message("Successfully Registered " , "alert-success"));
			return "signup";
			
		}catch(Exception e){
			e.printStackTrace();
			m.addAttribute("user",user);
			session.setAttribute("message",new Message("Something went wrong " +e.getMessage(), "alert-danger"));
			return "signup";
		}
		
	}
	@RequestMapping("/signin")
	public String loginPage(Model m) {
		m.addAttribute("title","This is loginPage");
		return "login.html";
	}
	
}
