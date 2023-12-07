package com.Sales_manage.Sales_manage.merchandise.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchandiseRequestDTO {
    private String categori;
    private String merchandiseName;
    private Long cost;
    private Long price;
    private boolean salesStatus;
}
