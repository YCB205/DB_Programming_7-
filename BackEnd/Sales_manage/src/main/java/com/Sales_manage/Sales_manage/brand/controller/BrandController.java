package com.Sales_manage.Sales_manage.brand.controller;

import com.Sales_manage.Sales_manage.store_manager.dto.Store_managerDTO;
import com.Sales_manage.Sales_manage.store_manager.service.Store_managerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final Store_managerService storemanagerService;


    @GetMapping("/store")
    @ResponseBody
    public List<Store_managerDTO> getAll() {
        System.out.println(storemanagerService.getAll());
        return storemanagerService.getAll();
    }

}
