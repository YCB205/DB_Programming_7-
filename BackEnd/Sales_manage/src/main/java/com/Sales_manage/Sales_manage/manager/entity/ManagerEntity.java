package com.Sales_manage.Sales_manage.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name= "manager")
public class ManagerEntity {
    @Id
    private String idManager;

    private String passwd;
    private String name;
    private String email;
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "id_brand", nullable = false)
    private com.Sales_manage.Sales_manage.brand.entity.BrandEntity idBrand;
}
