package com.Sales_manage.Sales_manage.merchandise.entity;

import com.Sales_manage.Sales_manage.orderSheet.entity.IncludeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

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

    @Column(nullable = false)
    private boolean salesStatus = true;

    @OneToMany(mappedBy = "idMerchandise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncludeEntity> includes;

}
