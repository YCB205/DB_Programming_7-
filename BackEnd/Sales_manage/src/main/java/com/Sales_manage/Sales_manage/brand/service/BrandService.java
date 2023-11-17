package com.Sales_manage.Sales_manage.brand.service;

import com.Sales_manage.Sales_manage.store_manager.dto.Store_managerDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.Store_managerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final Store_managerRepository storemanagerRepository;

    public List<Store_managerDTO> getAll() {
        List<Store_managerEntity> storemanagerEntityList = storemanagerRepository.findAll();
        List<Store_managerDTO> storemanagerDTOList = new ArrayList<>();
        for (Store_managerEntity storemanagerEntity : storemanagerEntityList){
            Store_managerDTO storemanagerDTO = Store_managerDTO.toMemberTestDTO(storemanagerEntity);
            storemanagerDTOList.add(storemanagerDTO);
        }
        return storemanagerDTOList;
    }

}
