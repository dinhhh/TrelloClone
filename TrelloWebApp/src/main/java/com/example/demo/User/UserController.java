package com.example.demo.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.ConfirmationToken.ConfirmationToken;
import com.example.demo.ConfirmationToken.ConfirmationTokenService;

@Controller
public class UserController {
	@Autowired
	private final UserService userService;
	@Autowired
    private final ConfirmationTokenService confirmationTokenService;

	public UserController(UserService userService, ConfirmationTokenService confirmationTokenService) {
		super();
		this.userService = userService;
		this.confirmationTokenService = confirmationTokenService;
	}
    
    @GetMapping("/sign-in")
    public String signin() {
    	return "login";
    }
    
    @RequestMapping(value = "/sign-up", method = RequestMethod.GET)
    public String displaySignUp(Model model) {
    	model.addAttribute("user", new User());
    	return "signup";
    }

    @PostMapping("/sign-up")
    public String signUp(User user, Model model) {

        String result = userService.signUpUser(user);
        model.addAttribute("result", result);
        return "signup";
    }

    @GetMapping("/sign-up/confirm")
    public String confirmMail(@RequestParam("token") String token) {

        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

        optionalConfirmationToken.ifPresent(userService::confirmUser);
        
        return "redirect:/sign-in";
    }
    
    @GetMapping("/forgot")
    public String displayForgotPassword() {
    	return "forgot";
    }
    
    @PostMapping("/forgot")
    public ModelAndView forgotPassword(@RequestParam("email") String email) {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("forgot");
    	if(!userService.checkEmailExist(email)) {
    		mav.addObject("message", "This wasn't an account for that email!");
    	}else {
    		mav.addObject("message", "Check email to change your password!");
    		userService.generateToken4Forgot(email);
    	}
    	return mav;
    }   
    
    @GetMapping("/forgot/confirm")
    public ModelAndView confirmReset(@RequestParam("token") String token) {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("set-new-password");
    	Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
         if(optionalConfirmationToken.isPresent()) {
        	 mav.addObject("token", token);
        	 return mav;
         }else {
        	 mav.addObject("message", "Your token is invalid!!!");
        	 return mav;
         }
    }
    
    @GetMapping("/set-new-password")
    public String displaySetNewPassword() {
    	return "set-new-password";
    }
    
    @PostMapping("/set-new-password")
    public ModelAndView setNewPassword(@RequestParam("password") String password, @RequestParam("token") String token) {
    	ModelAndView mav = new ModelAndView();
    	userService.updatePassword(password, token);
    	mav.setViewName("set-new-password");
    	mav.addObject("message", "Your password is updated!");
    	return mav;
    }
    
}
