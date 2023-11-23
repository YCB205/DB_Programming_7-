package com.Sales_manage.Sales_manage.orderSheet.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MerchandiseResponse {
    private String categori;
    private Long id_merchandise;
    private String merchandiseName;
    private Long cost;
    private Long price;
    private short orderCount;


}

