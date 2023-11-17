package com.Sales_manage.Sales_manage.manager.controller;

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
@RequestMapping("/manager")
public class ManagerController {
    private final Store_managerService storeManagerService;


    @GetMapping("/store")
    @ResponseBody
    public List<Store_managerDTO> getAll() {
        System.out.println(storeManagerService.getAll());
        return storeManagerService.getAll();
    }

}
