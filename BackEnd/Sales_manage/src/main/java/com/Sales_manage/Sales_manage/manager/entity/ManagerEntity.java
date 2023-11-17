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
    private String id_manager;

    private String passwd;
    private String name;
    private String email;
    private String phone_number;
    @ManyToOne
    @JoinColumn(name = "id_brand", nullable = false)
    private com.Sales_manage.Sales_manage.brand.entity.BrandEntity id_brand;
}
