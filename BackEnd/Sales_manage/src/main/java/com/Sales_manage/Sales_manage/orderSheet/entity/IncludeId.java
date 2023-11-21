package com.Sales_manage.Sales_manage.orderSheet.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class IncludeId implements Serializable {
    private Long idOrdersheet;
    private Long idMerchandise;

}
