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
//        return "redirect:/sign-in";
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("redirect:/sign-up");
//        mav.addObject("result", result);
        model.addAttribute("result", result);
        return "signup";
    }

    @GetMapping("/sign-up/confirm")
    public String confirmMail(@RequestParam("token") String token) {

        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

        optionalConfirmationToken.ifPresent(userService::confirmUser);
        
        return "redirect:/sign-in";
    }
}
