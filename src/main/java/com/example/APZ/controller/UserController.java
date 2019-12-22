package com.example.APZ.controller;

import com.example.APZ.domain.Role;
import com.example.APZ.domain.User;
import com.example.APZ.repos.UserRepo;
import com.example.APZ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

  /*  @GetMapping("/user")
    public String findUser(@RequestParam(required = false, defaultValue = "") String filter1, Model model){
        User users = (User) userRepo.findAll();
        users = (User) userRepo.findAll();
        if(filter1 != null && !filter1.isEmpty()) {
            users = userRepo.findByUsername(filter1);
        }
        else {
            users = (User) userRepo.findAll();
        }

        model.addAttribute("users", users);
        model.addAttribute("filter", filter1);

        return "user";
    }
*/
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ){

        User userFromDb = userRepo.findByUsername(username);

        if(userFromDb != null){
            form.put("usernameError", "This username already exists");
            return "redirect:/user";
        }

        userService.saveUser(user, username, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String username,
            Model model
    ){

        User userFromDb = userRepo.findByUsername(username);

        if(userFromDb != null){
            model.addAttribute("usernameError", "This username already exists");
            return "redirect:/user/profile";
        }

        if(email.isEmpty()){
            model.addAttribute("emailError", "This email already exists");
            return "redirect:/user/profile";
        }

        userService.updateProfile(user, password, email, username);

        return "redirect:/user/profile";
    }

}
