package com.Sales_manage.Sales_manage.brand_office.service;

import com.Sales_manage.Sales_manage.store_manager.dto.StoreManagerDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.StoreManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Brand_officeService {
    private final StoreManagerRepository brandOfficeRepository;

    public List<StoreManagerDTO> getAll() {
        List<StoreManagerEntity> brandOfficeEntityList = brandOfficeRepository.findAll();
        List<StoreManagerDTO> brandOfficeDTOList = new ArrayList<>();
        for (StoreManagerEntity brandOfficeEntity: brandOfficeEntityList){
            StoreManagerDTO brandOfficeDTO = StoreManagerDTO.toMemberTestDTO(brandOfficeEntity);
            brandOfficeDTOList.add(brandOfficeDTO);
        }
        return brandOfficeDTOList;
    }

}
