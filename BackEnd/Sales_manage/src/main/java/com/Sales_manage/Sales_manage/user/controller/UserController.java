package com.Sales_manage.Sales_manage.user.controller;

import com.Sales_manage.Sales_manage.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(HttpSession session, String id, String password, Model model) {
        if (userService.login(id, password, session)) {
            return "redirect:/html/store_manager/MainJumju.html";
        } else {
            model.addAttribute("loginError", "Fail to Login");
            return "index"; // 로그인 실패 시 index 페이지로 리다이렉션
        }
    }
}
