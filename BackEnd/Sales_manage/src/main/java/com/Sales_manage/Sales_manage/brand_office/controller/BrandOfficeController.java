package com.Sales_manage.Sales_manage.brand_office.controller;

import com.Sales_manage.Sales_manage.brand_office.service.BrandOfficeService;
import com.Sales_manage.Sales_manage.brand_office.dto.BrandOfficeDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BrandOfficeController {

    @Autowired
    private final BrandOfficeService brandOfficeService;
    public BrandOfficeController(BrandOfficeService brandOfficeService) {
        this.brandOfficeService = brandOfficeService;
    }


    @GetMapping("/branch-storeManagers")
    @ResponseBody
    public List<List<Object>> getBranchStoreManagers(
            @RequestParam(name = "officeName", required = false) String officeName,
            @RequestParam(name = "name", required = false) String name, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        if ("manager".equals(loggedInUserRole)) {
            List<List<Object>> result = brandOfficeService.findBranchStoreManagers(officeName, name);
            return result;
        } else {
            ResponseEntity.badRequest().build();
        }
        return null;
    }

    @GetMapping("/closedBranch")
    @ResponseBody
    public List<BrandOfficeDTO> getBranchStoreManagers(
            @RequestParam(name = "officeName", required = false) String officeName, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        if ("manager".equals(loggedInUserRole)) {
            System.out.println("46번째 줄 실행");
            List<BrandOfficeDTO> result = brandOfficeService.getClosedBranch(officeName);
            return result;
        } else {
            ResponseEntity.badRequest().build();
        }
        return null;
    }

    @PutMapping("/branch-storeManagers")
    @ResponseBody
    public ResponseEntity<Void> updateBranch(
            @RequestBody BrandOfficeDTO brandOfficeDTO,
            HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        if ("manager".equals(loggedInUserRole)) {
            // 서비스를 통해 주문서 업데이트 수행
            System.out.println(brandOfficeDTO);
            brandOfficeService.updateBranch(brandOfficeDTO);
            return ResponseEntity.ok().build();
        } else {
            ResponseEntity.badRequest().build();
        }
        return null;
    }

    @PutMapping("/deleteStoreManagerOnBranch")
    @ResponseBody
    public ResponseEntity<Object> deleteStoreManagerOnBranch(
            @RequestParam(name = "idBrandOffice", required = false) Long idBrandOffice, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        if ("manager".equals(loggedInUserRole)) {
            System.out.println("78번 실행"+ idBrandOffice);
            brandOfficeService.deleteStoreManagerOnBranch(idBrandOffice);
            return ResponseEntity.ok().build();
        } else {
            ResponseEntity.badRequest().build();
        }
        return null;
    }

    @DeleteMapping("/branch/{idBrandOffice}")
    @ResponseBody
    public ResponseEntity<Void> deleteBrandOffice(@PathVariable Long idBrandOffice, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        if ("manager".equals(loggedInUserRole)) {
            brandOfficeService.deleteBrandOffice(idBrandOffice);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
