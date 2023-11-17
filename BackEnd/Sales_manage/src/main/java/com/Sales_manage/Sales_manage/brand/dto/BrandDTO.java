package com.Sales_manage.Sales_manage.brand.dto;

import com.Sales_manage.Sales_manage.brand.entity.BrandEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BrandDTO {
    private Long id_brand;
    private String brand_name;


    public static BrandDTO toBrandDTO(BrandEntity brandEntity) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId_brand(brandEntity.getId_brand());
        brandDTO.setBrand_name(brandEntity.getBrand_name());
        return brandDTO;
    }
}
