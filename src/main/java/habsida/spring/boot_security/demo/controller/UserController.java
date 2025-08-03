package habsida.spring.boot_security.demo.controller;


import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "list";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.saveUser(user);
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "add";
        }
    }


    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id,Model model) {
        try {
            userService.deleteUserById(id);
        } catch (NoSuchElementException e){
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/users";
    }


    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit";
    }


    @GetMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.updateUser(user);
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "edit";
        }
    }
}
