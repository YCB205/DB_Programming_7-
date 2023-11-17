package com.Sales_manage.Sales_manage.brand_office.dto;

import com.Sales_manage.Sales_manage.brand_office.entity.Brand_officeEntity;
import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Brand_officeDTO {
    private Long id_brand_office;
    private String office_name;
    private String address;
    private Long id_brand;
    private String id_store_manager;


    public static Brand_officeDTO toBrandOfficeDTO(Brand_officeEntity brandOfficeEntity) {
        Brand_officeDTO brandOfficeDTO = new Brand_officeDTO();
        brandOfficeDTO.setId_brand_office(brandOfficeEntity.getId_brand_office());
        brandOfficeDTO.setOffice_name(brandOfficeEntity.getOffice_name());
        brandOfficeDTO.setAddress(brandOfficeEntity.getAddress());
        brandOfficeDTO.setId_brand(brandOfficeEntity.getId_brand().getId_brand());
        brandOfficeDTO.setId_store_manager(brandOfficeEntity.getId_store_manager().getId_store_manager());
        return brandOfficeDTO;
    }
}
