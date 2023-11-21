package com.Sales_manage.Sales_manage.brand_office.dto;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BrandOfficeDTO {
    private Long idBrandOffice;
    private String officeName;
    private String address;
    private Long idBrand;
    private String idStoreManager;


    public static BrandOfficeDTO toBrandOfficeDTO(BrandOfficeEntity brandOfficeEntity) {
        BrandOfficeDTO brandOfficeDTO = new BrandOfficeDTO();
        brandOfficeDTO.setIdBrandOffice(brandOfficeEntity.getIdBrandoffice());
        brandOfficeDTO.setOfficeName(brandOfficeEntity.getOfficeName());
        brandOfficeDTO.setAddress(brandOfficeEntity.getAddress());
        brandOfficeDTO.setIdBrand(brandOfficeEntity.getIdBrand().getIdBrand());
        brandOfficeDTO.setIdStoreManager(brandOfficeEntity.getIdStoremanger().getIdStoremanager());
        return brandOfficeDTO;
    }
}
