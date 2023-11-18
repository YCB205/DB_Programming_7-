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
    private Long idBrand;
    private String brandName;


    public static BrandDTO toBrandDTO(BrandEntity brandEntity) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setIdBrand(brandEntity.getIdBrand());
        brandDTO.setBrandName(brandEntity.getBrandName());
        return brandDTO;
    }
}
