package com.example.session17.controller;

import com.example.session17.entity.*;
import com.example.session17.model.CartItem;
import com.example.session17.model.OrderCustomer;
import com.example.session17.service.cart.CartServiceImp;
import com.example.session17.service.order.OrderServiceImp;
import com.example.session17.service.orderDetail.OrderDetailServiceImp;
import com.example.session17.service.product.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private CartServiceImp cartServiceImp;

    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private OrderServiceImp orderServiceImp;

    @Autowired
    private OrderDetailServiceImp orderDetailServiceImp;

    @GetMapping("pay")
    public String pay(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            return "redirect:/login";
        }
        List<ProductCart> cartItems = cartServiceImp.getAllCarts(customer.getId());
        List<CartItem> cartDTOs = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (ProductCart item : cartItems) {
            Product product = productServiceImp.getProduct(item.getProductId());
            if (product != null) {
                BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(itemTotal);

                CartItem dto = new CartItem();
                dto.setCart(item);
                dto.setProduct(product);
                cartDTOs.add(dto);
            }
        }
        OrderCustomer orderCustomer = new OrderCustomer();
        orderCustomer.setName(customer.getUsername());
        orderCustomer.setPhoneNumber(customer.getPhoneNumber());
        model.addAttribute("cartItems", cartDTOs);
        model.addAttribute("total", total);
        model.addAttribute("orderCustomer", orderCustomer);
        model.addAttribute("order", new Orders());
        return "pay";
    }

    @PostMapping("order-save")
    public String orderSave(@Valid @ModelAttribute("orderCustomer") OrderCustomer orderCustomer,
                            BindingResult bindingResult,
                            HttpSession session,
                            Model model) {
        if (bindingResult.hasErrors()) {
            Customer customer = (Customer) session.getAttribute("loggedInUser");
            List<ProductCart> cartItems = cartServiceImp.getAllCarts(customer.getId());
            List<CartItem> cartDTOs = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            for (ProductCart item : cartItems) {
                Product product = productServiceImp.getProduct(item.getProductId());
                if (product != null) {
                    BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    total = total.add(itemTotal);
                    CartItem dto = new CartItem();
                    dto.setCart(item);
                    dto.setProduct(product);
                    cartDTOs.add(dto);
                }
            }

            model.addAttribute("cartItems", cartDTOs);
            model.addAttribute("total", total);
            model.addAttribute("orderCustomer", orderCustomer);
            model.addAttribute("order", new Orders());
            return "pay";
        }

        Customer customer = (Customer) session.getAttribute("loggedInUser");
        List<ProductCart> cartItems = cartServiceImp.getAllCarts(customer.getId());
        BigDecimal total = BigDecimal.ZERO;

        for (ProductCart item : cartItems) {
            Product product = productServiceImp.getProduct(item.getProductId());
            if (product != null) {
                total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        Orders newOrder = new Orders();
        newOrder.setCustomer(customer);
        newOrder.setRecipientName(orderCustomer.getName());
        newOrder.setPhoneNumber(orderCustomer.getPhoneNumber());
        newOrder.setAddress(orderCustomer.getAddress());
        newOrder.setTotalMoney(total.doubleValue());
        newOrder.setStatus(true);

        orderServiceImp.save(newOrder);
        for (ProductCart item : cartItems) {
            Product product = productServiceImp.getProduct(item.getProductId());
            if (product != null) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(newOrder);
                orderDetail.setProduct(product);
                orderDetail.setQuantity(item.getQuantity());
                orderDetailServiceImp.save(orderDetail);
            }
        }
        cartServiceImp.removeCart(customer.getId());
        return "redirect:/home";
    }

}
