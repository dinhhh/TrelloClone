package com.example.demo.controller;

import com.example.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
    @GetMapping("")
    public String viewHome() {
        return "welcome";
    }

    @GetMapping("/welcome")
    public String viewHome2() {
        return "welcome";
    }

    @GetMapping("/profile")
    public String viewProfile() {
        return "profile";
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

    @GetMapping("/board/home")
    public String reGoToHome() {
        return "redirect:/home";
    }

    @GetMapping("/profile/home")
    public String reGoToHome2() {
        return "redirect:/home";
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

    @GetMapping("/board/{id}")
    public String goToBoard(@PathVariable String id) {
        return "board";
    }

}
