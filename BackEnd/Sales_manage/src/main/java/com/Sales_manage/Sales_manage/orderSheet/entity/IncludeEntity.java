package com.Sales_manage.Sales_manage.orderSheet.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name= "include")
public class IncludeEntity {

    @EmbeddedId
    private IncludeId includeId;

    private short orderCount;
    private Long profit;
    private Long sales;

}
