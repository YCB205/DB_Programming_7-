package com.Sales_manage.Sales_manage.brand.controller;

import com.Sales_manage.Sales_manage.brand.dto.BrandDTO;
import com.Sales_manage.Sales_manage.brand.service.BrandService;
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
    private final BrandService brandService;


    @GetMapping("/store")
    @ResponseBody
    public List<BrandDTO> getAll() {
        System.out.println(brandService.getAll());
        return brandService.getAll();
    }

}
