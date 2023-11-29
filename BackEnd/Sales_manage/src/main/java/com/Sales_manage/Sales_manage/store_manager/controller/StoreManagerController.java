package com.Sales_manage.Sales_manage.store_manager.controller;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.store_manager.dto.StoreManagerDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.service.StoreManagerService;
import com.Sales_manage.Sales_manage.user.dto.UserData;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class StoreManagerController {
    private final StoreManagerService storeManagerService;


    @GetMapping("/storeManagers")
    @ResponseBody
    public Map<String, Object> getUserInfo(
            @RequestParam(name = "idStoreManager", required = false) String idStoreManager,
            @RequestParam(name = "name", required = false) String name,
            HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");

        if ("manager".equals(loggedInUserRole)) {
            if(idStoreManager.isEmpty() && name.isEmpty()) {
                System.out.println("35번째 줄 실행");
                List<StoreManagerDTO> allStoreManagers = storeManagerService.getAll();
                result.put("storeManagers", allStoreManagers);
            } else if(!idStoreManager.isEmpty() && name.isEmpty()){
                StoreManagerEntity storeManagerEntity = storeManagerService.getStoreManagerInfo(idStoreManager);
                BrandOfficeEntity brandOfficeEntity = storeManagerService.getBrandOfficeInfo(storeManagerEntity);

                result.put("idStoreManager", storeManagerEntity.getIdStoremanager());
                result.put("name", storeManagerEntity.getName());
                result.put("email", storeManagerEntity.getEmail());
                result.put("phoneNumber", storeManagerEntity.getPhoneNumber());
                result.put("officeName", brandOfficeEntity.getOfficeName());
                result.put("position", "점주");
            } else {
                List<StoreManagerDTO> filterStoreManagers = storeManagerService.getFilterName(name);
                result.put("storeManagers", filterStoreManagers);
            }
        } else {
            ResponseEntity.badRequest().build();
        }

        return result;
    }


    @PutMapping("/storeManagers")
    @ResponseBody
    public ResponseEntity<String> updateStoreManagers(@RequestBody UserData userData, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");

        if ("manager".equals(loggedInUserRole)) {
            storeManagerService.updateStoreManager(userData);
        } else {
            return new ResponseEntity<>("Invalid user role", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);

    }






}
