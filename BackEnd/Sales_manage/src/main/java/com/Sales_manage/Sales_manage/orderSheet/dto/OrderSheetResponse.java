package com.Sales_manage.Sales_manage.orderSheet.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderSheetResponse {
    private Long idOrdersheet;
    private LocalDateTime orderTime;
    private Long sales;
    private Long profit;
    private List<MerchandiseResponse> merchandise;

    // Getter 및 Setter는 생략했습니다.
}
