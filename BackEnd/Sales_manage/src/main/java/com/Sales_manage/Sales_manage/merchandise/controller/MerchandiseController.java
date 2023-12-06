package com.Sales_manage.Sales_manage.merchandise.controller;


import com.Sales_manage.Sales_manage.merchandise.dto.MerchandiseRequestDTO;
import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import com.Sales_manage.Sales_manage.merchandise.service.MerchandiseService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MerchandiseController {

    @Autowired
    private MerchandiseService merchandiseService;

    @GetMapping("/orderproducts")
    @ResponseBody
    public Map<String, List<Map<String, Object>>> getProductsOrder(@RequestParam(value = "product_name") String productName,
                                                              @RequestParam(value = "category") List<String> categories,
                                                              HttpSession session)
    {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        return merchandiseService.getProductsOrder(productName, categories, loggedInUserId);
    }

    @GetMapping("/allProducts")
    @ResponseBody
    public Map<String, List<Map<String, Object>>> getAllProducts(@RequestParam(value = "product_name") String productName,
                                                                   @RequestParam(value = "category") List<String> categories,
                                                                   HttpSession session)
    {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        return merchandiseService.getAllProducts(productName, categories, loggedInUserId);
    }

    @PutMapping("/products")
    @ResponseBody
    public ResponseEntity<String> updateMerchandise(
            @PathVariable("id") Long id,
            @RequestBody MerchandiseRequestDTO merchandiseRequestDTO) {
        String result = merchandiseService.updateMerchandise(id, merchandiseRequestDTO);
        return ResponseEntity.ok(result);
    }


}
