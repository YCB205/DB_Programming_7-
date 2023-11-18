package com.Sales_manage.Sales_manage.user.controller;

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

}
