package com.Sales_manage.Sales_manage.manager.controller;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.manager.dto.ManagerDTO;
import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import com.Sales_manage.Sales_manage.manager.service.ManagerService;
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
public class ManagerController {
    private final ManagerService managerService;



//    @GetMapping("/managers")
//    @ResponseBody
//    public Map<String, Object> getUserInfo(
//            @RequestParam(name = "idStoreManager", required = false) String idStoreManager,
//            @RequestParam(name = "name", required = false) String name,
//            HttpSession session) {
//        Map<String, Object> result = new HashMap<>();
//
//        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
//
//        if ("manager".equals(loggedInUserRole)) {
//            if(idStoreManager.isEmpty() && name.isEmpty()) {
//                System.out.println("35번째 줄 실행");
//                List<ManagerDTO> allStoreManagers = ManagerService.getAll();
//                result.put("storeManagers", allStoreManagers);
//            } else if(!idStoreManager.isEmpty() && name.isEmpty()){
//                ManagerEntity storeManagerEntity = ManagerService.getStoreManagerInfo(idStoreManager);
//                BrandOfficeEntity brandOfficeEntity = ManagerService.getBrandOfficeInfo(storeManagerEntity);
//
//                result.put("idStoreManager", storeManagerEntity.getIdStoremanager());
//                result.put("name", storeManagerEntity.getName());
//                result.put("email", storeManagerEntity.getEmail());
//                result.put("phoneNumber", storeManagerEntity.getPhoneNumber());
////                result.put("officeName", brandOfficeEntity.getOfficeName());
//                result.put("position", "점주");
//            } else {
//                List<ManagerDTO> filterStoreManagers = ManagerService.getFilterName(name);
//                result.put("storeManagers", filterStoreManagers);
//            }
//        } else {
//            ResponseEntity.badRequest().build();
//        }
//
//        return result;
//    }
//
//
//    @PutMapping("/managers")
//    @ResponseBody
//    public ResponseEntity<String> updateStoreManagers(@RequestBody UserData userData, HttpSession session) {
//        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
//
//        if ("manager".equals(loggedInUserRole)) {
//            ManagerService.updateManager(userData);
//        } else {
//            return new ResponseEntity<>("Invalid user role", HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
//
//    }
//
//
//    @DeleteMapping("/managers/{idManager}")
//    @ResponseBody
//    public ResponseEntity<Void> deleteStoreManager(@PathVariable String idManager, HttpSession session) {
//        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
//        if ("manager".equals(loggedInUserRole)) {
//            ManagerService.deleteManager(idManager);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//
//    @GetMapping("/checkedManagerId")
//    @ResponseBody
//    public ResponseEntity<String> checkPasswd(@RequestParam String idStoremanager, HttpSession session) {
//        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
//
//        if ("manager".equals(loggedInUserRole)) {
//            if (ManagerService.checkManagerId(idStoremanager)){
//                return ResponseEntity.ok("success");
//            }
//        }
//
//        // 비밀번호가 일치하지 않을 때
//        return ResponseEntity.badRequest().body("The password does not match");
//    }
//
//    @PostMapping("/managers")
//    @ResponseBody
//    public ResponseEntity<String> createStoreManager(@RequestBody ManagerDTO storeManagerDTO) {
//        try {
//            ManagerService.createManager(storeManagerDTO);
//            return new ResponseEntity<>("Store Manager created successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error creating Store Manager", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
