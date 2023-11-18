package com.Sales_manage.Sales_manage.brand_office.controller;

import com.Sales_manage.Sales_manage.store_manager.dto.StoreManagerDTO;
import com.Sales_manage.Sales_manage.store_manager.service.StoreManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brand_office")
public class BrandOfficeController {
    private final StoreManagerService brandOfficeService;


    @GetMapping("/store")
    @ResponseBody
    public List<StoreManagerDTO> getAll() {
        System.out.println(brandOfficeService.getAll());
        return brandOfficeService.getAll();
    }

}
