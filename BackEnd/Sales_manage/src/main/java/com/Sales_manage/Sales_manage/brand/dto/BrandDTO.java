package com.Sales_manage.Sales_manage.brand.dto;

import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
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


    public static BrandDTO toBrandDTO(Store_managerEntity storemanagerEntity) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId_brand(storemanagerEntity.getId_brand());
        brandDTO.setBrand_name(storemanagerEntity.getPasswd());
        return brandDTO;
    }
}
