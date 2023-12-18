package com.Sales_manage.Sales_manage.merchandise.controller;


import com.Sales_manage.Sales_manage.merchandise.dto.MerchandiseRequestDTO;
import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import com.Sales_manage.Sales_manage.merchandise.service.MerchandiseService;

import com.Sales_manage.Sales_manage.orderSheet.dto.MerchandiseRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        return merchandiseService.getAllProducts(productName, categories, loggedInUserId, loggedInUserRole);
    }

    @PutMapping("/products")
    @ResponseBody
    public ResponseEntity<String> updateMerchandise(
            @RequestBody MerchandiseRequestDTO merchandiseRequestDTO,
            HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        String result = merchandiseService.updateMerchandise(merchandiseRequestDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/products")
    @ResponseBody
    public ResponseEntity<String> createMerchandise(@RequestBody MerchandiseRequestDTO merchandiseRequest,
                                                    HttpSession session) {
        try {
            String loggedInUserId = (String) session.getAttribute("loggedInUserId");
            String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
            merchandiseService.createMerchandise(merchandiseRequest, loggedInUserId, loggedInUserRole);
            return ResponseEntity.ok("Merchandise created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create merchandise.");
        }
    }

    @DeleteMapping("/products{idMerchandise}")
    @ResponseBody
    public ResponseEntity<Void> deleteProduct(@PathVariable Long idMerchandise, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        if ("manager".equals(loggedInUserRole)) {
            merchandiseService.deleteProduct(idMerchandise);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();

    }
}
