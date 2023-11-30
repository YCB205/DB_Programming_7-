package com.Sales_manage.Sales_manage.orderSheet.controller;


import com.Sales_manage.Sales_manage.merchandise.ropository.MerchandiseRepository;
import com.Sales_manage.Sales_manage.orderSheet.dto.OrderSheetResponse;
import com.Sales_manage.Sales_manage.orderSheet.service.OrderSheetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderSheetController {

    @Autowired
    private OrderSheetService orderSheetService;

    @GetMapping("/orderSheet")
    @ResponseBody
    public ResponseEntity<List<OrderSheetResponse>> getOrderSheet(
            @RequestParam("search_merchandise") String searchMerchandiseValue,
            @RequestParam("startDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDateTime,
                @RequestParam("endDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDateTime,
            HttpSession session) {

        System.out.println("신호받음");

        // 로그인한 사용자의 역할과 아이디 확인
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        // 역할이 store_manager인지 확인
        if (!"store_manager".equals(loggedInUserRole)) {
            System.out.println("37번줄 실행");
            return ResponseEntity.badRequest().build();
        } else {
        // 서비스를 통해 주문서 목록 조회
            System.out.println("41번줄 실행");
        List<OrderSheetResponse> orderSheetList = orderSheetService.getOrderSheetList(loggedInUserId, startDateTime, endDateTime, searchMerchandiseValue);
        return ResponseEntity.ok(orderSheetList);
        }
    }

    @PostMapping("/orderSheet")
    @ResponseBody
    public void createOrderSheet(@RequestParam("product_id") List<Integer> productIds,
                                 @RequestParam("count") List<Integer> counts,
                                 @RequestParam("setDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime setDateTime,
                                 HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        orderSheetService.createOrderSheet(productIds, counts, setDateTime, loggedInUserId);

    }
}


