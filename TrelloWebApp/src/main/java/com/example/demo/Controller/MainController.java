package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	@GetMapping("testapi")
	public String testAPI() {
		return "test";
	}
	
}
