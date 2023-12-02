package com.Sales_manage.Sales_manage.brand_office.entity;

import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name= "brandoffice")
public class BrandOfficeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBrandoffice;

    private String officeName;
    private String address;

    @ManyToOne
    @JoinColumn(name = "id_brand", nullable = false)
    private com.Sales_manage.Sales_manage.brand.entity.BrandEntity idBrand;

    @OneToOne
    @JoinColumn(name = "id_storemanager")
    private StoreManagerEntity idStoremanger;

    @Column(nullable = false)
    private boolean operationalStatus = true;

}
