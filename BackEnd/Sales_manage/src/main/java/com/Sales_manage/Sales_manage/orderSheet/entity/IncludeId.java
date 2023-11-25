package com.Sales_manage.Sales_manage.orderSheet.entity;

import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
@Embeddable
public class IncludeId implements Serializable {

    private long idOrdersheet;

    private long idMerchandise;

    public IncludeId() {

    }

    public IncludeId(Long idOrdersheet, Long idMerchandise) {
        this.idOrdersheet = idOrdersheet;
        this.idMerchandise = idMerchandise;
    }
}
