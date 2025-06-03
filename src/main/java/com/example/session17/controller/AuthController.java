package com.example.session17.controller;

import com.example.session17.entity.Customer;
import com.example.session17.model.EnumRole;
import com.example.session17.model.LoginCustomer;
import com.example.session17.service.auth.AuthServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private AuthServiceImp authService;

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @GetMapping("successRegister")
    public String succesRegister() {
        return "successRegister";
    }

    @PostMapping("register-save")
    public String register(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        authService.register(customer);
        return "successRegister";
    }

    @GetMapping("admin")
    public String admin() {
        return "adminPage";
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("customer", new Customer());
        return "login";
    }

    @PostMapping("login-save")
    public String login(@Valid @ModelAttribute("customer") LoginCustomer customer, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        Customer loginCustomer = authService.login(customer.getUsername(), customer.getPassword());

        if (loginCustomer != null) {
            session.setAttribute("loggedInUser", loginCustomer);
            if(loginCustomer.getRole() == EnumRole.ADMIN){
                return "redirect:/admin";
            }else {
                return "redirect:/home";
            }
        }
        model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng");
        return "login";
    }

}
