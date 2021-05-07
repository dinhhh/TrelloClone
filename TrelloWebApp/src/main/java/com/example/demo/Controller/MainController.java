package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.User.User;

@Controller
public class MainController {
	@GetMapping("")
	public String viewHome() {
		return "welcome";
	}
	
	@GetMapping("/login_sucess")
	public String loginSucess() {
		return "login_sucess";
	}
	
	@GetMapping("/sign-up")
	public String signUp(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	
	@GetMapping("/home")
	public String goToHome() {
		System.out.println("get mapping /home");
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
	
	@GetMapping("testapi")
	public String testAPI() {
		return "test";
	}
	
	
	
}
