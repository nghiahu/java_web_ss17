package com.example.session17.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.session17.entity.Customer;
import com.example.session17.entity.Product;
import com.example.session17.model.EnumRole;
import com.example.session17.service.auth.AuthServiceImp;
import com.example.session17.service.order.OrderServiceImp;
import com.example.session17.service.product.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private AuthServiceImp authServiceImp;
    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private OrderServiceImp orderServiceImp;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("admin")
    public String admin(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            return "redirect:/login";
        }else {
            if (customer.getRole() == EnumRole.USER) {
                return "login";
            }
        }
        model.addAttribute("pageContent", "dashboard");
        model.addAttribute("totalCustomer", authServiceImp.countCustomers());
        model.addAttribute("totalOrders", orderServiceImp.countOrders());
        model.addAttribute("totalProduct", productServiceImp.countProduct());
        model.addAttribute("revenue", orderServiceImp.revenue());
        return "adminPage";
    }

    @GetMapping("dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageContent", "dashboard");
        model.addAttribute("totalCustomer", authServiceImp.countCustomers());
        model.addAttribute("totalOrders", orderServiceImp.countOrders());
        model.addAttribute("totalProduct", productServiceImp.countProduct());
        model.addAttribute("revenue", orderServiceImp.revenue());
        return "adminPage";
    }

    @GetMapping("managerProduct")
    public String managerProduct(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 Model model) {
        model.addAttribute("pageContent", "managerProduct");
        model.addAttribute("products", productServiceImp.getAllProducts(page,size));
        model.addAttribute("currentPage", page);
        return "adminPage";
    }

    @GetMapping("/managerCustomer")
    public String managerCustomer(Model model,
                                  @RequestParam(defaultValue = "") String keyword,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        List<Customer> customers = authServiceImp.searchCustomers(keyword, page, size);
        int total = authServiceImp.countSearchCustomers(keyword);
        int totalPages = (int) Math.ceil((double) total / size);

        model.addAttribute("pageContent", "managerCustomer");
        model.addAttribute("customers", customers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        return "adminPage";
    }

    @PostMapping("/toggleStatus")
    public String toggleCustomerStatus(@RequestParam int id, @RequestParam boolean status, @RequestParam String keyword, @RequestParam int page) {
        authServiceImp.updateStatus(id, status);
        return "redirect:/managerCustomer?keyword=" + keyword + "&page=" + page;
    }

    @GetMapping("managerOrder")
    public String managerOrder(Model model) {
        model.addAttribute("pageContent", "managerOrder");
        return "adminPage";
    }

    @GetMapping("addProduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @GetMapping("editProduct")
    public String editProduct(@RequestParam("id") Long id, Model model) {
        model.addAttribute("product", productServiceImp.getProduct(id));
        return "updateProduct";
    }

    @GetMapping("deleteProduct")
    public String deleteProduct(@RequestParam("id") Long id) {
        productServiceImp.deleteProduct(id);
        return "redirect:/managerProduct";
    }

    @PostMapping("updateProduct")
    public String updateProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String url = (String) uploadResult.get("url");
                if (url == null || url.isEmpty()) {
                    throw new RuntimeException("Invalid URL");
                }
                product.setImage(url);
            } else {
                Product oldProduct = productServiceImp.getProduct(product.getId());
                product.setImage(oldProduct.getImage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        productServiceImp.updateProduct(product);
        return "redirect:/managerProduct";
    }

    @PostMapping("add-product")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("url");
            if (url == null || url.isEmpty()) {
                throw new RuntimeException("Invalid URL");
            }
            product.setImage(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        productServiceImp.addProduct(product);
        return "redirect:/managerProduct";
    }

}
