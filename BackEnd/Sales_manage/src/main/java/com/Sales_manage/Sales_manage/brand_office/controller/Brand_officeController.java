package com.Sales_manage.Sales_manage.brand_office.controller;

import com.Sales_manage.Sales_manage.store_manager.dto.Store_managerDTO;
import com.Sales_manage.Sales_manage.store_manager.service.Store_managerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Brand_officeController {
    private final Store_managerService brandOfficeService;


    @GetMapping("/store")
    @ResponseBody
    public List<Store_managerDTO> getAll() {
        System.out.println(brandOfficeService.getAll());
        return brandOfficeService.getAll();
    }

}
