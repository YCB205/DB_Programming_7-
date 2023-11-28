package com.Sales_manage.Sales_manage.brand_office.controller;

import com.Sales_manage.Sales_manage.brand_office.service.BrandOfficeService;
import com.Sales_manage.Sales_manage.brand_office.dto.BrandOfficeDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BrandOfficeController {

    @Autowired
    private final BrandOfficeService brandOfficeService;
    public BrandOfficeController(BrandOfficeService brandOfficeService) {
        this.brandOfficeService = brandOfficeService;
    }


    @GetMapping("/branch-storeManagers")
    @ResponseBody
    public List<List<Object>> getBranchStoreManagers(
            @RequestParam(name = "officeName", required = false) String officeName,
            @RequestParam(name = "name", required = false) String name, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        if ("manager".equals(loggedInUserRole)) {
            List<List<Object>> result = brandOfficeService.findBranchStoreManagers(officeName, name);
            return result;
        } else {
            ResponseEntity.badRequest().build();
        }
        return null;
    }

}
