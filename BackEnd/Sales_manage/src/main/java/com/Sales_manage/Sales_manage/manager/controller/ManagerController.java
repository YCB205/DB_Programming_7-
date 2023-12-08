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

    @GetMapping("/managers")
    @ResponseBody
    public Map<String, Object> getUserInfo(
            @RequestParam(name = "idManager", required = false) String idManager,
            @RequestParam(name = "name", required = false) String name,
            HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        if ("admin".equals(managerService.getPostion(loggedInUserId))) {
            if(idManager.isEmpty() && name.isEmpty()) {
                System.out.println("35번째 줄 실행");
                List<ManagerDTO> allManagers = managerService.getAll();
                result.put("Managers", allManagers);
            } else if(!idManager.isEmpty() && name.isEmpty()){
                ManagerEntity managerEntity = managerService.getManagerInfo(idManager);

                result.put("idManager", managerEntity.getIdManager());
                result.put("name", managerEntity.getName());
                result.put("email", managerEntity.getEmail());
                result.put("phoneNumber", managerEntity.getPhoneNumber());
                result.put("position", "매니저");
            } else {
                List<ManagerDTO> filterManagers = managerService.getFilterName(name);
                result.put("Managers", filterManagers);
            }
        } else {
            ResponseEntity.badRequest().build();
        }

        return result;
    }


    @PutMapping("/managers")
    @ResponseBody
    public ResponseEntity<String> updateStoreManagers(@RequestBody UserData userData, HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        if ("admin".equals(managerService.getPostion(loggedInUserId))) {
            managerService.updateManager(userData);
        } else {
            return new ResponseEntity<>("Invalid user role", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);

    }


    @DeleteMapping("/managers/{idManager}")
    @ResponseBody
    public ResponseEntity<Void> deleteManager(@PathVariable String idManager, HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        if ("admin".equals(managerService.getPostion(loggedInUserId))) {
            managerService.deleteManager(idManager);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/checkedManagerId")
    @ResponseBody
    public ResponseEntity<String> checkPasswd(@RequestParam String idManager, HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        if ("admin".equals(managerService.getPostion(loggedInUserId))) {
            if (managerService.checkManagerId(idManager)){
                return ResponseEntity.ok("success");
            }
        }

        // 비밀번호가 일치하지 않을 때
        return ResponseEntity.badRequest().body("The password does not match");
    }

    @PostMapping("/managers")
    @ResponseBody
    public ResponseEntity<String> createManager(@RequestBody ManagerDTO managerDTO, HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        if ("admin".equals(managerService.getPostion(loggedInUserId))) {
            try {
                managerService.createManager(managerDTO, loggedInUserId);
                return new ResponseEntity<>("Store Manager created successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error creating Store Manager", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } return null;
    }


}
