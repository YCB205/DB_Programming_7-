package com.Sales_manage.Sales_manage.merchandise.entity;

import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name= "merchandise")
public class MerchandiseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_merchandise;

    private String categori;
    private String merchandiseName;
    private Long cost;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "id_brand", nullable = false)
    private com.Sales_manage.Sales_manage.brand.entity.BrandEntity idBrand;

}
