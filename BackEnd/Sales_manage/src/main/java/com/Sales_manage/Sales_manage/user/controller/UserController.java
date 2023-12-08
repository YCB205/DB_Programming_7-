package com.Sales_manage.Sales_manage.user.controller;

import com.Sales_manage.Sales_manage.brand.entity.BrandEntity;
import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.user.dto.UserData;
import com.Sales_manage.Sales_manage.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(HttpSession session, String id, String password, Model model) {
        String userRole = userService.login(id, password, session);
        if ("store_manager".equals(userRole)) {
            return "redirect:/html/store_manager/MainJumju.html";
        } else if ("manager".equals(userRole)) {
            return "redirect:/html/manager/MainManager.html";
        } else {
            model.addAttribute("loginError", "Fail to Login");
            return "index";
        }
    }

    @GetMapping("/userPasswd")
    @ResponseBody
    public ResponseEntity<String> checkPasswd(@RequestParam String passwd, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        if ("manager".equals(loggedInUserRole)) {
            if (userService.checkPasswd(loggedInUserId, passwd)){
                return ResponseEntity.ok("success");
            }
        }

        // 비밀번호가 일치하지 않을 때
        return ResponseEntity.badRequest().body("The password does not match");
    }


    @GetMapping("/userPosition")
    @ResponseBody
    public String userPosition(HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        if ("manager".equals(userService.getPostion(loggedInUserId))){
            return "매니저";
        } else {
            return "관리자";
        }
    }


    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        if ("manager".equals(loggedInUserRole)) {
            if ("manager".equals(userService.getPostion(loggedInUserId))) {
                ManagerEntity managerEntity = userService.getManagerInfo(loggedInUserId);
                BrandEntity brandEntity = managerEntity.getIdBrand();

                result.put("brandName", brandEntity.getBrandName());
                result.put("idManager", managerEntity.getIdManager());
                result.put("name", managerEntity.getName());
                result.put("email", managerEntity.getEmail());
                result.put("phoneNumber", managerEntity.getPhoneNumber());
                result.put("position", "매니저");
            } else {
                System.out.println("82번줄 실행");
                ManagerEntity managerEntity = userService.getManagerInfo(loggedInUserId);
                BrandEntity brandEntity = managerEntity.getIdBrand();

                result.put("brandName", brandEntity.getBrandName());
                result.put("idManager", managerEntity.getIdManager());
                result.put("name", managerEntity.getName());
                result.put("email", managerEntity.getEmail());
                result.put("phoneNumber", managerEntity.getPhoneNumber());
                result.put("position", "관리자");
            }


        } else if ("store_manager".equals(loggedInUserRole)) {
            StoreManagerEntity storeManagerEntity = userService.getStoreManagerInfo(loggedInUserId);
            BrandOfficeEntity brandOfficeEntity = userService.getBrandOfficeInfo(storeManagerEntity);
            BrandEntity brandEntity = brandOfficeEntity.getIdBrand();

            result.put("brandName", brandEntity.getBrandName());
            result.put("idStoreManager", storeManagerEntity.getIdStoremanager());
            result.put("name", storeManagerEntity.getName());
            result.put("email", storeManagerEntity.getEmail());
            result.put("phoneNumber", storeManagerEntity.getPhoneNumber());
            result.put("officeName", brandOfficeEntity.getOfficeName());
            result.put("position","점주");
        }

        return result;
    }



    @PutMapping("/user")
    @ResponseBody
    public ResponseEntity<String> updateUser(@RequestBody UserData userData, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");

        try {
            if ("store_manager".equals(loggedInUserRole)) {
                userService.updateStoreManager(userData);
            } else if ("manager".equals(loggedInUserRole)) {
                userService.updateManager(userData);
            } else {
                return new ResponseEntity<>("Invalid user role", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
            return "success to logout";
        } else {
            return "fail to logout";
        }

    }

}
