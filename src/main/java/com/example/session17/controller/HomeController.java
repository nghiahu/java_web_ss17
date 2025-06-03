package com.example.session17.controller;

import com.example.session17.entity.Product;
import com.example.session17.service.product.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("home")
    public String home(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int size,
            Model model) {

        model.addAttribute("products", productServiceImp.getAllProducts(page, size));
        model.addAttribute("currentPage", page);
        return "home";
    }

    @GetMapping("productDetail")
    public String productDetail(@RequestParam("id") Long id, Model model, HttpSession session) {
        Object user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("product", productServiceImp.getProduct(id));
        return "ProductDetail";
    }

}
