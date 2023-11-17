package com.Sales_manage.Sales_manage.user.controller;

import com.Sales_manage.Sales_manage.store_manager.dto.Store_managerDTO;
import com.Sales_manage.Sales_manage.store_manager.service.Store_managerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final Store_managerService storeManagerService;


    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        // 아이디와 비밀번호 검증 로직 추가 (여기서는 간단히 생략)
        if (storeManagerService.isValidLogin(id, password)) {
            // 로그인 성공 시 세션에 아이디 저장
            session.setAttribute("loggedInUserId", id);
            // 로그인 성공 시 리다이렉션
            return "redirect:/html/htlmMainJumju.html";
        } else {
            // 로그인 실패 시 알람을 띄우고 loginPage로 이동
            model.addAttribute("loginError", "Fail to Login");
            return "index";
        }
    }

    @GetMapping("/store")
    @ResponseBody
    public List<Store_managerDTO> getAll() {
        System.out.println(storeManagerService.getAll());
        return storeManagerService.getAll();
    }

}
