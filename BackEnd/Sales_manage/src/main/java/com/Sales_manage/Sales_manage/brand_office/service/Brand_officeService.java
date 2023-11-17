package com.Sales_manage.Sales_manage.brand_office.service;

import com.Sales_manage.Sales_manage.store_manager.dto.Store_managerDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.Store_managerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Brand_officeService {
    private final Store_managerRepository brandOfficeRepository;

    public List<Store_managerDTO> getAll() {
        List<Store_managerEntity> brandOfficeEntityList = brandOfficeRepository.findAll();
        List<Store_managerDTO> brandOfficeDTOList = new ArrayList<>();
        for (Store_managerEntity brandOfficeEntity: brandOfficeEntityList){
            Store_managerDTO brandOfficeDTO = Store_managerDTO.toMemberTestDTO(brandOfficeEntity);
            brandOfficeDTOList.add(brandOfficeDTO);
        }
        return brandOfficeDTOList;
    }

}
