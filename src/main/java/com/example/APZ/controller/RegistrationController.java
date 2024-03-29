package com.example.APZ.controller;

import com.example.APZ.domain.User;
import com.example.APZ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if(user.getPassword() != null && !user.getPassword().equals(user.getPassword2())){
            bindingResult.addError(new FieldError("user", "password", "Passwords are different!"));
            return "registration";
        }

        if(bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userService.checkEmail(user)) {
            model.addAttribute("emailError", "This email already exists");
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "This username already exists");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if(isActivated){
            model.addAttribute("message", "User successfully activated");
        } else{
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
