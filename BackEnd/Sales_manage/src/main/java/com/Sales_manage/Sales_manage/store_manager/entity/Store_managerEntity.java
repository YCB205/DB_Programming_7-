package com.Sales_manage.Sales_manage.store_manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name= "store_manager")
public class Store_managerEntity {
    @Id
    private String id_store_manager;

    private String passwd;
    private String name;
    private String email;
    private String number;
}
