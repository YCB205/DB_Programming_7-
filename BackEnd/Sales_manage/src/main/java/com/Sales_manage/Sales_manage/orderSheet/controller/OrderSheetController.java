package com.Sales_manage.Sales_manage.orderSheet.controller;

import com.Sales_manage.Sales_manage.orderSheet.dto.MerchandiseRequest;
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
        List<OrderSheetResponse> orderSheetList = orderSheetService.getOrderSheetList(loggedInUserId, startDateTime, endDateTime, searchMerchandiseValue);
        return ResponseEntity.ok(orderSheetList);
        }
    }

    @GetMapping("/allOrderproducts")
    @ResponseBody
    public ResponseEntity<List<Object[]>> getallorderproducts(
            @RequestParam("search_merchandise") List<String> searchMerchandiseValue,
            @RequestParam("startDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDateTime,
            @RequestParam("endDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDateTime,
            @RequestParam("brandoffice_id") List<Long> brandofficeId,
            HttpSession session) {


        // 로그인한 사용자의 역할과 아이디 확인
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        List<Object[]> salesData = orderSheetService.getAllSalesData(searchMerchandiseValue, startDateTime, endDateTime, brandofficeId, loggedInUserRole, loggedInUserId);
        // 역할이 store_manager인지 확인
        if (salesData.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(salesData);

    }

    @GetMapping("/chartOrderproducts")
    @ResponseBody
    public ResponseEntity<List<Object[]>> getchartorderproducts(
            @RequestParam("search_merchandise") List<String> searchMerchandiseValue,
            @RequestParam("startDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDateTime,
            @RequestParam("endDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDateTime,
            @RequestParam("brandoffice_id") List<Long> brandofficeId,
            HttpSession session) {


        // 로그인한 사용자의 역할과 아이디 확인
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        List<Object[]> salesData = orderSheetService.getAllChartData(searchMerchandiseValue, startDateTime, endDateTime, brandofficeId, loggedInUserRole, loggedInUserId);
        System.out.println("searchMerchandiseValue = " + searchMerchandiseValue);
        System.out.println("startDateTime = " + startDateTime);
        System.out.println("endDateTime = " + endDateTime);
        // 역할이 store_manager인지 확인
        if (salesData.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(salesData);

    }



    @PutMapping("/orderSheet")
    @ResponseBody
    public ResponseEntity<Void> updateOrderSheet(
            @RequestBody MerchandiseRequest merchandiseRequest,
            HttpSession session) {

        // 로그인한 사용자의 역할과 아이디 확인
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");

        // 역할이 store_manager인지 확인
        if (!"store_manager".equals(loggedInUserRole)) {
            return ResponseEntity.badRequest().build();
        } else {
            // 서비스를 통해 주문서 업데이트 수행
            orderSheetService.updateOrderSheet(merchandiseRequest);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/orderSheet/{idOrdersheet}")
    @ResponseBody
    public ResponseEntity<Void> deleteOrderSheet(@PathVariable Long idOrdersheet, HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        if (!"store_manager".equals(loggedInUserRole)) {
            return ResponseEntity.badRequest().build();
        } else {
            // 서비스를 통해 주문서 및 관련 IncludeEntity 삭제
            orderSheetService.deleteOrderSheet(idOrdersheet);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/orderSheet")
    @ResponseBody
    public void createOrderSheet(@RequestParam("product_id") List<Long> productIds,
                                 @RequestParam("count") List<Short> counts,
                                 @RequestParam("setDateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime setDateTime,
                                 HttpSession session) {
        String loggedInUserRole = (String) session.getAttribute("loggedInUserRole");
        if ("store_manager".equals(loggedInUserRole)) {
            String loggedInUserId = (String) session.getAttribute("loggedInUserId");
            orderSheetService.createOrderSheet(productIds, counts, setDateTime, loggedInUserId);
        } else {
            ResponseEntity.badRequest().build();
        }

    }

}


