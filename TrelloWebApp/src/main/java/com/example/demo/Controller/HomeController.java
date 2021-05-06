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
		return "welcome";
	}
	
//	@GetMapping("/signup")
//	public String signUp(Model model) {
//		model.addAttribute("user", new User());
//		return "signup";
//	}
	
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
	
//	@PostMapping("/process_signup")
//	public String process(User user) {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	    String encodedPassword = passwordEncoder.encode(user.getPassword());
//	    user.setPassword(encodedPassword);
//	     
//	    userRepo.save(user);
//	     
//	    return "register_success";
//	}
	
	@GetMapping("/login_sucess")
	public String loginSucess() {
		return "login_sucess";
	}
	
	@GetMapping("/home")
	public String goToHome() {
		return "home";
	}
	
	@GetMapping("/updateInfo")
	public String goToProfile() {
		return "profile";
	}
	
	@GetMapping("/board")
	public String getBoard() {
		return "board";
	}
	
}
