package com.Sales_manage.Sales_manage.orderSheet.entity;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name= "ordersheet")
public class OrderSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdersheet;

    private LocalDateTime orderTime;

    @ManyToOne
    @JoinColumn(name = "id_brandoffice", nullable = false)
    private BrandOfficeEntity idBrandoffice;

    @OneToMany(mappedBy = "idOrdersheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncludeEntity> includes;
}
