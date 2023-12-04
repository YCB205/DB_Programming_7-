package com.Sales_manage.Sales_manage.orderSheet.entity;

import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@IdClass(IncludeId.class)
@Table(name = "include")
public class IncludeEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_ordersheet")
    public OrderSheetEntity idOrdersheet;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_merchandise")
    public MerchandiseEntity idMerchandise;

    private short orderCount;
    private Long totalCost;
    private Long sales;
}
