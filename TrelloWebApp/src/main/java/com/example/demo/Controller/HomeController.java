package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;

@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String viewHome() {
		return "home";
	}
	
	@GetMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@GetMapping("/signin")
	public String login() {
		return "login";
	}
	
	@PostMapping("/process_signup")
	public String process(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
	     
	    userRepo.save(user);
	     
	    return "register_success";
	}
	
}
