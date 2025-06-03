package com.example.session17.controller;

import com.example.session17.entity.Customer;
import com.example.session17.entity.Product;
import com.example.session17.entity.ProductCart;
import com.example.session17.service.cart.CartServiceImp;
import com.example.session17.service.product.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartServiceImp cartServiceImp;

    @Autowired
    private ProductServiceImp productServiceImp;

    @PostMapping("addToCart")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {

        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            return "redirect:/login";
        }

        ProductCart cart = new ProductCart();
        cart.setCustomerId(customer.getId());
        cart.setProductId(productId);
        cart.setQuantity(quantity);

        cartServiceImp.addToCart(cart);

        return "redirect:/home";
    }

    @GetMapping("listCart")
    public String viewCart(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            return "redirect:/login";
        }

        List<ProductCart> cartItems = cartServiceImp.getAllCarts(customer.getId());
        BigDecimal total = BigDecimal.ZERO;

        for (ProductCart item : cartItems) {
            Product product = productServiceImp.getProduct(item.getProductId());
            if (product != null) {
                BigDecimal price = product.getPrice();
                BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                total = total.add(price.multiply(quantity));
            }
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "listCart";
    }

}
