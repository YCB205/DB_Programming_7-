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
    private final Store_managerRepository storeManagerRepository;

    public List<Store_managerDTO> getAll() {
        List<Store_managerEntity> storeManagerEntityList = storeManagerRepository.findAll();
        List<Store_managerDTO> storeManagerDTOList = new ArrayList<>();
        for (Store_managerEntity memberTestEntity: storeManagerEntityList){
            Store_managerDTO storeManagerDTO = Store_managerDTO.toMemberTestDTO(memberTestEntity);
            storeManagerDTOList.add(storeManagerDTO);
        }
        return storeManagerDTOList;
    }

}
