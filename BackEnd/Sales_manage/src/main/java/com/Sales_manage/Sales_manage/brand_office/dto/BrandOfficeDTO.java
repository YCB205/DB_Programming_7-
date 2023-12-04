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
    private boolean operationalStatus;

    public static BrandOfficeDTO toBrandOfficeDTO(BrandOfficeEntity brandOfficeEntity) {
        BrandOfficeDTO brandOfficeDTO = new BrandOfficeDTO();
        brandOfficeDTO.setIdBrandOffice(brandOfficeEntity.getIdBrandoffice());
        brandOfficeDTO.setOfficeName(brandOfficeEntity.getOfficeName());
        brandOfficeDTO.setAddress(brandOfficeEntity.getAddress());
        brandOfficeDTO.setIdBrand(brandOfficeEntity.getIdBrand().getIdBrand());

        // idStoremanger에 대한 null 체크 추가
        if (brandOfficeEntity.getIdStoremanger() != null) {
            brandOfficeDTO.setIdStoreManager(brandOfficeEntity.getIdStoremanger().getIdStoremanager());
        } else {
            // idStoremanger가 null인 경우 처리
            brandOfficeDTO.setIdStoreManager(null);
            // 예외를 throw하거나, 메시지를 로그하거나, 기본값을 설정하는 등의 처리를 추가할 수 있습니다.
        }

        brandOfficeDTO.setOperationalStatus(brandOfficeEntity.isOperationalStatus());
        return brandOfficeDTO;
    }
}
