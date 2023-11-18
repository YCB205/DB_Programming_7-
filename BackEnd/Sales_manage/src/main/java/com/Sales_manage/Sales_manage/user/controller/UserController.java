package com.Sales_manage.Sales_manage.user.controller;

import com.Sales_manage.Sales_manage.brand.entity.BrandEntity;
import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        if (userService.login(id, password, session).equals("store_manager")) {
            return "redirect:/html/store_manager/MainJumju.html"; //아이디가 점주 일때 점주 메인페이지로 이동
        } else if (userService.login(id, password, session).equals("manager")) {
            return "redirect:/html/manager/MainJumju.html"; //아이디가 매니저 일때 매니저 메인페이지로 이동
        } else {
            model.addAttribute("loginError", "Fail to Login");
            return "index"; // 로그인 실패 시 로그인페이지로 이동
        }
    }

    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        if ("manager".equals(loggedInUserRole)) {
            ManagerEntity managerEntity = userService.getManagerInfo(loggedInUserId);
            BrandEntity brandEntity = managerEntity.getIdBrand();

            result.put("brandName", brandEntity.getBrandName());
            result.put("idManager", managerEntity.getIdManager());
            result.put("name", managerEntity.getName());
            result.put("email", managerEntity.getEmail());
            result.put("phoneNumber", managerEntity.getPhoneNumber());
            result.put("position","매니저");
        } else if ("store_manager".equals(loggedInUserRole)) {
            StoreManagerEntity storeManagerEntity = userService.getStoreManagerInfo(loggedInUserId);
            BrandOfficeEntity brandOfficeEntity = userService.getBrandOfficeInfo(storeManagerEntity);
            BrandEntity brandEntity = brandOfficeEntity.getIdBrand();

            result.put("brandName", brandEntity.getBrandName());
            result.put("idStoreManager", storeManagerEntity.getIdStoreManager());
            result.put("name", storeManagerEntity.getName());
            result.put("email", storeManagerEntity.getEmail());
            result.put("number", storeManagerEntity.getNumber());
            result.put("officeName", brandOfficeEntity.getOfficeName());
            result.put("position","점주");
        }

        return result;
    }

}
