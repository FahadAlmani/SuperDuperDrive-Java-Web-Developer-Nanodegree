package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignup(UserModel userModel){
        return "signup";

    }
    @PostMapping
    public String postSignup(UserModel userModel, Model model){
        if(!this.userService.isAvailableUsername(userModel.getUsername())){
            model.addAttribute("errorMessage", "The user Name is already exist");
            return "signup";
        }
        int numberOfRowsAdded = userService.createUser(userModel);
        if(numberOfRowsAdded != 1){
            model.addAttribute("errorMessage", "Something went wrong, please try again later");
            return "signup";
        }
        model.addAttribute("isSignupSuccess", true);
        return "redirect:/login";

    }
}
