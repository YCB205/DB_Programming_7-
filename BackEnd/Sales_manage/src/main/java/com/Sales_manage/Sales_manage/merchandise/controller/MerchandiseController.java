package com.Sales_manage.Sales_manage.merchandise.controller;


import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import com.Sales_manage.Sales_manage.merchandise.service.MerchandiseService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Controller
public class MerchandiseController {

    @Autowired
    private MerchandiseService merchandiseService;

    @GetMapping("/orderproducts")
    @ResponseBody
    public Map<String, List<Map<String, Object>>> getProductsOrder(@RequestParam(value = "product_name", defaultValue = "") String productName,
                                                              @RequestParam(value = "category") List<String> categories,
                                                              HttpSession session)
    {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        return merchandiseService.getProductsOrder(productName, categories, loggedInUserId);
    }

    @GetMapping("/products")
    @ResponseBody
    public List<MerchandiseEntity> getProducts(@RequestParam(value = "product_name", defaultValue = "") String productName, HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        return merchandiseService.getProducts(productName, loggedInUserId);
    }



}
