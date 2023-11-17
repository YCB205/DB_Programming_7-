package com.Sales_manage.Sales_manage.brand_office.entity;

import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name= "brand_office")
public class Brand_officeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_brand_office;

    private String office_name;
    private String address;

    @ManyToOne
    @JoinColumn(name = "id_brand", nullable = false)
    private com.Sales_manage.Sales_manage.brand.entity.BrandEntity id_brand;

    @ManyToOne
    @JoinColumn(name = "id_store_manager")
    private Store_managerEntity id_store_manager;
}
